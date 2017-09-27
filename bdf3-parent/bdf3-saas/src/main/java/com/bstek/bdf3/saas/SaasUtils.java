package com.bstek.bdf3.saas;

import java.util.Stack;

import com.bstek.bdf3.jpa.JpaUtil;
import com.bstek.bdf3.saas.command.Command;
import com.bstek.bdf3.saas.command.CommandNeedReturn;
import com.bstek.bdf3.saas.domain.Organization;
import com.bstek.bdf3.saas.service.CommandService;
import com.bstek.bdf3.security.ContextUtils;
import com.bstek.bdf3.security.orm.User;

/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2016年8月16日
 */
public abstract class SaasUtils {
	
	private static ThreadLocal<Stack<Organization>> threadLocal = new ThreadLocal<Stack<Organization>>();
	private static CommandService commandService;
	
	public static final void pushOrganization(String organizationId) {
		Organization organization = new Organization();
		organization.setId(organizationId);
		pushOrganization(organization);
	}
	
	public static final void pushMasterSecurityContext() {
		pushOrganization(Constants.MASTER);
	}

	
	public static final void pushOrganization(Organization organization) {
		Organization tempOrganization = new Organization();
		tempOrganization.setId(organization.getId());
		Stack<Organization> stack = threadLocal.get();
		if (stack == null) {
			stack = new Stack<Organization>();
			threadLocal.set(stack);
		}
		stack.push(organization);
	}
	
	public static final Organization popOrganization() {
		Stack<Organization> stack = threadLocal.get();
		if (!stack.isEmpty()) {
			return stack.pop();
		}
		if (stack != null && stack.isEmpty()) {
			threadLocal.remove();
		}
		User user = ContextUtils.getLoginUser();
		return user.getOrganization();
	}
	
	public static final Organization peekOrganization() {
		Stack<Organization> stack = threadLocal.get();
		if (stack == null || stack.isEmpty()) {
			User user = ContextUtils.getLoginUser();
			if (user == null) {
				return null;
			}
			return user.getOrganization();
		}
		return stack.peek();
	}
	
	public static <T> T doQuery(String organizationId, CommandNeedReturn<T> command) {
		try {
			pushOrganization(organizationId);
			return getCommandService().executeQueryCommand(command);
		} finally {
			popOrganization();
		}
	}
	
	public static <T> T doNonQuery(String organizationId, CommandNeedReturn<T> command) {
		try {
			pushOrganization(organizationId);
			return getCommandService().executeNonQueryCommand(command);
		} finally {
			popOrganization();
		}
	}
	
	public static void doQuery(String organizationId, Command command) {
		try {
			pushOrganization(organizationId);
			getCommandService().executeQueryCommand(command);
		} finally {
			popOrganization();
		}
	}
	
	public static void doNonQuery(String organizationId, Command command) {
		try {
			pushOrganization(organizationId);
			getCommandService().executeNonQueryCommand(command);
		} finally {
			popOrganization();
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
		User user = ContextUtils.getLoginUser();
		if (user != null) {
			return user.getOrganization();
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
