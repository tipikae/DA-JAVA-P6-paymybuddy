package com.tipikae.paymybuddy.entities;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;

/**
 * Entity class for billing.
 * @author tipikae
 * @version 1.0
 *
 */
@Data
@Entity
@Table(name = "billing")
public class Billing implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "number")
	private int number;
	
	@Column(name = "fee")
	private double fee;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(
			name = "number_operation",
			referencedColumnName = "number")
	private Operation operation;
}
