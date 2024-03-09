package pl.JestesPiekna.model;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name = "authorities")
public class Authorities implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "username")
    private String username;
    @Column(name = "authority")
    private String authority;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Authorities(Integer id,String authority, User user) {
        this.id = id;
        this.authority = authority;
        this.user = user;
    }

    public Authorities(User user, String authority) {
        this.user = user;
        this.authority = authority;
    }



    public Authorities() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}