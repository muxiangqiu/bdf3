package com.bstek.bdf3.security.ui.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import javax.servlet.ServletContext;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.bstek.bdf3.dorado.jpa.JpaUtil;
import com.bstek.bdf3.security.cache.SecurityCacheEvict;
import com.bstek.bdf3.security.orm.Component;
import com.bstek.bdf3.security.orm.Permission;
import com.bstek.bdf3.security.ui.builder.ViewBuilder;
import com.bstek.bdf3.security.ui.builder.ViewComponent;
import com.bstek.bdf3.security.ui.dummy.DummyRequest;
import com.bstek.dorado.data.entity.EntityState;
import com.bstek.dorado.data.entity.EntityUtils;
import com.bstek.dorado.view.View;
import com.bstek.dorado.view.ViewState;
import com.bstek.dorado.view.manager.ViewConfig;
import com.bstek.dorado.view.manager.ViewConfigManager;
import com.bstek.dorado.web.DoradoContext;

/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2016年7月12日
 */
@Service
@Transactional(readOnly = true)
public class PermissionServiceImpl implements PermissionService {
  private Logger logger = LoggerFactory.getLogger(PermissionServiceImpl.class);

	@Autowired
	private ViewConfigManager viewConfigManager;
	
	@Autowired
	private ViewBuilder viewBuilder;
	
	@SuppressWarnings({ "unchecked" })
	@Override
	public Collection<ViewComponent> loadComponents(String path) {
	  if (StringUtils.isEmpty(path)) {
      return Collections.EMPTY_LIST;
    }
    String viewName = StringUtils.substringBeforeLast(path, ".d");
    DoradoContext doradoContext = DoradoContext.getCurrent();
    ServletContext servletContext = doradoContext.getServletContext();

    List<ViewComponent> viewComponents = new ArrayList<>();

    // 通过线程分离dorado context
    Thread thread = new Thread(() -> {
      String VIEWSTATE_KEY = ViewState.class.getName();
      DoradoContext context = DoradoContext.init(servletContext, new DummyRequest());
      context.setAttribute(VIEWSTATE_KEY, ViewState.rendering);
      try {
        ViewComponent root = new ViewComponent();
        ViewConfig viewConfig = (ViewConfig) context.getAttribute(viewName);
        if (viewConfig == null) {
          viewConfig = viewConfigManager.getViewConfig(viewName);
          context.setAttribute(viewName, viewConfig);
        }
        if (viewConfig != null && viewConfig.getView() != null && viewBuilder.support(viewConfig.getView())) {
          View view = viewConfig.getView();
          viewBuilder.build(view, root, root, viewConfig);
        }
        viewComponents.addAll(root.getChildren());

      } catch (Exception e) {
        logger.error("解析 {} 出错", viewName, e);
      } finally {
        context.setAttribute(VIEWSTATE_KEY, ViewState.servicing);
      }
    });
    thread.start();
    try {
      thread.join();
    } catch (InterruptedException e) {
      logger.error("thread InterruptedException", e);
    }

    return viewComponents;
	}
	
	
	@Override
	public List<Permission> loadPermissions(String roleId, String urlId) {
		return JpaUtil
				.linq(Permission.class)
				.toEntity()
				.collect(Component.class, "resourceId")
				.equal("roleId", roleId)
				.equal("resourceType", Component.RESOURCE_TYPE)
				.exists(Component.class)
					.equalProperty("id", "resourceId")
					.equal("urlId", urlId)
				.list();
	}
	
	@SecurityCacheEvict
	@Override
	@Transactional
	public void save(Permission permission) {
		Component component = EntityUtils.getValue(permission, "component");
		if (permission.getResourceId() == null) {
			component.setId(UUID.randomUUID().toString());
			permission.setResourceId(component.getId());
			JpaUtil.persist(component);
		} else {
			JpaUtil.save(component);
		}
		
		if (EntityState.NONE.equals(EntityUtils.getState(component))
				&& EntityState.NONE.equals(EntityUtils.getState(permission))) {
			JpaUtil.remove(JpaUtil.merge(component));
			JpaUtil.remove(JpaUtil.merge(permission));
		} else {
			JpaUtil.save(permission);
		}
		
	}
}
