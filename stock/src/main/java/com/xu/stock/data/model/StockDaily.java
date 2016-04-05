package com.xu.stock.data.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author lunan.xu 股票每日指数
 */
public class StockDaily implements Serializable, Comparable<StockDaily> {
	private static final long serialVersionUID = 2643489463991553829L;

	// 股票每日指数Id
	private Long dailyId;
	// 股票Id
	private Integer stockId;
	// 股票代码
	private String stockCode;
	// 股票名称
	private String stockName;
	// 日期
	private Date date;
	// 昨天收盘价
	private BigDecimal lastClose;
	// 开盘价
	private BigDecimal open;
	// 收盘价
	private BigDecimal close;
	// 涨跌额
	private BigDecimal closeGap;
	// 涨跌幅
	private BigDecimal closeGapRate;
	// 最高价
	private BigDecimal high;
	// 最低价
	private BigDecimal low;
	// 最高价与开盘价差价 high-open
	private BigDecimal highGap;
	// 最高价与开盘价相差比例 (high-open)/open
	private BigDecimal highGapRate;
	// 最低价与开盘价差价 低 low-open
	private BigDecimal lowGap;
	// 最低价与开盘价相差比例 (low-open)/open
	private BigDecimal lowGapRate;
	// 成交额
	private Long amount;
	// 成交量
	private Long volume;
	// 总资产
	private Long asset;
	// 创建日期
	private Date created;
	// 更新日期
	private Date updated;
	// 是否除权
	private Boolean isExrights;

	private Integer exrights;

	public Integer getExrights() {
		return exrights;
	}

	public void setExrights(Integer exrights) {
		this.exrights = exrights;
	}

	public Long getDailyId() {
		return dailyId;
	}

	public void setDailyId(Long dailyId) {
		this.dailyId = dailyId;
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

	public BigDecimal getLastClose() {
		return lastClose;
	}

	public void setLastClose(BigDecimal lastClose) {
		this.lastClose = lastClose;
	}

	public BigDecimal getOpen() {
		return open;
	}

	public void setOpen(BigDecimal open) {
		this.open = open;
	}

	public BigDecimal getClose() {
		return close;
	}

	public void setClose(BigDecimal close) {
		this.close = close;
	}

	public BigDecimal getCloseGap() {
		return closeGap;
	}

	public void setCloseGap(BigDecimal closeGap) {
		this.closeGap = closeGap;
	}

	public BigDecimal getCloseGapRate() {
		return closeGapRate;
	}

	public void setCloseGapRate(BigDecimal closeGapRate) {
		this.closeGapRate = closeGapRate;
	}

	public BigDecimal getHigh() {
		return high;
	}

	public void setHigh(BigDecimal high) {
		this.high = high;
	}

	public BigDecimal getLow() {
		return low;
	}

	public void setLow(BigDecimal low) {
		this.low = low;
	}

	public BigDecimal getHighGap() {
		return highGap;
	}

	public void setHighGap(BigDecimal highGap) {
		this.highGap = highGap;
	}

	public BigDecimal getHighGapRate() {
		return highGapRate;
	}

	public void setHighGapRate(BigDecimal highGapRate) {
		this.highGapRate = highGapRate;
	}

	public BigDecimal getLowGap() {
		return lowGap;
	}

	public void setLowGap(BigDecimal lowGap) {
		this.lowGap = lowGap;
	}

	public BigDecimal getLowGapRate() {
		return lowGapRate;
	}

	public void setLowGapRate(BigDecimal lowGapRate) {
		this.lowGapRate = lowGapRate;
	}

	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

	public Long getVolume() {
		return volume;
	}

	public void setVolume(Long volume) {
		this.volume = volume;
	}

	public Long getAsset() {
		return asset;
	}

	public void setAsset(Long asset) {
		this.asset = asset;
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

	public Boolean getIsExrights() {
		return isExrights;
	}

	public void setIsExrights(Boolean isExrights) {
		this.isExrights = isExrights;
	}

	@Override
	public String toString() {
		return "StockDaily [dailyId=" + dailyId + ", stockId=" + stockId + ", stockCode=" + stockCode + ", stockName="
				+ stockName + ", date=" + date + ", lastClose=" + lastClose + ", open=" + open + ", close=" + close
				+ ", closeGap=" + closeGap + ", closeGapRate=" + closeGapRate + ", high=" + high + ", low=" + low
				+ ", highGap=" + highGap + ", highGapRate=" + highGapRate + ", lowGap=" + lowGap + ", lowGapRate="
				+ lowGapRate + ", amount=" + amount + ", volume=" + volume + ", asset=" + asset + ", created=" + created
				+ ", updated=" + updated + ", isExrights=" + isExrights + ", exrights=" + exrights + "]";
	}

	public int compareTo(StockDaily stockDaily) {
		return this.getDate().compareTo(stockDaily.getDate());
	}

}