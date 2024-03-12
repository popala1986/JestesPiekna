package pl.JestesPiekna.registration.dto;

import jakarta.validation.constraints.*;

import java.util.Date;

public class RegisterUserDto {


    @NotBlank(message = "username is mandatory")
    private String username;
    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?!.* ).*", message = "Password must contain at least one lowercase letter, " +
            "one uppercase letter, one digit, and no white spaces")
    private String password;
    @NotBlank(message = "email is mandatory")
    @Email(message = "Invalid email address")
    private String email;
    @NotBlank(message = "firstName cannot be blank")
    @Size(min = 1, message = "\n" +
            "The name must have at least 1 character")
    @Pattern(regexp = "^[a-zA-ZłŁąĄęĘśŚżŻźŹ]+$", message = "The name must contain only letters")
    private String firstName;
    @NotBlank(message = "firstName cannot be blank")
    @Size(min = 1, message = "\n" +
            "The name must have at least 1 character")
    @Pattern(regexp = "^[a-zA-ZłŁąĄęĘśŚżŻźŹ]+$", message = "The name must contain only letters")
    private String lastName;
    @NotNull(message = "Phone number cannot be null")
    @NotBlank(message = "phone number cannot be blank")
    @Positive(message = "Phone number must be a positive number")
    @Size(min = 9, max = 9, message = "The phone number can only contain 9 digits ")
    private String phone;
    @NotBlank
    private String healthCondition;

    public RegisterUserDto(String username, String password, String email, String firstName, String lastName, String phone, String healthCondition) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.healthCondition = healthCondition;
    }

    public RegisterUserDto() {
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getHealthCondition() {
        return healthCondition;
    }

    public void setHealthCondition(String healthCondition) {
        this.healthCondition = healthCondition;
    }
}


