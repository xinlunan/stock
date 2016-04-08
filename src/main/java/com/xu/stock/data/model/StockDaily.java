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
    private Long              dailyId;
    // 股票代码
    private String            stockCode;
    // 股票名称
    private String            stockName;
    // 日期
    private Date              date;
    // 昨天收盘价
    private Double        lastClose;
    // 开盘价
    private Double        open;
    // 收盘价
    private Double        close;
    // 涨跌额
    private Double        closeGap;
    // 涨跌幅
    private Double        closeGapRate;
    // 最高价
    private Double        high;
    // 最低价
    private Double        low;
    // 最高价与开盘价差价 high-open
    private Double        highGap;
    // 最高价与开盘价相差比例 (high-open)/open
    private Double        highGapRate;
    // 最低价与开盘价差价 低 low-open
    private Double        lowGap;
    // 最低价与开盘价相差比例 (low-open)/open
    private Double        lowGapRate;
    // 除权系数
    private Double        exrights;
    // 本次除权系数
    private Double        thisExrights;
    // 成交额
    private Double        amount;
    // 成交量
    private Double        volume;
    // 总资产
    private Long              asset;
    // 创建日期
    private Date              created;
    // 更新日期
    private Date              updated;

    public BigDecimal getExrights() {
        return BigDecimal.valueOf(exrights);
    }

    public void setExrights(BigDecimal exrights) {
        this.exrights = exrights.doubleValue();
    }

    public Long getDailyId() {
        return dailyId;
    }

    public void setDailyId(Long dailyId) {
        this.dailyId = dailyId;
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
        return BigDecimal.valueOf(lastClose);
    }

    public void setLastClose(BigDecimal lastClose) {
        this.lastClose = lastClose.doubleValue();
    }

    public BigDecimal getOpen() {
        return BigDecimal.valueOf(open);
    }

    public void setOpen(BigDecimal open) {
        this.open = open.doubleValue();
    }

    public BigDecimal getClose() {
        return BigDecimal.valueOf(close);
    }

    public void setClose(BigDecimal close) {
        this.close = close.doubleValue();
    }

    public BigDecimal getCloseGap() {
        return BigDecimal.valueOf(closeGap);
    }

    public void setCloseGap(BigDecimal closeGap) {
        this.closeGap = closeGap.doubleValue();
    }

    public BigDecimal getCloseGapRate() {
        return BigDecimal.valueOf(closeGapRate);
    }

    public void setCloseGapRate(BigDecimal closeGapRate) {
        this.closeGapRate = closeGapRate.doubleValue();
    }

    public BigDecimal getHigh() {
        return BigDecimal.valueOf(high);
    }

    public void setHigh(BigDecimal high) {
        this.high = high.doubleValue();
    }

    public BigDecimal getLow() {
        return BigDecimal.valueOf(low);
    }

    public void setLow(BigDecimal low) {
        this.low = low.doubleValue();
    }

    public BigDecimal getHighGap() {
        return BigDecimal.valueOf(highGap);
    }

    public void setHighGap(BigDecimal highGap) {
        this.highGap = highGap.doubleValue();
    }

    public BigDecimal getHighGapRate() {
        return BigDecimal.valueOf(highGapRate);
    }

    public void setHighGapRate(BigDecimal highGapRate) {
        this.highGapRate = highGapRate.doubleValue();
    }

    public BigDecimal getLowGap() {
        return BigDecimal.valueOf(lowGap);
    }

    public void setLowGap(BigDecimal lowGap) {
        this.lowGap = lowGap.doubleValue();
    }

    public BigDecimal getLowGapRate() {
        return BigDecimal.valueOf(lowGapRate);
    }

    public void setLowGapRate(BigDecimal lowGapRate) {
        this.lowGapRate = lowGapRate.doubleValue();
    }

    public BigDecimal getAmount() {
        return BigDecimal.valueOf(amount);
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount.doubleValue();
    }

    public BigDecimal getVolume() {
        return BigDecimal.valueOf(volume);
    }

    public void setVolume(BigDecimal volume) {
        this.volume = volume.doubleValue();
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

    public BigDecimal getThisExrights() {
        return BigDecimal.valueOf(thisExrights);
    }

    public void setThisExrights(BigDecimal thisExrights) {
        this.thisExrights = thisExrights.doubleValue();
    }

    @Override
    public String toString() {
        return "StockDaily [dailyId=" + dailyId + ", stockCode=" + stockCode + ", stockName=" + stockName + ", date=" + date + ", lastClose=" + lastClose + ", open=" + open
               + ", close=" + close + ", closeGap=" + closeGap + ", closeGapRate=" + closeGapRate + ", high=" + high + ", low=" + low + ", highGap=" + highGap + ", highGapRate="
               + highGapRate + ", lowGap=" + lowGap + ", lowGapRate=" + lowGapRate + ", exrights=" + exrights + ", thisExrights=" + thisExrights + ", amount=" + amount
               + ", volume=" + volume + ", asset=" + asset + ", created=" + created + ", updated=" + updated + "]";
    }

    public int compareTo(StockDaily stockDaily) {
        return this.getDate().compareTo(stockDaily.getDate());
    }

}
