package com.bstek.bdf3.dorado.jpa.lin;

import java.util.List;

import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;

import org.springframework.data.domain.Pageable;

import com.bstek.bdf3.dorado.jpa.filter.Filter;
import com.bstek.bdf3.dorado.jpa.parser.CriterionParser;
import com.bstek.bdf3.dorado.jpa.policy.LinqContext;
import com.bstek.bdf3.jpa.lin.Lin;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;

/**
 *@author Kevin.yang
 *@since 2015年6月12日
 */
public interface Linq extends Lin<Linq, CriteriaQuery<?>> {

	
	Linq toEntity();
	 
	Linq filter(Filter filter);
	 
	Linq addParser(CriterionParser criterionParser);
	
	Linq addSubQueryParser(Class<?> entityClass, String... foreignKeys);

	Linq addSubQueryParser(Class<?>... entityClasses);

	Linq where(Criteria criteria);
	
	LinqContext getLinqContext();

	Linq collect(String... properties);

	Linq collect(Class<?> entityClass, String... properties);

	Linq collectSelect(Class<?> entityClass, String... projections);

	Linq setDisableSmartSubQueryCriterion();

	Linq setDisableBackFillFilter();
	
	<T> List<T> findAll();

	Linq desc(String... properties);

	Linq desc(Expression<?>... expressions);

	Linq asc(String... properties);

	Linq asc(Expression<?>... expressions);

	<T> void findAll(Page<T> page);

	Long count();

	boolean exists();

	<T> T findOne();

	Linq distinct();
	
	Linq aliasToBean();
	
	Linq aliasToMap();
	
	Linq aliasToTuple();

	Linq aliasToBean(Class<?> resultClass);

	<T> org.springframework.data.domain.Page<T> findAll(Pageable pageable);





}