package com.bstek.bdf3.dorado.jpa;

import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.metamodel.SingularAttribute;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import com.bstek.bdf3.dorado.jpa.lin.Linq;
import com.bstek.bdf3.dorado.jpa.lin.impl.LinqImpl;
import com.bstek.bdf3.dorado.jpa.policy.CriteriaPolicy;
import com.bstek.bdf3.dorado.jpa.policy.SaveContext;
import com.bstek.bdf3.dorado.jpa.policy.SavePolicy;
import com.bstek.bdf3.dorado.jpa.policy.impl.DirtyTreeSavePolicy;
import com.bstek.bdf3.jpa.lin.Lind;
import com.bstek.bdf3.jpa.lin.Linu;
import com.bstek.dorado.data.entity.EntityUtils;


/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2016年1月31日
 */
public abstract class JpaUtil {
	
	private static CriteriaPolicy defaultQBCCriteriaPolicy;
	
	private static SavePolicy defaultSavePolicy;

	
	public static CriteriaPolicy getDefaultQBCCriteriaPolicy() {
		return defaultQBCCriteriaPolicy;
	}
	
	public static void setDefaultQBCCriteriaPolicy(CriteriaPolicy criteriaPolicy) {
		defaultQBCCriteriaPolicy = criteriaPolicy;
	}
	
	public static void setDefaultSavePolicy(SavePolicy savePolicy) {
		defaultSavePolicy = savePolicy;
	}
	
	public static <T> Linq linq(Class<T> domainClass) {
		return new LinqImpl(domainClass);
	}
	
	public static <T> Linq linq(Class<T> domainClass, Class<?> resultClass) {
		return new LinqImpl(domainClass, resultClass);
	}
	
	public static Lind lind(Class<?> domainClass) {
		return com.bstek.bdf3.jpa.JpaUtil.lind(domainClass);
	}
	
	public static Linu linu(Class<?> domainClass) {
		return com.bstek.bdf3.jpa.JpaUtil.linu(domainClass);
	}
	
	/**
	 * 智能数据保存。
	 * @param entityOrEntityList 实体类对象或者实体类集合
	 */
	public static void save(Object entityOrEntityList) {
		Class<?> entityClass = GenricTypeUtils.getGenricType(entityOrEntityList);
		EntityManager entityManager = getEntityManager(entityClass);
		SaveContext context = new SaveContext();
		context.setEntity(entityOrEntityList);
		context.setEntityManager(entityManager);
		defaultSavePolicy.apply(context);
		
	}
	
	/**
	 * 智能数据保存。
	 * <p>
	 * 方法内部会根据实体类智能判断所属EntityManager，<br>
	 * 只有当某个实体类归属于多个EntityManager的情况下，不可以使用此方法<br>
	 * 同时智能提取立体数据模型中的各个层级的实体数据，交由参数savePolicy来处理后继的持久化操作。
	 * </p>
	 * 
	 * @param entityOrEntityList 实体类对象或者实体类集合
	 * @param savePolicy 保存策略
	 */
	public static void save(Object entityOrEntityList, SavePolicy savePolicy) {
		Assert.notNull(savePolicy, "savePolicy can not be null!");;
		Class<?> entityClass = GenricTypeUtils.getGenricType(entityOrEntityList);
		EntityManager entityManager = getEntityManager(entityClass);
		DirtyTreeSavePolicy dirtyTreeSavePolicy = new DirtyTreeSavePolicy();
		dirtyTreeSavePolicy.setSavePolicy(savePolicy);
		SaveContext context = new SaveContext();
		context.setEntity(entityOrEntityList);
		context.setEntityManager(entityManager);
		dirtyTreeSavePolicy.apply(context);
	}
	
	public static <T> T persist(T entity) {
		EntityManager em = getEntityManager(GenricTypeUtils.getGenricType(entity));
		em.persist(entity);
		return entity;
	}
	
	public static <T> T merge(T entity) {
		EntityManager em = getEntityManager(GenricTypeUtils.getGenricType(entity));
		return em.merge(entity);
	}
	
	public static <T> List<T> persist(Iterable<? extends T> entities) {
		List<T> result = new ArrayList<T>();

		if (entities == null) {
			return result;
		}

		for (T entity : entities) {
			result.add(persist(entity));
		}

		return result;
	}
	
	public static <T> List<T> merge(Iterable<? extends T> entities) {
		List<T> result = new ArrayList<T>();

		if (entities == null) {
			return result;
		}

		for (T entity : entities) {
			result.add(merge(entity));
		}

		return result;
	}
	
