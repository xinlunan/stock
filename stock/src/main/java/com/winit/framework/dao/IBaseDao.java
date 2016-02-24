package com.winit.framework.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.winit.framework.model.Pager;

/**
 * 顶层DAO接口
 * 
 * @author BW
 * @date 2014-6-30
 */
public interface IBaseDao<T extends Serializable> {

	/**
	 * 新增对象
	 * 
	 * @param entity
	 * @return
	 */
	public int add(T entity);

	/**
	 * 删除对象
	 * 
	 * @param obj
	 * @return
	 */
	public int delete(Serializable id);

	/**
	 * 更新对象
	 * 
	 * @param entity
	 * @return
	 */
	public int update(T entity);

	/**
	 * 根据ID获取对象
	 * 
	 * @param clz
	 * @param id
	 * @return
	 */
	public T get(Serializable id);

	/**
	 * 分页查询
	 * 
	 * @param parameterMap
	 * @return
	 */
	public Pager<T> find(Map<String, Object> parameterMap);

	/**
	 * 分页查询，通过sqlId
	 * 
	 * @param sqlId
	 * @param parameterMap
	 * @return
	 */
	public Pager<T> find(String sqlId, Map<String, Object> parameterMap);

	/**
	 * 分页查询，通过sqlId
	 * 
	 * @param sqlId
	 * @param parameterMap
	 * @param startIndex
	 * @param pageSize
	 * @return
	 */
	public Pager<T> find(String sqlId, Map<String, Object> parameterMap, int startIndex, int pageSize);

	/**
	 * 查询，将结果按Map返回
	 * 
	 * @param sqlId
	 * @param parameterMap
	 * @param mapKeyField
	 * @return
	 */
	public Map<Serializable, T> getMap(String sqlId, Map<String, Object> parameterMap, String mapKeyField);

	/**
	 * 查询，返回List
	 * 
	 * @param sqlId
	 * @param parameterMap
	 * @return
	 */
	public List<T> getList(String sqlId, Object parameters);

}
