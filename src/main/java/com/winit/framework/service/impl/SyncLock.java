package com.winit.framework.service.impl;

import javax.annotation.Resource;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.stereotype.Component;

import com.winit.framework.service.ISyncLockService;

@Component("syncLock")
public class SyncLock implements BeanFactoryAware {
	private static BeanFactory beanFactory;
	@Resource
	ISyncLockService syncLockService;
	private static SyncLock syncLock;

	private SyncLock() {

	}
	public static SyncLock getInstance() {
		if (syncLock == null) {
			syncLock = (SyncLock) beanFactory.getBean("syncLock");
		}
		return syncLock;
	}

	/**
	 * 加锁 TODO Add comments here.
	 * 
	 * @param syncLock
	 * @return false：没加锁上 true：加锁成功
	 */
	public boolean tryLock(String lockTable, String lockId, String operator, String remark) {
		return syncLockService.tryLock(lockTable, lockId, operator, remark);
	}

	/**
	 * 解锁 TODO Add comments here.
	 * 
	 * @param syncLock
	 * @return false：解锁失败 true：解锁成功
	 */
	public boolean unLock(String lockTable, String lockId, String operator, String remark) {
		return syncLockService.unLock(lockTable, lockId, operator, remark);
	}

	public void setBeanFactory(BeanFactory factory) throws BeansException {
		this.beanFactory = factory;

	}
	public BeanFactory getBeanFactory() {
		return beanFactory;
	}
}
