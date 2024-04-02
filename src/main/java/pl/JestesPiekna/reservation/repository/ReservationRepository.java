package pl.JestesPiekna.reservation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.JestesPiekna.model.Reservation;
import pl.JestesPiekna.model.ServiceType;
import pl.JestesPiekna.model.User;

import java.util.Date;
import java.util.List;


@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {

    @Query("SELECT COUNT(r) > 0 FROM Reservation r WHERE r.reservation_date = :reservationDate AND r.service_type = :serviceType")
    boolean existsByReservationDateAndServiceType(@Param("reservationDate") Date reservationDate, @Param("serviceType") ServiceType serviceType);

    List<Reservation> findAllByClient(User client);

    @Query("SELECT r FROM Reservation r WHERE r.client = :client ORDER BY r.reservation_date ASC")
    List<Reservation> findAllByClientOrderByReservationDateAsc(@Param("client") User client);

    @Query("SELECT r FROM Reservation r ORDER BY r.reservation_date ASC")
    List<Reservation> findAllReservationsSortedByReservationDateAsc();

}