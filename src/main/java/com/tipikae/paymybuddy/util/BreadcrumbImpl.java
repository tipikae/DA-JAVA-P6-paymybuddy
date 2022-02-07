package com.tipikae.paymybuddy.util;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.tipikae.paymybuddy.dto.BreadcrumbDTO;
import com.tipikae.paymybuddy.exceptions.BreadcrumbException;

/**
 * Breadcrumb implementation.
 * @author tipikae
 * @version 1.0
 *
 */
@Component
public class BreadcrumbImpl implements IBreadcrumb {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(BreadcrumbImpl.class);

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<BreadcrumbDTO> getBreadCrumb(String query, String label) throws BreadcrumbException {
		List<BreadcrumbDTO> breadcrumb = new ArrayList<>();
		try {
			breadcrumb.add(getHomeBreadcrumb());
			BreadcrumbDTO dto = new BreadcrumbDTO();
			dto.setCurrent(true);
			dto.setLabel(label);
			dto.setQuery(query);
			breadcrumb.add(dto);
		} catch (Exception e) {
			LOGGER.debug("GetBreadCrumb exception: " + e.getMessage());
			throw new BreadcrumbException("Error when creating DTO.");
		}
		
		return breadcrumb;
	}
	
	private BreadcrumbDTO getHomeBreadcrumb() throws BreadcrumbException {
		BreadcrumbDTO home = new BreadcrumbDTO();
		try {
			home.setCurrent(false);
			home.setLabel("Home");
			home.setQuery("/home");
		} catch (Exception e) {
			LOGGER.debug("getHomeBreadcrumb exception: " + e.getMessage());
			throw new BreadcrumbException("Error when creating home DTO.");
		}
		return home;
	}

}
