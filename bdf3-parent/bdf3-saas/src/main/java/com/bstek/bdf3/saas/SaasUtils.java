package com.bstek.bdf3.saas;

import org.springframework.security.core.context.SecurityContextHolder;

import com.bstek.bdf3.saas.domain.Organization;
import com.bstek.bdf3.saas.domain.TempOrganizationSupport;

/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2016年8月16日
 */
public abstract class SaasUtils {
	public static final void setSecurityContext(String organizationId) {
		TempOrganizationSupport tempOrganizationSupport = new TempOrganizationSupport();
		Organization organization = new Organization();
		organization.setId(organizationId);
		tempOrganizationSupport.setOrganization(organization);
		SecurityContextHolder.getContext().setAuthentication(new OrganizationAuthentication(tempOrganizationSupport));
	}
	
	public static final void clearSecurityContext() {
		SecurityContextHolder.getContext().setAuthentication(null);
	}

}
