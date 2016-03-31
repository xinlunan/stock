package com.xu.stock.analyse.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 股票分时指数
 * 
 * @version
 * 
 * 			<pre>
 * Author	Version		Date		Changes
 * lunan.xu 	1.0  		2016年3月30日 	Created
 *
 *          </pre>
 * 
 * @since 1.
 */
public class StockAnalyseStrategy implements Serializable {
	private static final long serialVersionUID = -1687096421228533000L;
	// 策略ID
	private Long strategyId;
	// 策略类型
	private String strategyType;
	// 版本
	private String version;
	// 参数
	private String prameters;
	// 创建日期
	private Date created;
	// 更新日期
	private Date updated;

	public Long getStrategyId() {
		return strategyId;
	}

	public void setStrategyId(Long strategyId) {
		this.strategyId = strategyId;
	}

	public String getStrategyType() {
		return strategyType;
	}

	public void setStrategyType(String strategyType) {
		this.strategyType = strategyType;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getPrameters() {
		return prameters;
	}

	public void setPrameters(String prameters) {
		this.prameters = prameters;
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

	@Override
	public String toString() {
		return "StockAnanlyseStrategy [strategyId=" + strategyId + ", strategyType=" + strategyType + ", version="
				+ version + ", prameters=" + prameters + ", created=" + created + ", updated=" + updated + "]";
	}

}
