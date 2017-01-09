package com.bstek.bdf3.saas.ui.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bstek.bdf3.dorado.jpa.JpaUtil;
import com.bstek.bdf3.dorado.jpa.policy.SaveContext;
import com.bstek.bdf3.dorado.jpa.policy.impl.SmartSavePolicy;
import com.bstek.bdf3.saas.domain.Organization;
import com.bstek.dorado.data.entity.EntityState;
import com.bstek.dorado.data.entity.EntityUtils;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;

/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2017年1月9日
 */
@Service("ui.organizationService")
@Transactional(readOnly = true)
public class OrganizationServiceImpl implements OrganizationService {

	@Autowired
	private com.bstek.bdf3.saas.service.OrganizationService organizationService;
	
	@Override
	public void load(Page<Organization> page, Criteria criteria) {
		JpaUtil.linq(Organization.class).where(criteria).paging(page);
	}

	@Override
	@Transactional
	public void save(List<Organization> organizations) {
		JpaUtil.save(organizations, new SmartSavePolicy() {

			@Override
			public void apply(SaveContext context) {
				EntityState state = EntityUtils.getState(context.getEntity());
				if (EntityState.DELETED.equals(state)) {
					Organization organization = context.getEntity();
					organizationService.remove(organization);
				} else {
					super.apply(context);
				}
				
			}
			
		});

	}

	@Override
	public boolean isExist(String organizationId) {
		return JpaUtil.linq(Organization.class).equal("id", organizationId).exists();
		
	}

}
