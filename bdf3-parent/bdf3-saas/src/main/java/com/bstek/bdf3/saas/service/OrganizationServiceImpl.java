package com.bstek.bdf3.saas.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bstek.bdf3.jpa.JpaUtil;
import com.bstek.bdf3.saas.domain.Organization;
import com.bstek.bdf3.saas.resource.ResourceAllocator;
import com.bstek.bdf3.saas.resource.ResourceReleaser;

/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2016年8月12日
 */
@Service
@Transactional(readOnly = true)
public class OrganizationServiceImpl implements OrganizationService {

	@Autowired
	private List<ResourceAllocator> allocators;
	
	@Autowired
	private List<ResourceReleaser> releasers;
	
	@Override
	public Organization get(String id) {
		return JpaUtil.getOne(Organization.class, id);
	}

	@Override
	@Transactional
	public void register(Organization organization) {
		for (ResourceAllocator allocator : allocators) {
			allocator.allocate(organization);
		}
		JpaUtil.persist(organization);
	}
	
	@Override
	@Transactional
	public void allocteResource(Organization organization) {
		for (ResourceAllocator allocator : allocators) {
			allocator.allocate(organization);
		}
	}

	@Override
	@Transactional
	public void releaseResource(Organization organization) {
		for (ResourceReleaser releaser : releasers) {
			releaser.release(organization);
		}
	}

}
