package com.authentication.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

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
public class AuthenticationResponse implements Serializable {

    private String accessToken;
    private String refreshToken;
}