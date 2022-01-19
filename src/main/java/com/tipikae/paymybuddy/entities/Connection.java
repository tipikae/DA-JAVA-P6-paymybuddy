package com.tipikae.paymybuddy.entities;

import java.io.Serializable;

import javax.persistence.Column;
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
@Table(name = "connections")
public class Connection implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	private ConnectionId id;

	@ManyToOne
	@MapsId("numberSrcAccount")
	@JoinColumn(name = "number_src_account")
	private Account srcAccount;

	@ManyToOne
	@MapsId("numberDestAccount")
	@JoinColumn(name = "number_dest_account")
	private Account destAccount;

	@Column(name = "name")
	private String name;
}
