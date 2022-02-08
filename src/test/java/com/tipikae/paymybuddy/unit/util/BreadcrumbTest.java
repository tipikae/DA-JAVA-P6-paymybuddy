package com.tipikae.paymybuddy.unit.util;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.tipikae.paymybuddy.dto.BreadcrumbDTO;
import com.tipikae.paymybuddy.exceptions.BreadcrumbException;
import com.tipikae.paymybuddy.util.BreadcrumbImpl;

@ExtendWith(SpringExtension.class)
class BreadcrumbTest {
	
	private BreadcrumbImpl breadcrumb = new BreadcrumbImpl();

	@Test
	void getBreadCrumbReturnsListWhenOk() throws BreadcrumbException {
		List<BreadcrumbDTO> list = breadcrumb.getBreadCrumb("/bank", "Bank");
		assertEquals(2, list.size());
		assertEquals("Home", list.get(0).getLabel());
		assertEquals("Bank", list.get(1).getLabel());
	}

	@Test
	void getBreadCrumbThrowsBreadcrumbExceptionWhenEmptyField() {
		assertThrows(BreadcrumbException.class,
				() -> breadcrumb.getBreadCrumb("/bank", ""));
	}
}
