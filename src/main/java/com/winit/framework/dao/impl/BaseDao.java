package com.winit.framework.dao.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import com.winit.framework.dao.IBaseDao;
import com.winit.framework.log.BaseLog;
import com.winit.framework.model.PageContext;
import com.winit.framework.model.Pager;
import com.winit.framework.util.GenericsUtils;

/**
 * 顶层DAO接口
 * 
 * @author BW
 * @date 2014-6-30
 * @typeName BaseDAO
 */
@Service("baseDao")
public abstract class BaseDao<T extends Serializable> implements IBaseDao<T> {
	protected static final BaseLog log = BaseLog.getLogger(BaseDao.class);

	public static final String SUFFIX_INSERT = ".insert";
	public static final String SUFFIX_UPDATE = ".update";
	public static final String SUFFIX_DELETE = ".delete";
	public static final String SUFFIX_DELETE_BY_ID = ".deleteById";
	public static final String SUFFIX_GET_BY_ID = ".getById";
	public static final String SUFFIX_FIND = ".find";
	public static final String SUFFIX_COUNT = ".count";

	@Resource
	private SqlSession sqlSession;

	public SqlSession getSqlSession() {
		return sqlSession;
	}

	@SuppressWarnings("unchecked")
	protected Class<T> entityClass = GenericsUtils.getSuperClassGenricType(this.getClass());

	protected String getNameSpace() {
		return entityClass.getName() + ".";
	}

	@Override
	public int add(T entity) {
		return getSqlSession().insert(entityClass.getName() + SUFFIX_INSERT, entity);
	}

	@Override
	public int delete(Serializable id) {
		return getSqlSession().delete(entityClass.getName() + SUFFIX_DELETE_BY_ID, id);
	}

	@Override
	public int update(T entity) {
		return getSqlSession().update(entityClass.getName() + SUFFIX_UPDATE, entity);
	}

	@Override
	@SuppressWarnings("unchecked")
	public T get(Serializable id) {
		return (T) getSqlSession().selectOne(entityClass.getName() + SUFFIX_GET_BY_ID, id);
	}

	@Override
	public Pager<T> find(Map<String, Object> parameterMap) {
		return this.find(entityClass.getName() + SUFFIX_FIND, parameterMap);
	}

	@Override
	public Pager<T> find(String sqlId, Map<String, Object> parameterMap) {
		return find(sqlId, parameterMap, PageContext.getStartIndex(), PageContext.getPageSize());
	}

	@Override
	public Pager<T> find(String sqlId, Map<String, Object> parameterMap, int startIndex, int pageSize) {
		pageSize = pageSize == 0 ? 20 : pageSize;
		if (parameterMap == null) {
			parameterMap = new HashMap<String, Object>();
		}
		parameterMap.put("sortField", PageContext.getSortField());
		parameterMap.put("sortType", PageContext.getSortType());

		List<T> result = getSqlSession().selectList(sqlId, parameterMap, new RowBounds(startIndex, pageSize));
		Integer totalRecord = getSqlSession().selectOne(sqlId + SUFFIX_COUNT, parameterMap);

		Pager<T> pager = new Pager<T>();
		pager.setResult(result);
		pager.setTotalRecord(totalRecord);
		pager.setStartIndex(startIndex);
		pager.setPageSize(pageSize);

		return pager;
	}

	@Override
	public Map<Serializable, T> getMap(String sqlId, Map<String, Object> parameterMap, String mapKeyField) {
		return getSqlSession().selectMap(sqlId, parameterMap, mapKeyField);
	}

	@Override
	public List<T> getList(String sqlId, Object parameters) {
		return getSqlSession().selectList(sqlId, parameters);
	}

}
