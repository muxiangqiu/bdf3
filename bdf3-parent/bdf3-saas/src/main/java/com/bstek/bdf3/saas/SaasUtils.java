package com.bstek.bdf3.saas;

import java.util.Stack;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.bstek.bdf3.saas.domain.Organization;
import com.bstek.bdf3.saas.domain.TempOrganizationSupport;

/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2016年8月16日
 */
public abstract class SaasUtils {
	
	private static ThreadLocal<Stack<Authentication>> threadLocal = new ThreadLocal<Stack<Authentication>>();

	public static final void setSecurityContext(String organizationId) {
		Organization organization = new Organization();
		organization.setId(organizationId);
		setSecurityContext(organization);
	}
	
	public static final void setSecurityContext(Organization organization) {
		TempOrganizationSupport tempOrganizationSupport = new TempOrganizationSupport();
		Organization tempOrganization = new Organization();
		tempOrganization.setId(organization.getId());
		tempOrganizationSupport.setOrganization(tempOrganization);		
		SecurityContextHolder.getContext().setAuthentication(new OrganizationAuthentication(tempOrganizationSupport));
	}
	
	public static final void pushSecurityContext(Organization organization) {
		TempOrganizationSupport tempOrganizationSupport = new TempOrganizationSupport();
		Organization tempOrganization = new Organization();
		tempOrganization.setId(organization.getId());
		tempOrganizationSupport.setOrganization(tempOrganization);
		Stack<Authentication> stack = threadLocal.get();
		if (stack == null) {
			stack = new Stack<Authentication>();
			threadLocal.set(stack);
		}
		stack.push(SecurityContextHolder.getContext().getAuthentication());
		SecurityContextHolder.getContext().setAuthentication(new OrganizationAuthentication(tempOrganizationSupport));
	}
	
	public static final void popSecurityContext() {
		Stack<Authentication> stack = threadLocal.get();
		if (stack == null) {
			return;
		}
		if (!stack.isEmpty()) {
			SecurityContextHolder.getContext().setAuthentication(stack.pop());
		}
		if (stack.isEmpty()) {
			threadLocal.remove();
		}
	}

	
	public static final void clearSecurityContext() {
		SecurityContextHolder.getContext().setAuthentication(null);
	}

}
