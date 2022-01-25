package com.tipikae.paymybuddy.entities;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import lombok.Data;

/**
 * Connection entity
 * @author tipikae
 * @version 1.0
 *
 */
@Data
@Entity
@Table(name = "connection")
public class Connection implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * Embedded ConnectionId.
	 */
	@EmbeddedId
	private ConnectionId id;

	/**
	 * Source User object.
	 */
	@ManyToOne
	@MapsId("emailUserSrc")
	@JoinColumn(name = "email_user_src")
	private User srcUser;

	/**
	 * Destination User object.
	 */
	@ManyToOne
	@MapsId("emailUserDest")
	@JoinColumn(name = "email_user_dest")
	private User destUser;
}
