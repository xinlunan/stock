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
public class StockTrade implements Serializable {
	private static final long serialVersionUID = -352228891672124416L;

	// 股票每分钟指数Id
	private Long tradeId;
	// 股票Id
	private Integer stockId;
	// 股票代码
	private String stockCode;
	// 股票名称
	private String stockName;
	// 日期
	private Date date;
	// 小时
	private Integer hour;
	// 分钟
	private Integer minute;
	// 最低价
	private Integer price;
	// 交易类型
	private String tradeType;
	// 交易性质
	private String tradeNature;
	// 策略
	private String strategy;
	// 策略版本
	private Integer version;
	// 参数
	private String parameters;
	// 创建日期
	private Date created;
	// 更新日期
	private Date updated;

	public Long getTradeId() {
		return tradeId;
	}

	public void setTradeId(Long tradeId) {
		this.tradeId = tradeId;
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

	public Integer getHour() {
		return hour;
	}

	public void setHour(Integer hour) {
		this.hour = hour;
	}

	public Integer getMinute() {
		return minute;
	}

	public void setMinute(Integer minute) {
		this.minute = minute;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public String getTradeType() {
		return tradeType;
	}

	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}

	public String getTradeNature() {
		return tradeNature;
	}

	public void setTradeNature(String tradeNature) {
		this.tradeNature = tradeNature;
	}

	public String getStrategy() {
		return strategy;
	}

	public void setStrategy(String strategy) {
		this.strategy = strategy;
	}

	public String getParameters() {
		return parameters;
	}

	public void setParameters(String parameters) {
		this.parameters = parameters;
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

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	@Override
	public String toString() {
		return "StockTrade [tradeId=" + tradeId + ", stockId=" + stockId + ", stockCode=" + stockCode + ", stockName="
				+ stockName + ", date=" + date + ", hour=" + hour + ", minute=" + minute + ", price=" + price
				+ ", tradeType=" + tradeType + ", tradeNature=" + tradeNature + ", strategy=" + strategy + ", version="
				+ version + ", parameters=" + parameters + ", created=" + created + ", updated=" + updated + "]";
	}

}
