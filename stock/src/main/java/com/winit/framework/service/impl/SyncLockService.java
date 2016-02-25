package com.winit.framework.service.impl;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.winit.framework.dao.ISyncLockDao;
import com.winit.framework.dao.impl.SyncLockDao;
import com.winit.framework.log.BaseLog;
import com.winit.framework.model.SyncLock;
import com.winit.framework.service.ISyncLockService;

@Service("syncLockService")
public class SyncLockService extends BaseService implements ISyncLockService {
	private BaseLog log = BaseLog.getLogger(SyncLockService.class);
	@Resource
	ISyncLockDao syncLockDao;

	public boolean tryLock(String lockTable, String lockId, String operator, String remark) {
		boolean isLocked = false;
		try {
			SyncLock tempLock = syncLockDao.getLock(lockTable, lockId);
			if (tempLock != null) {
				long stopMinutes = ((new Date()).getTime() - tempLock.getUpdated().getTime())/(1000 * 60);
				if (ISyncLockDao.LOCKED.equals(tempLock.getStatus()) && stopMinutes < 20) {
					return false;
				} else {
					syncLockDao.Lock(lockTable, lockId, operator, remark);
				}

			} else {
				SyncLock syncLock = createSyncLock(lockTable, lockId, operator, remark);
				syncLockDao.createLock(syncLock);
			}
			isLocked = true;
		} catch (Exception e) {
			log.info("SyncLockService lock exception：", e);
		}

		return isLocked;
	}
	
	private SyncLock createSyncLock(String lockTable, String lockId, String operator, String remark) {
		SyncLock syncLock = new SyncLock();
		syncLock.setCreated(new Date());
		syncLock.setIp(getHostIpAddress());
		syncLock.setLockId(lockId);
		syncLock.setLockTable(lockTable);
		syncLock.setOperator(operator);
		syncLock.setPort("");
		syncLock.setStatus(SyncLockDao.LOCKED);
		syncLock.setUpdated(new Date());
		syncLock.setRemark(remark);
		return syncLock;
	}

	public boolean unLock(String lockTable, String lockId, String operator, String remark) {
		Integer count = syncLockDao.unLock(lockTable, lockId, operator, remark);
		return (count == null || count < 1) ? false : true;
	}

	public String getHostIpAddress() {
		InetAddress localhost = null;
		try {
			localhost = InetAddress.getLocalHost();
		} catch (UnknownHostException uhe) {
			log.info("====Localhost not seeable. Something is odd. =====");
		}
		return localhost.getHostAddress();
	}

}
