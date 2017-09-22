package com.bstek.bdf3.saas;

import java.util.Stack;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.bstek.bdf3.jpa.JpaUtil;
import com.bstek.bdf3.saas.command.Command;
import com.bstek.bdf3.saas.command.CommandNeedReturn;
import com.bstek.bdf3.saas.domain.Organization;
import com.bstek.bdf3.saas.domain.TempOrganizationSupport;
import com.bstek.bdf3.saas.service.CommandService;
import com.bstek.bdf3.security.orm.OrganizationSupport;

/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2016年8月16日
 */
public abstract class SaasUtils {
	
	private static ThreadLocal<Stack<Authentication>> threadLocal = new ThreadLocal<Stack<Authentication>>();
	private static CommandService commandService;

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
	
	public static final void pushSecurityContext(String organizationId) {
		Organization organization = new Organization();
		organization.setId(organizationId);
		pushSecurityContext(organization);
	}
	
	public static final void pushMasterSecurityContext() {
		pushSecurityContext(Constants.MASTER);
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
	
	public static <T> T doQuery(String organizationId, CommandNeedReturn<T> command) {
		try {
			pushSecurityContext(organizationId);
			return getCommandService().executeQueryCommand(command);
		} finally {
			popSecurityContext();
		}
	}
	
	public static <T> T doNonQuery(String organizationId, CommandNeedReturn<T> command) {
		try {
			pushSecurityContext(organizationId);
			return getCommandService().executeNonQueryCommand(command);
		} finally {
			popSecurityContext();
		}
	}
	
	public static void doQuery(String organizationId, Command command) {
		try {
			pushSecurityContext(organizationId);
			getCommandService().executeQueryCommand(command);
		} finally {
			popSecurityContext();
		}
	}
	
	public static void doNonQuery(String organizationId, Command command) {
		try {
			pushSecurityContext(organizationId);
			getCommandService().executeNonQueryCommand(command);
		} finally {
			popSecurityContext();
		}
	}
	
	public static <T> T doQuery(CommandNeedReturn<T> command) {
		return doQuery(Constants.MASTER, command);
	}
	
	public static <T> T doNonQuery(CommandNeedReturn<T> command) {
		return doNonQuery(Constants.MASTER, command);
	}
	
	public static void doQuery(Command command) {
		doQuery(Constants.MASTER, command);
	}
	
	public static void doNonQuery(Command command) {
		doNonQuery(Constants.MASTER, command);
	}
	
	public static Organization getLoginOrg() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof OrganizationSupport) {
			return ((OrganizationSupport) principal).getOrganization();
		}
		return null;
	}
	
	public static String getLoginOrgId() {
		Organization org = getLoginOrg();
		if (org != null) {
			return org.getId();
		}
		return null;
	}
	
	private static CommandService getCommandService() {
		if (commandService == null) {
			commandService = JpaUtil.getApplicationContext().getBean(CommandService.class);
		}
		return commandService;
	}

	

	
	

}
