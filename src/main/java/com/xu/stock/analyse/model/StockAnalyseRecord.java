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
public class StockAnalyseRecord implements Serializable {
	private static final long serialVersionUID = -352228891672124416L;

	// 股票每分钟指数Id
	private Long recordId;
	// 股票Id
	private Integer stockId;
	// 股票代码
	private String stockCode;
	// 股票名称
	private String stockName;
	// 日期
	private Date date;
	// 交易类型
	private String tradeType;
	// 策略
	private String strategy;
	// 策略版本
	private Integer version;
	// 创建日期
	private Date created;
	// 更新日期
	private Date updated;

	public Long getRecordId() {
		return recordId;
	}

	public void setRecordId(Long recordId) {
		this.recordId = recordId;
	}

	public Integer getStockId() {
		return stockId;
	}

	public void setStockId(Integer stockId) {
		this.stockId = stockId;
	}

	public String getStockCode() {
		return stockCode;
	}

	public void setStockCode(String stockCode) {
		this.stockCode = stockCode;
	}

	public String getStockName() {
		return stockName;
	}

	public void setStockName(String stockName) {
		this.stockName = stockName;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getTradeType() {
		return tradeType;
	}

	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}

	public String getStrategy() {
		return strategy;
	}

	public void setStrategy(String strategy) {
		this.strategy = strategy;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "StockAnalyseRecord [recordId=" + recordId + ", stockId=" + stockId + ", stockCode=" + stockCode
				+ ", stockName=" + stockName + ", date=" + date + ", tradeType=" + tradeType + ", strategy=" + strategy
				+ ", version=" + version + ", created=" + created + ", updated=" + updated + "]";
	}

}
