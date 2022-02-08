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
		if(query.equals("") || label.equals("")) {
			LOGGER.debug("GetBreadCrumb exception: Empty field");
			throw new BreadcrumbException("Error when creating DTO.");
		}
		List<BreadcrumbDTO> breadcrumb = new ArrayList<>();
		breadcrumb.add(getHomeBreadcrumb());
		BreadcrumbDTO dto = new BreadcrumbDTO();
		dto.setCurrent(true);
		dto.setLabel(label);
		dto.setQuery(query);
		breadcrumb.add(dto);
		
		return breadcrumb;
	}
	
	private BreadcrumbDTO getHomeBreadcrumb() {
		BreadcrumbDTO home = new BreadcrumbDTO();
		home.setCurrent(false);
		home.setLabel("Home");
		home.setQuery("/home");
		return home;
	}

}
