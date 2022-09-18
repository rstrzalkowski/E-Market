package pl.marketapi.domain.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class LoginRequest {

    @NotBlank(message = "Email should not be empty.")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Password should not be empty.")
    private String password;

}
