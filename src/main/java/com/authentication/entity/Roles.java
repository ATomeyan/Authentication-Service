package com.authentication.entity;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Set;

import static com.authentication.entity.Permission.*;

/**
 * @author Artur Tomeyan
 * @date 04/11/2022
 */
public enum Roles {

    ADMIN(
            Set.of(ADMIN_READ,
                    ADMIN_UPDATE,
                    ADMIN_CREATE,
                    ADMIN_DELETE)),
    USER(Set.of(USER_READ));

    private final Set<Permission> permissions;

    Roles(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    public List<SimpleGrantedAuthority> getGrantedAuthorities() {
        List<SimpleGrantedAuthority> collect = getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .toList();

        collect.add(new SimpleGrantedAuthority("ROLE_" + this.name()));

        return collect;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }
}