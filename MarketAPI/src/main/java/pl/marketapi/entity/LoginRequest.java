package pl.marketapi.entity;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LoginRequest {

    @NotBlank(message = "Name should not be empty.")
    private String username;

    @NotBlank(message = "Password should not be empty.")
    private String password;

}
