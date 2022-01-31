package com.tipikae.paymybuddy.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;

/**
 * Account entity
 * @author tipikae
 * @version 1.0
 *
 */
@Data
@Entity
@Table(name = "account")
public class Account implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * User id.
	 */
	@Id
	@Column(name = "id_user")
	private int idUser;

	/**
	 * Created date.
	 */
	@Column(name = "date_created")
	private Date dateCreated;

	/**
	 * Balance.
	 */
	@Column(name = "balance")
	private double balance;

	/**
	 * User object.
	 */
	@OneToOne
	@MapsId
	@JoinColumn(name = "id_user")
	private User user;

	/**
	 * Operations list.
	 */
	@OneToMany(
			mappedBy = "account",
			cascade = CascadeType.ALL)
	private List<Operation> operations;
}
