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
    public String submitReservation(@RequestParam("serviceTypeId") Integer serviceTypeId,
                                    @RequestParam("reservationDateTime") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") Date reservationDateTime,
                                    Model model,
                                    Authentication authentication) {


        User client = reservationService.getLoggedInUser();


        ServiceType serviceType = serviceTypeService.getServiceTypeById(serviceTypeId);


        Reservation reservation = new Reservation();
        reservation.setService_type(serviceType);
        reservation.setReservation_date(reservationDateTime);


        reservation.setClient(client);


        if (!reservationService.existsByReservationDateAndServiceType(reservationDateTime, serviceType)) {

            reservationService.saveReservation(reservation,client);


            return "confirmedReservation";
        } else {

            model.addAttribute("errorMessage", "Wybrana data jest już zarezerwowana dla wybranej usługi. Proszę wybrać inną datę lub inną usługę.");
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


