package com.authentication.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponse implements Serializable {

    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private boolean enabled;
    private List<String> roles;
}