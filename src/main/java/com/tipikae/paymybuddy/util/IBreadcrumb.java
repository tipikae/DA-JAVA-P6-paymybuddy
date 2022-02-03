package com.tipikae.paymybuddy.util;

import java.util.List;

import com.tipikae.paymybuddy.dto.BreadcrumbDTO;
import com.tipikae.paymybuddy.exceptions.BreadcrumbException;

/**
 * Handler breadcrumb interface.
 * @author tipikae
 * @version 1.0
 *
 */
public interface IBreadcrumb {

	/**
	 * Get a breadcrumb.
	 * @param query
	 * @param label
	 * @return List<BreadcrumbDTO>
	 * @throws BreadcrumbException
	 */
	List<BreadcrumbDTO> getBreadCrumb(String query, String label) throws BreadcrumbException;
}
