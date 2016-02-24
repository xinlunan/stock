package com.winit.framework.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.winit.framework.dao.ISyncLockDao;
import com.winit.framework.model.SyncLock;

@Repository("syncLockDao")
public class SyncLockDao extends BaseDao<SyncLock> implements ISyncLockDao {
	private final String SQL_GET_LOCK = getNameSpace() + "getLock";
	private final String SQL_LOCK = getNameSpace() + "lock";
	private final String SQL_CREATELOCK = getNameSpace() + "createLock";
	private final String SQL_UNLOCK = getNameSpace() + "unLock";
	public static final String BASE_TABLE_NAME = "WT_SYNC_LOCK";

	@Override
	public SyncLock getLock(String lockTable, String lockId) {
		Map<String, Object> paras = new HashMap<String, Object>();
		paras.put("lockTable", lockTable);
		paras.put("lockId", lockId);

		paras.put("table", BASE_TABLE_NAME);
		return getSqlSession().selectOne(SQL_GET_LOCK, paras);
	}

	@Override
	public Integer Lock(String lockTable, String lockId, String operator, String remark) {
		Map<String, Object> paras = new HashMap<String, Object>();
		paras.put("lockTable", lockTable);
		paras.put("lockId", lockId);
		paras.put("operator", operator);
		paras.put("remark", remark);
		paras.put("status", ISyncLockDao.LOCKED);

		paras.put("table", BASE_TABLE_NAME);
		return getSqlSession().update(SQL_LOCK, paras);
	}

	@Override
	public Integer createLock(SyncLock syncLock) {
		return getSqlSession().insert(SQL_CREATELOCK, syncLock);
	}

	@Override
	public Integer unLock(String lockTable, String lockId, String operator, String remark) {
		Map<String, Object> paras = new HashMap<String, Object>();
		paras.put("lockTable", lockTable);
		paras.put("lockId", lockId);
		paras.put("operator", operator);
		paras.put("remark", remark);
		paras.put("status", ISyncLockDao.UNLOCKED);

		paras.put("table", BASE_TABLE_NAME);
		return getSqlSession().update(SQL_UNLOCK, paras);
	}


}
