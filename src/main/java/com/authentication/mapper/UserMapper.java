package com.authentication.mapper;

import com.authentication.dto.RegistrationRequest;
import com.authentication.entity.Role;
import com.authentication.entity.User;
import org.springframework.stereotype.Component;

/**
 * @author Artur Tomeyan
 * @date 09/04/2023
 */

@Component
public class UserMapper {

    private UserMapper(){}

    public User mapUserRequestDtoToEntity(Role role, RegistrationRequest registrationRequest){
        User user = new User();

        user.setId(registrationRequest.getId());
        user.setFirstName(registrationRequest.getFirstName());
        user.setLastName(registrationRequest.getLastName());
        user.setUsername(registrationRequest.getUsername());
        user.setEmail(registrationRequest.getEmail());
        user.setEnabled(true);

        UserRoleMapper.mapRequestDtoToEntity(role, user);

        return user;
    }
}