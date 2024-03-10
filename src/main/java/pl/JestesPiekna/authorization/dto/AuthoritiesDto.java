package pl.JestesPiekna.authorization.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import pl.JestesPiekna.model.User;


public class AuthoritiesDto {

    @NotNull(message = "user cannot be null")
    @NotBlank(message = "username is mandatory")
    private String username;

    @NotNull(message = "user cannot be null")
    @NotBlank(message = "username is mandatory")
    private String authority;

    @NotNull(message = "user cannot be null")
    private User user;

    public AuthoritiesDto(String username, String authority, User user) {
        this.username = username;
        this.authority = authority;
        this.user = user;
    }

    public AuthoritiesDto() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

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
