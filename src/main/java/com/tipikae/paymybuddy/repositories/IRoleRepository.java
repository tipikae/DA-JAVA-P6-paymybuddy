package com.tipikae.paymybuddy.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tipikae.paymybuddy.entities.Role;

/**
 * Role repository interface.
 * @author tipikae
 * @version 1.0
 *
 */
@Repository
public interface IRoleRepository extends JpaRepository<Role, String> {

}
