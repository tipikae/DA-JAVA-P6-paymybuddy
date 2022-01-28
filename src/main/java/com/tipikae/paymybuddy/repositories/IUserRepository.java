package com.tipikae.paymybuddy.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tipikae.paymybuddy.entities.User;

/**
 * User repository interface.
 * @author tipikae
 * @version 1.0
 *
 */
@Repository
public interface IUserRepository extends JpaRepository<User, String> {

	/**
	 * Find an user by email.
	 * @param email
	 * @return Optional<User>
	 */
	Optional<User> findByEmail(String email);
	
	/**
	 * Get potential friends.
	 * @param email
	 * @return List<User>
	 */
	@Query(
		value = "SELECT * FROM user u WHERE u.email NOT IN (SELECT c.email_user_dest FROM connection c WHERE c.email_user_src = :email) AND u.email <> :email ORDER BY u.lastname ASC", 
		nativeQuery = true)
	List<User> getPotentialFriends(@Param("email") String email);
	
	/**
	 * Delete an user by email.
	 * @param email
	 */
	void deleteByEmail(String email);
}
