package pl.JestesPiekna.registration.dto;

import jakarta.validation.constraints.*;

import java.util.Date;

public class UserDto {

    @NotBlank(message = "username is mandatory")
    private String username;

    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?!.* ).*", message = "Password must contain at least one lowercase letter, " +
            "one uppercase letter, one digit, and no white spaces")
    @Size(min = 8, max = 32, message = "Password must be between 8 and 32 characters")
    private String password;

    @NotBlank(message = "email is mandatory")
    @Email(message = "Invalid email address")
    private String email;

    @NotNull(message = "Enabled status is mandatory")
    private Integer enabled;

    private String activationToken;
    private Date tokenExpirationDate;

    public UserDto(String username, String password, String email, int enabled, String activationToken, Date tokenExpirationDate) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.enabled = enabled;
        this.activationToken = activationToken;
        this.tokenExpirationDate = tokenExpirationDate;
    }

    public UserDto() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getEnabled() {
        return enabled;
    }

    public void setEnabled(int enabled) {
        this.enabled = enabled;
    }

    public String getActivationToken() {
        return activationToken;
    }

    public void setActivationToken(String activationToken) {
        this.activationToken = activationToken;
    }

    public Date getTokenExpirationDate() {
        return tokenExpirationDate;
    }

    public void setTokenExpirationDate(Date tokenExpirationDate) {
        this.tokenExpirationDate = tokenExpirationDate;
    }
}
