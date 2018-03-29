package com.bstek.bdf3.notice.ui.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import com.bstek.bdf3.dorado.jpa.JpaUtil;
import com.bstek.bdf3.dorado.jpa.policy.SaveContext;
import com.bstek.bdf3.dorado.jpa.policy.impl.SmartSavePolicyAdapter;
import com.bstek.bdf3.notice.domain.GroupTemplate;
import com.bstek.bdf3.notice.domain.Template;
import com.bstek.dorado.annotation.DataProvider;
import com.bstek.dorado.annotation.DataResolver;
import com.bstek.dorado.data.provider.Page;

/**
 * @author Kevin Yang (mailto:muxiangqiu@gmail.com)
 * @since 2018年3月26日
 */
@Controller
@Transactional(readOnly = true)
public class TemplateController {
	
	@DataProvider
	public List<Template> load() {
		return JpaUtil.linq(Template.class).list();
	}
	
	@DataProvider
	public List<Template> loadByLikeName(Page<Template> page, String groupId, String name) {
		return JpaUtil.linq(Template.class)
				.addIf(name)
					.equal("name", "%" + name + "%")
				.endIf()
				.notExists(GroupTemplate.class)
					.equalProperty("templateId", "id")
					.equal("groupId", groupId)
				.end()
				.isFalse("offline")
				.or()
					.isFalse("global")
					.isFalse("displayable")
				.end()
				.list(page);
	}
	
	@DataProvider
	public List<Template> loadGlobal() {
		return JpaUtil.linq(Template.class)
				.isFalse("offline")
				.isTrue("global")
				.list();
	}
	
	
	
	@DataResolver
	@Transactional
	public void save(List<Template> tempaltes) {
		JpaUtil.save(tempaltes, new SmartSavePolicyAdapter() {

			@Override
			public boolean beforeDelete(SaveContext context) {
				Template template = context.getEntity();
				JpaUtil.lind(GroupTemplate.class).equal("templateId", template.getId()).delete();
				return true;
			}
			
		});
	}

}
