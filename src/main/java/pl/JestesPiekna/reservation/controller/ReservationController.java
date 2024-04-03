package pl.JestesPiekna.reservation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.JestesPiekna.model.*;
import pl.JestesPiekna.reservation.service.ReservationService;
import pl.JestesPiekna.serviceType.service.ServiceTypeService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ReservationController {

    private final ReservationService reservationService;

    private final ServiceTypeService serviceTypeService;

    @Autowired
    public ReservationController(ReservationService reservationService, ServiceTypeService serviceTypeService) {
        this.reservationService = reservationService;
        this.serviceTypeService = serviceTypeService;
    }
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @GetMapping("/addReservation")
    public String showReservationForm(Model model) {

        String firstName = String.valueOf(reservationService.getFirstNameFromContext());
        model.addAttribute("firstName", firstName);

        List<ServiceType> serviceTypes = serviceTypeService.getAllServiceTypes();
        model.addAttribute("serviceTypes", serviceTypes);


        return "reservationForm";
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @PostMapping("/submitReservation")
    public String submitReservation(@RequestParam(value = "serviceTypeId", required = false) Integer serviceTypeId,
                                    @RequestParam("reservationDate") String reservationDate,
                                    @RequestParam("reservationTime") String reservationTime,
                                    Model model,
                                    Authentication authentication) {

        if (serviceTypeId == null) {
            model.addAttribute("errorMessage", "Proszę wybrać rodzaj usługi.");
            model.addAttribute("serviceTypes", serviceTypeService.getAllServiceTypes());
            return "reservationForm"; // Zwrócenie widoku formularza z błędem
        }
        User client = reservationService.getLoggedInUser();
        ServiceType serviceType = serviceTypeService.getServiceTypeById(serviceTypeId);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        try {

            String dateTimeStr = reservationDate + " " + reservationTime;


            Date reservationDateTime = sdf.parse(dateTimeStr);


            Reservation reservation = new Reservation();
            reservation.setService_type(serviceType);
            reservation.setReservation_date(reservationDateTime);
            reservation.setClient(client);

            if (!reservationService.existsByReservationDate(reservationDateTime)) {
                reservationService.saveReservation(reservation, client);
                return "confirmedReservation";
            } else {
                model.addAttribute("errorMessage", "Wybrana godzina wizyty jest już zarezerwowana. Proszę wybrać inną godzinę.");
                model.addAttribute("serviceTypes", serviceTypeService.getAllServiceTypes());
                model.addAttribute("selectedServiceTypeId", serviceTypeId);
                return "reservationForm";
            }
        } catch (ParseException e) {
            e.printStackTrace();
            // Obsługa błędu parsowania daty i godziny
            model.addAttribute("errorMessage", "Wystąpił błąd podczas przetwarzania daty i godziny rezerwacji.");
            model.addAttribute("serviceTypes", serviceTypeService.getAllServiceTypes());
            model.addAttribute("selectedServiceTypeId", serviceTypeId);
            return "reservationForm";
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/reservations")
    public String showReservations(Model model) {
        String username = reservationService.getUsernameFromContext();
        List<Reservation> reservations = reservationService.findAllReservationsSortedByReservationDateAsc();

        List<String> firstNames = reservations.stream()
                .map(reservation -> reservation.getClient().getUserProfile())
                .map(userProfile -> userProfile != null ? userProfile.getFirstName() : "Brak danych")
                .collect(Collectors.toList());

        List<String> lastNames = reservations.stream()
                .map(reservation -> reservation.getClient().getUserProfile())
                .map(userProfile -> userProfile != null ? userProfile.getLastName() : "Brak danych")
                .collect(Collectors.toList());

        model.addAttribute("firstNames", firstNames);
        model.addAttribute("lastNames", lastNames);
        model.addAttribute("username", username);
        model.addAttribute("reservations", reservations);

        return "reservationsView";
    }

    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @GetMapping("/my/reservations")
    public String showMyReservations(Model model) {

        String username = reservationService.getUsernameFromContext();


        if (username == null) {
            return "redirect:/loginForm";
        }

        String firstName = reservationService.getFirstNameFromUserProfil(username);
        String lastName = reservationService.getLastNameFromUserProfil(username);


        User loggedInUser = reservationService.getLoggedInUser();
        List<Reservation> reservations = reservationService.getAllReservationsForUserByOrderReservationDateAsc(loggedInUser);



        model.addAttribute("firstName", firstName);
        model.addAttribute("lastName", lastName);
        model.addAttribute("reservations", reservations);

        return "myReservationsView";
    }
}


