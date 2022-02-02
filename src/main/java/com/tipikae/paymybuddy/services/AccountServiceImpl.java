package com.tipikae.paymybuddy.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tipikae.paymybuddy.repositories.IOperationRepository;

/**
 * Account Service implementation.
 * @author tipikae
 * @version 1.0
 *
 */
@Service
public class AccountServiceImpl implements IAccountService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AccountServiceImpl.class);
	
	/**
	 * User Service.
	 */
	@Autowired
	private IUserService userService;
	
	/**
	 * Operation repository.
	 */
	@Autowired
	private IOperationRepository operationRepository;

}
