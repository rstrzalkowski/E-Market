package pl.marketapi.entity;

import lombok.Data;


import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class RegisterRequest {

    @NotBlank(message = "First name should not be empty.")
    private String firstName;

    @NotBlank(message = "Last name should not be empty.")
    private String lastName;

    @NotBlank(message = "Email should not be empty.")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Password should not be empty.")
    @Size(min = 8, message = "Password should contain at least 8 characters")
    private String password;

}
