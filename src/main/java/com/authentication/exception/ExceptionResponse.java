package com.authentication.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.time.LocalDateTime;

/**
 * @author Artur Tomeyan
 * @date 13/09/2023
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ExceptionResponse {

    private LocalDateTime dateTime;
    private int httpStatusCode;
    private String errorResponse;
    private String message;

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("dateTime", dateTime)
                .append("httpStatusCode", httpStatusCode)
                .append("errorResponse", errorResponse)
                .append("message", message)
                .toString();
    }
}