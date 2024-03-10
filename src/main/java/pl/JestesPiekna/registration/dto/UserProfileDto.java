package pl.JestesPiekna.registration.dto;

import jakarta.validation.constraints.*;

public class UserProfileDto {

    @NotBlank(message = "firstName cannot be blank")
    @Size(min = 1, message = "\n" +
            "The name must have at least 1 character")
    @Pattern(regexp = "^[a-zA-ZłŁąĄęĘśŚżŻźŹ]+$", message = "The name must contain only letters")
    private String firstName;

    @NotBlank(message = "lastName cannot be blank")
    @Pattern(regexp = "^[a-zA-ZłŁąĄęĘśŚżŻźŹ]+$", message = "The name must contain only letters")
    private String lastName;

    @NotNull(message = "Phone number cannot be null")
    @Positive(message = "Phone number must be a positive number")
    @Pattern(regexp = "^[0-9]{9}", message = "The phone must contain min and max\n" +
            "9 digits ")
    private String phone;

    @NotBlank(message = "health_condition cannot be blank")
    private String health_condition;

    public UserProfileDto(String firstName, String lastName, String phone, String health_condition) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.health_condition = health_condition;
    }

    public UserProfileDto() {
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

    public String getHealth_condition() {
        return health_condition;
    }

    public void setHealth_condition(String health_condition) {
        this.health_condition = health_condition;
    }
}
