package com.bstek.bdf3.security.ui.builder;

import java.util.Collection;
import java.util.Collections;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.bstek.bdf3.security.ui.utils.ClassUtils;
import com.bstek.dorado.view.AbstractViewElement;
import com.bstek.dorado.view.widget.Component;
import com.bstek.dorado.view.widget.Control;

/**
 * @author Kevin.yang
 */
@SuppressWarnings({"rawtypes","unchecked"})
public abstract class AbstractBuilder<T extends AbstractViewElement> implements Builder<T>{
	
	@Autowired
	protected Collection<Builder> builders;
	
	@Autowired
	private DefaultBuilder defaultBuilder; 
		
	@Override
	public void build(T control, ViewComponent parent) {
		ViewComponent component = new ViewComponent();
		component.setId(getId(control));
		component.setDesc(getDesc(control));
		component.setIcon(getIcon(control));
		component.setEnabled(isEnabled(control));
		component.setName(getName(control));
		parent.addChildren(component);
		buildChildren(control,component);
	}
	
	protected void buildChildren(T control, ViewComponent parent){
		if(getChildren(control)!=null){
			for(Object child:getChildren(control)){
				if (child instanceof Control) {
					boolean exist = false;
					Control c = (Control) child;
					for (Builder builder : builders) {
						if (builder.support(c)) {
							exist = true;
							builder.build(c, parent);
							break;
						}
					}
					if (!exist) {
						defaultBuilder.build(c, parent);
					}
				}
			}
		}
	}
	
	protected String getId(T control){
		if(control instanceof Component){
			return ((Component)control).getId();
		}
		return null;
	}
	
	protected String getIcon(T control){
		return ">dorado/res/"+control.getClass().getName().replaceAll("\\.", "/")+".png";
	}
	
	protected String getName(T control){
		return control.getClass().getSimpleName();
	}
	
	protected boolean isEnabled(T control){
		return StringUtils.isNotEmpty(getId(control));
	}
	
	
	protected Collection getChildren(T control){
		return Collections.EMPTY_LIST;
	}
	
	protected String getDesc(T control){
		return control.getUserData() == null ? null : control.getUserData().toString();
	}
	
	@Override
	public boolean support(Object control) {
		Class<?> clazz=ClassUtils.getGenericType(this.getClass(), 0);
		return clazz.isAssignableFrom(control.getClass());
	}

}
