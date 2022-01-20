package com.tipikae.paymybuddy.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tipikae.paymybuddy.entities.User;

/**
 * User repository interface.
 * @author tipikae
 * @version 1.0
 *
 */
@Repository
public interface UserRepository extends JpaRepository<User, String> {

	Optional<User> findByEmail(String email);
}
