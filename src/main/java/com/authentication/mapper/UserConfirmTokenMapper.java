package com.authentication.mapper;

import com.authentication.entity.ConfirmationToken;
import com.authentication.entity.User;
import com.authentication.entity.UserToken;
import org.springframework.stereotype.Component;

/**
 * @author Artur Tomeyan
 * @date 07/09/2023
 */
@Component
public class UserConfirmTokenMapper {

    private UserConfirmTokenMapper() {
    }

    public static void mapRequestConfirmToEntity(User user, ConfirmationToken confirmationToken) {

        UserToken userToken = new UserToken();

        userToken.setUser(user);
        userToken.setConfirmationToken(confirmationToken);
    }
}