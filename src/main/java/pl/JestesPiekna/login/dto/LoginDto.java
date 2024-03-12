package pl.JestesPiekna.login.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class LoginDto {

    @NotBlank(message = "username is mandatory")
    private String username;

    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?!.* ).*", message = "Password must contain at least one lowercase letter, " +
            "one uppercase letter, one digit, and no white spaces")
    @Size(min = 8, max = 32, message = "Password must be between 8 and 20 characters")
    private String password;

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
}
