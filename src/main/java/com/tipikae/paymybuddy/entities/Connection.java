package com.tipikae.paymybuddy.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
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
@IdClass(ConnectionId.class)
public class Connection implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "number_account1")
	private int numberAccount1;

	@Id
	@Column(name = "number_account2")
	private int numberAccount2;

	@Column(name = "name")
	private String name;
}
