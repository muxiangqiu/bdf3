package com.bstek.bdf3.security.orm;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.StringUtils;

/**
 * 授权信息
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2016年1月25日
 */
@Entity
@Table(name = "BDF3_ROLE_GRANTED_AUTHORITY")
public class RoleGrantedAuthority implements GrantedAuthority {

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "ID_", length = 64)
	private String id;
	
	@Column(name = "ACTOR_ID_", length = 64)
	private String actorId;
	
	@Column(name = "ROLE_ID_", length = 64)
	private String roleId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 演员ID，及角色被赋予者ID
	 * @return actorId
	 */
	public String getActorId() {
		return actorId;
	}

	public void setActorId(String actorId) {
		this.actorId = actorId;
	}

	/**
	 * 角色ID
	 * @return roleId
	 */
	public String getRoleId() {
		return roleId;
	}
	
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	@Override
	public String getAuthority() {
		if (StringUtils.isEmpty(roleId)) {
			return null;
		}
		return "ROLE_" + roleId;
	}

}
