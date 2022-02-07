package com.tipikae.paymybuddy.unit.util;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.tipikae.paymybuddy.dto.BreadcrumbDTO;
import com.tipikae.paymybuddy.exceptions.BreadcrumbException;
import com.tipikae.paymybuddy.util.IBreadcrumb;

@SpringBootTest
class BreadcrumbTest {
	
	@Autowired
	private IBreadcrumb breadcrumb;

	@Test
	void getBreadCrumbReturnsListWhenOk() throws BreadcrumbException {
		List<BreadcrumbDTO> list = breadcrumb.getBreadCrumb("/bank", "Bank");
		assertEquals(2, list.size());
		assertEquals("Home", list.get(0).getLabel());
		assertEquals("Bank", list.get(1).getLabel());
	}

}
