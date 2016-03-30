package com.xu.stock.data.model;

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
public class StockMinuteIndex implements Serializable {
	private static final long serialVersionUID = -352228891672124416L;

	// 股票每分钟指数Id
	private Long indexId;
	// 股票Id
	private Integer stockId;
	// 股票代码
	private String stockCode;
	// 日期
	private Date date;
	// 小时
	private Integer hour;
	// 分钟
	private Integer minute;
	// 最低价
	private Integer price;
	// 创建日期
	private Date created;
	// 更新日期
	private Date updated;

	public Long getIndexId() {
		return indexId;
	}

	public void setIndexId(Long indexId) {
		this.indexId = indexId;
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
		return "StockMinuteIndex [indexId=" + indexId + ", stockId=" + stockId + ", stockCode=" + stockCode + ", date="
				+ date + ", hour=" + hour + ", minute=" + minute + ", price=" + price + ", created=" + created
				+ ", updated=" + updated + "]";
	}

}