	public static <T> T persistAndFlush(T entity) {
		T result = persist(entity);
		flush(entity);

		return result;
	}
	
	public static <T> T mergeAndFlush(T entity) {
		T result = merge(entity);
		flush(entity);

		return result;
	}
	
	public static <T> void remove(T entity) {
		EntityManager em = getEntityManager(GenricTypeUtils.getGenricType(entity));
		em.remove(entity);
	}
	
	public static <T> void remove(Iterable<? extends T> entities) {

		Assert.notNull(entities, "The given Iterable of entities not be null!");

		for (T entity : entities) {
			remove(entity);
		}
	}
	
	public static <T> void removeAll(Class<T> domainClass) {
		com.bstek.bdf3.jpa.JpaUtil.removeAll(domainClass);
	}
	
	public static <T> void removeAllInBatch(Class<T> domainClass) {
		com.bstek.bdf3.jpa.JpaUtil.removeAllInBatch(domainClass);
	}
	
	public static <T, ID extends Serializable> T getOne(Class<T> domainClass, ID id) {
		return com.bstek.bdf3.jpa.JpaUtil.getOne(domainClass, id);
	}
	
	public static <T> T findOne(Class<T> domainClass) {
		return com.bstek.bdf3.jpa.JpaUtil.findOne(domainClass);
	}
	
	public static <T> List<T> findAll(Class<T> domainClass) {
		return com.bstek.bdf3.jpa.JpaUtil.findAll(domainClass);
	}
	
	public static <T> List<T> findAll(CriteriaQuery<T> cq) {
		return com.bstek.bdf3.jpa.JpaUtil.findAll(cq);
	}
	
	public static <T> Page<T> findAll(Class<T> domainClass, Pageable pageable) {
		return com.bstek.bdf3.jpa.JpaUtil.findAll(domainClass, pageable);
	}
	
	public static <T> Page<T> findAll(CriteriaQuery<T> cq, Pageable pageable) {
		return com.bstek.bdf3.jpa.JpaUtil.findAll(cq, pageable);
	}
	
	public static <T> EntityManager getEntityManager(T entity) {
		return com.bstek.bdf3.jpa.JpaUtil.getEntityManager(GenricTypeUtils.getGenricType(entity));
	}
	
	public static <T> EntityManager getEntityManager(Class<T> domainClass) {
		return com.bstek.bdf3.jpa.JpaUtil.getEntityManager(domainClass);
	}
	
	public static <T> EntityManagerFactory getEntityManagerFactory(Class<T> domainClass) {
		return com.bstek.bdf3.jpa.JpaUtil.getEntityManagerFactory(domainClass);
	}
	
	public static <T> boolean isEntityClass(Class<T> domainClass) {
		return com.bstek.bdf3.jpa.JpaUtil.isEntityClass(domainClass);
	}
	
	public static <T> Long count(Class<T> domainClass) {
		return com.bstek.bdf3.jpa.JpaUtil.count(domainClass);
	}
	
	public static <T> Long count(CriteriaQuery<T> cq) {
		return com.bstek.bdf3.jpa.JpaUtil.count(cq);
	}
	
	public static <T> boolean exists(Class<T> domainClass) {
		return com.bstek.bdf3.jpa.JpaUtil.exists(domainClass);
	}
	
	public static <T> boolean exists(CriteriaQuery<T> cq) {
		return count(cq) > 0;
	}
	
	public static <T> void flush(T entity) {
		Assert.notNull(entity);
		EntityManager em = getEntityManager(entity.getClass());
		em.flush();
	}
	
	public static <T> void flush(Class<T> domainClass) {
		com.bstek.bdf3.jpa.JpaUtil.flush(domainClass);
	}
	
	public static Long executeCountQuery(TypedQuery<Long> query) {
		return com.bstek.bdf3.jpa.JpaUtil.executeCountQuery(query);
	}
	
