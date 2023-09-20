package com.authentication.repository;

import com.authentication.entity.ConfirmationToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author Artur Tomeyan
 * @date 02.01.2023
 */
public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken, Long> {

    Optional<ConfirmationToken> findByToken(String token);
}