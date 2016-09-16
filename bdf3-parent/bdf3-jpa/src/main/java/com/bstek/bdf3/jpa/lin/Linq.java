package com.bstek.bdf3.jpa.lin;

import java.util.List;

import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * 语言集成查询
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2016年1月31日
 */
public interface Linq extends Lin<Linq, CriteriaQuery<?>>{

	/**
	 * 查询数据（永不为null）
	 * @param <T> 领域类（实体类）范型
	 * @return 结果集合
	 */
	<T> List<T> findAll();

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
	<T> Page<T> findAll(Pageable pageable);

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


	
}
