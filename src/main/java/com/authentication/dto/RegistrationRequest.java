package com.authentication.dto;

import com.authentication.utils.validation.ValidationConstants;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * @author Artur Tomeyan
 * @date 03/11/2022
 */
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RegistrationRequest implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 255)
    private String firstName;

    @NotNull
    @Size(max = 255)
    private String lastName;

    @NotEmpty(message = "Email cannot be empty")
    @Email(message = "Email is not valid", regexp = ValidationConstants.EMAIL)
    @Size(max = 255)
    private String email;

    @NotNull
    @Size(max = 255)
    private String username;

    @NotNull
    @Pattern(message = "Password is not valid", regexp = ValidationConstants.PASSWORD)
    @Size(min = 8, max = 12)
    private String password;
}