package com.bstek.bdf3.saas.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bstek.bdf3.jpa.JpaUtil;
import com.bstek.bdf3.saas.domain.Organization;
import com.bstek.bdf3.saas.service.allocator.ResourceAllocator;

/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2016年8月12日
 */
@Service
public class OrganizationServiceImpl implements OrganizationService {

	@Autowired
	private List<ResourceAllocator> allocators;
	
	@Override
	public Organization get(String id) {
		return JpaUtil.getOne(Organization.class, id);
	}

	@Override
	public void register(Organization organization) {
		for (ResourceAllocator allocator : allocators) {
			allocator.allocate(organization);
		}
		JpaUtil.persist(organization);
	}

}
