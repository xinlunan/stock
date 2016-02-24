package com.winit.framework.dao;

import com.winit.framework.model.SyncLock;

public interface ISyncLockDao {
	/**
	 * 已经上锁
	 */
	public static final String LOCKED = "LOCKED";
	/**
	 * 没上锁
	 */
	public static final String UNLOCKED = "UNLOCKED";
	

	/**
	 * 获得锁对象TODO Add comments here.
	 * 
	 * @param syncLock
	 * @return false:没有锁 true：有锁了
	 */
	public SyncLock getLock(String lockTable, String lockId);

	/**
	 * 新建锁 TODO Add comments here.
	 * 
	 * @param syncLock
	 * @return false：没加锁上 true：加锁成功
	 */
	public Integer createLock(SyncLock syncLock);

	/**
	 * 加锁 TODO Add comments here.
	 * 
	 * @param syncLock
	 * @return false：没加锁上 true：加锁成功
	 */
	public Integer Lock(String lockTable, String lockId, String operator, String remark);

	/**
	 * 解锁 TODO Add comments here.
	 * 
	 * @param syncLock
	 * @return false：解锁失败 true：解锁成功
	 */
	public Integer unLock(String lockTable, String lockId, String operator, String remark);
}
