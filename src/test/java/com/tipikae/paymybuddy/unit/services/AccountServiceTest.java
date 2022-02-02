package com.tipikae.paymybuddy.unit.services;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.tipikae.paymybuddy.repositories.IAccountRepository;
import com.tipikae.paymybuddy.services.AccountServiceImpl;
import com.tipikae.paymybuddy.services.IUserService;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

	@Mock
	private IUserService userService;
	@Mock
	private IAccountRepository accountRepository;
	
	@InjectMocks
	private AccountServiceImpl accountService;

}
