package com.bstek.bdf3.security.ui.utils;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.util.StringUtils;

import com.bstek.dorado.common.MetaDataSupport;
import com.bstek.dorado.core.Configure;
import com.bstek.dorado.view.AbstractViewElement;

/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2017年6月14日
 */
public abstract class ControlUtils {
	
	public static final String NO_SECURITY = "noSecurity";
	public static final String SECURITY_DESC = "securityDesc";
	private static Set<String> componentPermissionSupportTypes;

	public static boolean isNoSecurtiy(MetaDataSupport metaDataSupport) {
		Map<String, Object> metaData = metaDataSupport.getMetaData();
		if (metaData != null) {
			Object noSecurity = metaData.get(NO_SECURITY);
			if (noSecurity instanceof Boolean) {
				if ((Boolean) noSecurity) {
					return true;
				}
			} else if (noSecurity instanceof String) {
				if (Boolean.valueOf((String) noSecurity)) {
					return true;
				}
			}
		}
		return false;
	}
	
	public static boolean supportControlType(AbstractViewElement control) {
		Set<String> types = getComponentPermissionSupportTypes();
		if (types != null 
				&& !types.contains(control.getClass().getSimpleName()) 
				&& !types.contains(control.getClass().getName())) {
			return false;
		}
		
		return true;
	}
	
	private static Set<String> getComponentPermissionSupportTypes() {
		if (componentPermissionSupportTypes != null) {
			return componentPermissionSupportTypes;
		}
		String componentPermissionSupportType = Configure.getString("bdf3.componentPermissionSupportType");
		if (!StringUtils.isEmpty(componentPermissionSupportType)) {
			String[] types = StringUtils.commaDelimitedListToStringArray(componentPermissionSupportType);
			componentPermissionSupportTypes = new HashSet<>();
			for (String t : types) {
				componentPermissionSupportTypes.add(t);
			}
		}
		return componentPermissionSupportTypes;

	}
	
	public static String getSecurityDesc(MetaDataSupport metaDataSupport) {
		Map<String, Object> metaData = metaDataSupport.getMetaData();
		if (metaData != null && metaData.get(SECURITY_DESC) instanceof String) {
			return (String) metaData.get(SECURITY_DESC);
		}
		return null;
	}
}
