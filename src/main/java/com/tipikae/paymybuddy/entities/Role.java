package com.tipikae.paymybuddy.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * Role entity.
 * @author tipikae
 * @version 1.0
 *
 */
@Data
@Entity
@Table(name = "role")
public class Role implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "role")
	private String role;
}
