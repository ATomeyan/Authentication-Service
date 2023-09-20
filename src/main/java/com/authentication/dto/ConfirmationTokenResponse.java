package com.authentication.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.io.Serializable;

/**
 * @author Artur Tomeyan
 * @date 07/09/2023
 */

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ConfirmationTokenResponse implements Serializable {

    private Long id;
    private String token;
}