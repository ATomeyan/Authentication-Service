package com.authentication.mapper;

import com.authentication.entity.Role;
import com.authentication.entity.User;
import com.authentication.entity.UserRoles;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Artur Tomeyan
 * @date 07/09/2023
 */
@Component
public class UserRoleMapper {

    private UserRoleMapper() {
    }

    public static void mapRequestDtoToEntity(Role role, User user) {

        UserRoles userRoles = new UserRoles();

        userRoles.setRole(role);
        user.setUserRoles(List.of(userRoles));
        userRoles.setUser(user);
    }
}