	/**
	 * 根据属性收集属性对应的数据
	 * @param source 源
	 * @param propertyName 属性名
	 * @param <T> 范型
	 * @return source集合每个对象的propertyName属性值的一个集合
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <T> Set<T> collect(Collection<?> source, String propertyName) {
		if (CollectionUtils.isEmpty(source)) {
			return Collections.EMPTY_SET;
		}
		Set result = new HashSet(source.size());

		for (Object obj : source) {
			Object value = null;
			if (obj instanceof Map) {
				value = ((Map) obj).get(propertyName);
			} else if (obj instanceof Tuple) {
				value = ((Tuple) obj).get(propertyName);
			} else if (EntityUtils.isEntity(obj)) {
				value = EntityUtils.getValue(obj, propertyName);
			} else if (obj != null) {
				PropertyDescriptor pd = BeanUtils.getPropertyDescriptor(obj.getClass(), propertyName);
				try {
					value = pd.getReadMethod().invoke(obj, new Object[]{});
				} catch (IllegalAccessException | IllegalArgumentException
						| InvocationTargetException e) {
					e.printStackTrace();
				}
			}
			if (value != null) {
				result.add(value);
			}
		}
		return result;
	}
	
	/**
	 * 根据属性收集属性对应的数据
	 * @param source 源
	 * @param <T> 领域类（实体类）范型
	 * @return source集合每个对象的propertyName属性值的一个集合
	 */
	@SuppressWarnings({"unchecked"})
	public static <T> Set<T> collectId(Collection<?> source) {
		if (CollectionUtils.isEmpty(source)) {
			return Collections.EMPTY_SET;
		}
		String idName = getIdName(source.iterator().next().getClass());
		return collect(source, idName);
	}
	
	/**
	 * source转Map，Key为propertyName对应的值，Value为source中propertyName属性值相同的元素
	 * @param source 源
	 * @param propertyName 属性名
	 * @param <K> propertyName对应的属性的类型
	 * @param <V> source集合元素类型
	 * @return 分类Map
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <K, V> Map<K, List<V>> classify(Collection<V> source, String propertyName) {
		if (CollectionUtils.isEmpty(source)) {
			return Collections.EMPTY_MAP;
		}
		Map result = new HashMap();

		for (Object obj : source) {
			Object value = getValue(obj, propertyName);
			Object target = result.get(value);
			if (target != null) {
				((List) target).add(obj);
			} else {
				List list = new ArrayList();
				list.add(obj);
				result.put(value, list);
			}
		}
		return result;
	}
	
	/**
	 * source转Map，Key为source元素的propertyName属性值，Value为该元素
	 * @param source 源集合
	 * @param propertyName 属性名
	 * @param <K> propertyName对应的属性的类型
	 * @param <V> source集合元素类型
	 * @return 索引Map
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <K, V> Map<K, V> index(Collection<V> source, String propertyName) {
		if (CollectionUtils.isEmpty(source)) {
			return Collections.EMPTY_MAP;
		}
		Map result = new HashMap();

		for (Object obj : source) {
			Object value = getValue(obj, propertyName);
			result.put(value, obj);
		}
		return result;
	}
	
	@SuppressWarnings("rawtypes")
	public static Object getValue(Object obj, String propertyName) {
		Object value = null;
		if (obj instanceof Map) {
			value = ((Map) obj).get(propertyName);
		} else if (obj instanceof Tuple) {
			value = ((Tuple) obj).get(propertyName);
		} else if (EntityUtils.isEntity(obj)) {
			value = EntityUtils.getValue(obj, propertyName);
		}  else if (obj != null) {
			PropertyDescriptor pd = BeanUtils.getPropertyDescriptor(obj.getClass(), propertyName);
			try {
				value = pd.getReadMethod().invoke(obj, new Object[]{});
			} catch (IllegalAccessException | IllegalArgumentException
					| InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		return value;
	}
	
	/**
	 * source转Map，Key为source元素主键属性属性值，Value为该元素
	 * @param source 源集合
	 * @param <K> propertyName对应的属性的类型
	 * @param <V> source集合元素类型
	 * @return 索引Map
	 */
	@SuppressWarnings("unchecked")
	public static <K, V> Map<K, V> index(Collection<V> source) {
		if (CollectionUtils.isEmpty(source)) {
			return Collections.EMPTY_MAP;
		}
		String idName = getIdName(source.iterator().next().getClass());
		return index(source, idName);

	}
	
	public static <T> TypedQuery<Long> getCountQuery(CriteriaQuery<T> cq) {
		return com.bstek.bdf3.jpa.JpaUtil.getCountQuery(cq);
	}
	
	public static <T> String getIdName(Class<T> domainClass) {
		return com.bstek.bdf3.jpa.JpaUtil.getIdName(domainClass);
	}
	
	public static <T> SingularAttribute<? super T, ?> getId(Class<T> domainClass) {
		return com.bstek.bdf3.jpa.JpaUtil.getId(domainClass);
	}
}
