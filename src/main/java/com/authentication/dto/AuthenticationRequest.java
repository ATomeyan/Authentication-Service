package com.authentication.dto;

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
public class AuthenticationRequest implements Serializable {

    private String username;
    private String password;
}