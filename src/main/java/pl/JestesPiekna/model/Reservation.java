package pl.JestesPiekna.model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "reservations")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User client;

    @ManyToOne
    @JoinColumn(name = "service_types_id")
    private ServiceType service_type;

    @Column(name = "reservation_date")
    private Date reservation_date;

    public Reservation(Integer id, User client, ServiceType service_type, Date reservation_date) {
        this.id = id;
        this.client = client;
        this.service_type = service_type;
        this.reservation_date = reservation_date;
    }

    public Reservation() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getClient() {
        return client;
    }

    public void setClient(User client) {
        this.client = client;
    }

    public ServiceType getService_type() {
        return service_type;
    }

    public void setService_type(ServiceType service_type) {
        this.service_type = service_type;
    }

    public Date getReservation_date() {
        return reservation_date;
    }

    public void setReservation_date(Date reservation_date) {
        this.reservation_date = reservation_date;
    }
}
