package com.winit.framework.service;


public interface ISyncLockService {
	/**
	 * 加锁 TODO Add comments here.
	 * 
	 * @param syncLock
	 * @return false：没加锁上 true：加锁成功
	 */
	public boolean tryLock(String lockTable, String lockId, String operator, String remark);

	/**
	 * 解锁 TODO Add comments here.
	 * 
	 * @param syncLock
	 * @return false：解锁失败 true：解锁成功
	 */
	public boolean unLock(String lockTable, String lockId, String operator, String remark);
}
