package com.bstek.bdf3.importer.policy.impl;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.transaction.annotation.Transactional;

import com.bstek.bdf3.dorado.jpa.BeanUtils;
import com.bstek.bdf3.dorado.jpa.JpaUtil;
import com.bstek.bdf3.importer.model.Cell;
import com.bstek.bdf3.importer.model.MappingRule;
import com.bstek.bdf3.importer.model.Record;
import com.bstek.bdf3.importer.policy.Context;
import com.bstek.bdf3.importer.policy.ParseRecordPolicy;
import com.bstek.bdf3.importer.processor.CellPostprocessor;
import com.bstek.bdf3.importer.processor.CellPreprocessor;
import com.bstek.bdf3.importer.processor.CellProcessor;

import net.sf.cglib.beans.BeanMap;

/**
 *@author Kevin.yang
 *@since 2015年8月23日
 */
public class ParseRecordPolicyImpl implements ParseRecordPolicy, ApplicationContextAware {

	private Collection<CellPreprocessor> cellPreprocessors;
	private Collection<CellProcessor> cellProcessors;
	private Collection<CellPostprocessor> cellPostprocessors;
	
	@Override
	@Transactional
	public void apply(Context context) throws ClassNotFoundException {
		List<Record> records = context.getRecords();
		
		for (int i = context.getStartRow(); i < records.size(); i++) {
			Record record = records.get(i);
			Object entity = BeanUtils.newInstance(context.getEntityClass());
			context.setCurrentEntity(entity);
			context.setCurrentRecord(record);
			String idProperty = JpaUtil.getIdName(context.getEntityClass());
			BeanMap beanMap = BeanMap.create(context.getCurrentEntity());
			if (beanMap.getPropertyType(idProperty) == String.class) {
				beanMap.put(idProperty, UUID.randomUUID().toString());
			}
			for (MappingRule mappingRule : context.getMappingRules()) {
				Cell cell = record.getCell(mappingRule.getExcelColumn());

				context.setCurrentMappingRule(mappingRule);
				context.setCurrentCell(cell);
				cellPreprocess(context);
				cellProcess(context);
				cellPostprocess(context);
				
			}
			JpaUtil.persist(entity);
			if (i % 100 == 0) {
				JpaUtil.persistAndFlush(entity);
				JpaUtil.getEntityManager(entity).clear();
			} else {
				JpaUtil.persist(entity);
			}
		}

	}
	
	
	protected void cellPreprocess(Context context) {
		for (CellPreprocessor cellPreProcessor : cellPreprocessors) {
			if (cellPreProcessor.support(context)) {
				cellPreProcessor.process(context);
			}
		}
	}
	
	protected void cellProcess(Context context) {
		for (CellProcessor cellProcessor : cellProcessors) {
			if (cellProcessor.support(context)) {
				cellProcessor.process(context);
			}
		}
	}
	
	protected void cellPostprocess(Context context) {
		for (CellPostprocessor cellPostprocessor : cellPostprocessors) {
			if (cellPostprocessor.support(context)) {
				cellPostprocessor.process(context);
			}
		}
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		cellPreprocessors = applicationContext.getBeansOfType(CellPreprocessor.class).values();
		cellProcessors = applicationContext.getBeansOfType(CellProcessor.class).values();
		cellPostprocessors = applicationContext.getBeansOfType(CellPostprocessor.class).values();

	}

}
