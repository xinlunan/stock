package com.winit.framework.model;

import java.util.Date;

import com.winit.framework.dao.impl.SyncLockDao;

public class SyncLock extends BaseEntity {
	private static final long serialVersionUID = 127676178857773332L;
	private Long id;
	private String lockId;
	private String lockTable;
	private String status;
	private String operator;
	private String ip;
	private String port;
	private String remark;
	private Date created;
	private Date updated;

	@SuppressWarnings("unused")
	private String table = SyncLockDao.BASE_TABLE_NAME;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public String getLockId() {
		return lockId;
	}

	public void setLockId(String lockId) {
		this.lockId = lockId;
	}
	public String getLockTable() {
		return lockTable;
	}

	public void setLockTable(String LockTable) {
		this.lockTable = LockTable;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	public Date getUpdated() {
		return updated;
	}
	public void setUpdated(Date updated) {
		this.updated = updated;
	}
}
