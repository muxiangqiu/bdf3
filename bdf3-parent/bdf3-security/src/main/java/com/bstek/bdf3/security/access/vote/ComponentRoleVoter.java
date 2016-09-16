/* Copyright 2004, 2005, 2006 Acegi Technology Pty Limited
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.bstek.bdf3.security.access.vote;

import java.util.Collection;

import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.CollectionUtils;

import com.bstek.bdf3.security.domain.Component;

/**
 * 组件角色投票器
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2016年7月7日
 */
@org.springframework.stereotype.Component
public class ComponentRoleVoter implements AccessDecisionVoter<Object> {

	private String rolePrefix = "ROLE_";

	
	public String getRolePrefix() {
		return rolePrefix;
	}

	public void setRolePrefix(String rolePrefix) {
		this.rolePrefix = rolePrefix;
	}

	public boolean supports(ConfigAttribute attribute) {
		if ((attribute.getAttribute() != null)
				&& attribute.getAttribute().startsWith(getRolePrefix())) {
			return true;
		}
		else {
			return false;
		}
	}

	public boolean supports(Class<?> clazz) {
		return Component.class.isAssignableFrom(clazz);
	}

	public int vote(Authentication authentication, Object object,
			Collection<ConfigAttribute> attributes) {
		if(authentication == null) {
			return ACCESS_DENIED;
		}
		if (CollectionUtils.isEmpty(attributes)) {
			return ACCESS_GRANTED;
		}
		int result = ACCESS_ABSTAIN;
		Component component = (Component) object;
		String componentType = "";
		if (component.getComponentType() != null) {
			componentType = component.getComponentType().name();
		}
		Collection<? extends GrantedAuthority> authorities = extractAuthorities(authentication);

		for (ConfigAttribute attribute : attributes) {
			if (this.supports(attribute)) {
				result = ACCESS_DENIED;

				for (GrantedAuthority authority : authorities) {
					if (attribute.getAttribute().startsWith((authority.getAuthority() + "_" + componentType))) {
						return ACCESS_GRANTED;
					}
				}
			}
		}

		return result;
	}

	Collection<? extends GrantedAuthority> extractAuthorities(
			Authentication authentication) {
		return authentication.getAuthorities();
	}
}
