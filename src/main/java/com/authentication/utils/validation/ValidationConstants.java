package com.authentication.utils.validation;

/**
 * @author Artur Tomeyan
 * @date 06/09/2023
 */
public final class ValidationConstants {

    private ValidationConstants() {
    }

    public static final String EMAIL = "^((?!\\.)[\\w-_.]*[^.])(@\\w+)(\\.\\w+(\\.\\w+)?[^.\\W])$";
    public static final String PASSWORD = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,12}$";
}