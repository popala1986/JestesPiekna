package pl.JestesPiekna.reservation.service;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pl.JestesPiekna.model.Reservation;
import pl.JestesPiekna.model.ServiceType;
import pl.JestesPiekna.model.User;
import pl.JestesPiekna.model.UserProfile;
import pl.JestesPiekna.registration.repository.UserProfileRepository;
import pl.JestesPiekna.registration.repository.UserRepository;
import pl.JestesPiekna.reservation.repository.ReservationRepository;

import java.util.Date;
import java.util.List;


@Service
public class ReservationService {

    private final UserRepository userRepository;

    private final UserProfileRepository userProfileRepository;

    private final ReservationRepository reservationRepository;

    public ReservationService(UserRepository userRepository, UserProfileRepository userProfileRepository, ReservationRepository reservationRepository) {
        this.userRepository = userRepository;
        this.userProfileRepository = userProfileRepository;
        this.reservationRepository = reservationRepository;
    }

    public String getUsernameFromContext() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            return authentication.getName();
        } else {
            return null;
        }
    }

    public String getFirstNameFromContext() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            User user = userRepository.findUserByUsername(username);

            if (user != null && user.getUserProfile() != null) {
                return user.getUserProfile().getFirstName();
            }
        }

        return "";
    }

    public String getLastNameFromContext() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            User user = userRepository.findUserByUsername(username);

            if (user != null && user.getUserProfile() != null) {
                return user.getUserProfile().getLastName();
            }
        }

        return "";
    }

    public User getLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            return userRepository.findUserByUsername(username);
        } else {
            return null;
        }
    }

    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    public boolean existsByReservationDateAndServiceType(Date reservationDate, ServiceType serviceType) {
        return reservationRepository.existsByReservationDateAndServiceType(reservationDate, serviceType);
    }

    public void saveReservation(Reservation reservation, User user) {
        reservation.setClient(user);
        reservationRepository.save(reservation);
    }

    public String getFirstNameFromUserProfil(String username) {

        User user = userRepository.findUserByUsername(username);

        if (user != null) {
            UserProfile userProfile = user.getUserProfile();

            if (userProfile != null) {
                return userProfile.getFirstName();
            }
        }
        return "";
    }

    public String getLastNameFromUserProfil(String username) {
        // Znajdź użytkownika na podstawie nazwy użytkownika
        User user = userRepository.findUserByUsername(username);

        if (user != null) {
            UserProfile userProfile = user.getUserProfile();

            if (userProfile != null) {
                return userProfile.getLastName();
            }
        }

        return "";
    }

    public List<Reservation> getAllReservationsForUser(User user) {
        return reservationRepository.findAllByClient(user);
    }


}