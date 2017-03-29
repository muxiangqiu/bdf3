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

	/**
	 * 查询数据（永不为null）
	 * @param <T> 领域类（实体类）范型
	 * @return 结果集合
	 */
	<T> List<T> list();

	/**
	 * 降序排列
	 * @param properties 实体属性名
	 * @return 本身
	 */
	Linq desc(String... properties);

	/**
	 * 降序排列
	 * @param expressions JPA Expression
	 * @return 本身
	 */
	Linq desc(Expression<?>... expressions);

	/**
	 * 升序排列
	 * @param properties 实体属性名
	 * @return 本身
	 */
	Linq asc(String... properties);

	/**
	 * 升序排列
	 * @param expressions JPA Expression
	 * @return 本身
	 */
	Linq asc(Expression<?>... expressions);

	/**
	 * 分页查询
	 * @param pageable 分页信息（包含索引页号和页的大小）
	 * @param <T> 领域类（实体类）范型
	 * @return 分页结果（包含索引页号的数据和总记录数）
	 */
	<T> org.springframework.data.domain.Page<T> paging(Pageable pageable);

	/**
	 * 查询记录条数
	 * @return 记录条数
	 */
	Long count();
	
	/**
	 * 查询是否存在记录
	 * @return true 则存在，false 则不存在
	 */
	boolean exists();

	/**
	 * 查询并返回一条记录（必须有一条记录）
	 * @param <T> 领域类（实体类）范型
	 * @return 实体对象
	 */
	<T> T findOne();

	/**
	 * 合并重复数据
	 * @return 本身
	 */
	Linq distinct();
	
	/**
	 * 根据投影别名转Bean（此处为查询对应的领域类）<br>
	 * 注意：<br>
	 * 1.此方法必须在select方法前调用<br>
	 * 2.此方法后必须要调用select以提供别名依据
	 * @return 本身
	 */
	Linq aliasToBean();
	
	/**
	 * 根据投影别名转Map<br>
	 * 注意：<br>
	 * 1.此方法必须在select方法前调用<br>
	 * 2.此方法后必须要调用select以提供别名依据
	 * @return 本身
	 */
	Linq aliasToMap();
	
	/**
	 * 根据投影别名转Tuple<br>
	 * 注意：<br>
	 * 1.此方法必须在select方法前调用<br>
	 * 2.此方法后必须要调用select以提供别名依据
	 * @return 本身
	 */
	Linq aliasToTuple();
	
	/**
	 * 根据投影别名转Bean<br>
	 * 注意：<br>
	 * 1.此方法必须在select方法前调用<br>
	 * 2.此方法后必须要调用select以提供别名依据
	 * @param resultClass Bean Class
	 * @return 本身
	 */
	Linq aliasToBean(Class<?> resultClass);
	
	/**
	 * 分页查询
	 * @param page 分页信息（包含索引页号和页的大小）
	 * @param <T> 领域类（实体类）范型
	 */
	<T> void paging(Page<T> page);
	
	/**
	 * 分页查询，不查询记录总数
	 * @param page 分页信息（包含索引页号和页的大小）
	 * @param <T> 领域类（实体类）范型
	 * @return 结果集合
	 */
	<T> List<T> list(Page<T> page);

	/**
	 * 分页查询，不查询记录总数
	 * @param pageable 分页信息（包含索引页号和页的大小）
	 * @param <T> 领域类（实体类）范型
	 * @return 结果集合
	 */
	<T> List<T> list(Pageable pageable);

	/**
	 * 分页查询，不查询记录总数
	 * @param page 分页号（从0开始）
	 * @param size 分页大小
	 * @param <T> 领域类（实体类）范型
	 * @return 结果集合
	 */
	<T> List<T> list(int page, int size);
	
	/**
	 * 将查询结果转成Dorado实体
	 * @return 本身
	 */
	Linq toEntity();
	
	/**
	 * 设置结果过滤器
	 * @param filter 结果过滤器
	 * @return 本身
	 */
	Linq filter(Filter filter);
	
	/**
	 * 添加条件解析器
	 * @param criterionParser 条件解析器
	 * @return 本身
	 */
	Linq addParser(CriterionParser criterionParser);
	
	/**
	 * 添加子查询解析器
	 * @param entityClass 实体类
	 * @param foreignKeys 实体类外键
	 * @return 本身
	 */
	Linq addSubQueryParser(Class<?> entityClass, String... foreignKeys);

	/**
	 * 添加子查询解析器
	 * @param entityClasses 实体类
	 * @return 本身
	 */
	Linq addSubQueryParser(Class<?>... entityClasses);

	/**
	 * 设置条件
	 * @param criteria 条件
	 * @return 本身
	 */
	Linq where(Criteria criteria);
	
	/**
	 * 获取语言集成查询上下文
	 * @return 本身
	 */
	LinqContext getLinqContext();

	/**
	 * 收集属性对应的值
	 * @param properties 收集属性
	 * @return 本身
	 */
	Linq collect(String... properties);
	
	/**
	 * 根据主键收集entityClass对应的数据，
	 * @param entityClass 实体类
	 * @return 本身
	 */
	Linq collect(Class<?> entityClass);

	/**
	 * 根据主键收集entityClass对应的数据
	 * @param otherProperty entityClass的某个属性，与properties属性存在关联
	 * @param entityClass 实体类
	 * @return 本身
	 */
	Linq collect(String otherProperty, Class<?> entityClass);

	/**
	 * 根据属性收集entityClass对应的数据
	 * @param entityClass 实体类
	 * @param properties 属性
	 * @return 本身
	 */
	Linq collect(Class<?> entityClass, String... properties);
	
	/**
	 * 根据属性收集entityClass对应的数据
	 * @param otherProperty entityClass的某个属性，与properties属性存在关联
	 * @param entityClass 实体类
	 * @param properties 属性
	 * @return 本身
	 */
	Linq collect(String otherProperty, Class<?> entityClass, String... properties);
	
	/**
	 * 根据主键收集entityClass对应的数据
	 * @param entityClass 实体类
	 * @param relationClass 多对多关系中的中间表对应的实体类
	 * @return 本身
	 */
	Linq collect(Class<?> relationClass, Class<?> entityClass);
	
	/**
	 * 根据主键收集entityClass对应的数据
	 * @param entityClass 实体类
	 * @param relationClass 多对多关系中的中间表对应的实体类
	 * @param relationProperty 查询主实体类在中间表对应的实体类中的关联属性
	 * @param relationOtherProperty 收集实体类在中间表对应的实体类中的关联属性
	 * @return 本身
	 */
	Linq collect(Class<?> relationClass, String relationProperty, String relationOtherProperty, Class<?> entityClass);
	
	/**
	 * 根据主键收集entityClass对应的数据
	 * @param entityClass 实体类
	 * @param relationClass 多对多关系中的中间表对应的实体类
	 * @param relationProperty 查询主实体类在中间表对应的实体类中的关联属性
	 * @param relationOtherProperty 收集实体类在中间表对应的实体类中的关联属性
	 * @param otherProperty entityClass的某个属性，与properties属性存在关联
	 * @return 本身
	 */
	Linq collect(Class<?> relationClass, String relationProperty, String relationOtherProperty, String otherProperty,
			Class<?> entityClass);
	
	/**
	 * 根据属性收集entityClass对应的数据
	 * @param entityClass 实体类
	 * @param relationClass 多对多关系中的中间表对应的实体类
	 * @param relationProperty 查询主实体类在中间表对应的实体类中的关联属性
	 * @param relationOtherProperty 收集实体类在中间表对应的实体类中的关联属性
	 * @param otherProperty entityClass的某个属性，与properties属性存在关联
	 * @param properties 属性
	 * @return 本身
	 */
	Linq collect(Class<?> relationClass, String relationProperty, String relationOtherProperty, String otherProperty,
			Class<?> entityClass, String... properties);

	/**
	 * 设置收集entityClass对应的数据的投影
	 * @param entityClass 实体类
	 * @param projections 投影
	 * @return 本身
	 */
	Linq collectSelect(Class<?> entityClass, String... projections);

	/**
	 * 禁用智能自查询条件生成
	 * @return 本身
	 */
	Linq setDisableSmartSubQueryCriterion();

	/**
	 *	禁用收集数据回填
	 * @return 本身
	 */
	Linq setDisableBackFillFilter();


}