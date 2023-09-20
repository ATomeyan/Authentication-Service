package com.authentication.repository;

import com.authentication.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Artur Tomeyan
 * @date 03/11/2022
 */
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}