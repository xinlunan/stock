package com.xu.stock.data.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

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
    private Double            lastClose;
    // 开盘价
    private Double            open;
    // 收盘价
    private Double            close;
    // 涨跌额
    private Double            closeGap;
    // 涨跌幅
    private Double            closeGapRate;
    // 最高价
    private Double            high;
    // 最低价
    private Double            low;
    // 最高价与开盘价差价 high-open
    private Double            highGap;
    // 最高价与开盘价相差比例 (high-open)/open
    private Double            highGapRate;
    // 最低价与开盘价差价 低 low-open
    private Double            lowGap;
    // 最低价与开盘价相差比例 (low-open)/open
    private Double            lowGapRate;
    // 除权系数
    private Double            exrights;
    // 本次除权系数
    private Double            thisExrights;
    // 成交额
    private Double            amount;
    // 成交量
    private Double            volume;
    // 总资产
    private Long              asset;
    // 量比
    private Double            volumeRatio      = -1d;
    // 5日均线
    private Double            ma5              = -1d;
    // 10日均线
    private Double            ma10             = -1d;
    // 20日均线
    private Double            ma20             = -1d;
    // 30日均线
    private Double            ma30             = -1d;
    // 40日均线
    private Double            ma40             = -1d;
    // 50日均线
    private Double            ma50             = -1d;
    // 60日均线
    private Double            ma60             = -1d;
    // 创建日期
    private Date              created;
    // 更新日期
    private Date              updated;
    // 分时信息
    private List<StockMinute> minutes;

    public BigDecimal getExrights() {
        return BigDecimal.valueOf(exrights);
    }

    public Double getExrightsObj() {
        return exrights;
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

    public BigDecimal getVolumeRatio() {
        return BigDecimal.valueOf(volumeRatio);
    }

    public void setVolumeRatio(BigDecimal volumeRatio) {
        this.volumeRatio = volumeRatio.doubleValue();
    }

    public BigDecimal getMa5() {
        return BigDecimal.valueOf(ma5);
    }

    public void setMa5(BigDecimal ma5) {
        this.ma5 = ma5.doubleValue();
    }

    public BigDecimal getMa10() {
        return BigDecimal.valueOf(ma10);
    }

    public void setMa10(BigDecimal ma10) {
        this.ma10 = ma10.doubleValue();
    }

    public BigDecimal getMa20() {
        return BigDecimal.valueOf(ma20);
    }

    public void setMa20(BigDecimal ma20) {
        this.ma20 = ma20.doubleValue();
    }

    public BigDecimal getMa30() {
        return BigDecimal.valueOf(ma30);
    }

    public void setMa30(BigDecimal ma30) {
        this.ma30 = ma30.doubleValue();
    }

    public BigDecimal getMa40() {
        return BigDecimal.valueOf(ma40);
    }

    public void setMa40(BigDecimal ma40) {
        this.ma40 = ma40.doubleValue();
    }

    public BigDecimal getMa50() {
        return BigDecimal.valueOf(ma50);
    }

    public void setMa50(BigDecimal ma50) {
        this.ma50 = ma50.doubleValue();
    }

    public BigDecimal getMa60() {
        return BigDecimal.valueOf(ma60);
    }

    public void setMa60(BigDecimal ma60) {
        this.ma60 = ma60.doubleValue();
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public void setLastClose(Double lastClose) {
        this.lastClose = lastClose;
    }

    public void setOpen(Double open) {
        this.open = open;
    }

    public void setClose(Double close) {
        this.close = close;
    }

    public void setCloseGap(Double closeGap) {
        this.closeGap = closeGap;
    }

    public void setCloseGapRate(Double closeGapRate) {
        this.closeGapRate = closeGapRate;
    }

    public void setHigh(Double high) {
        this.high = high;
    }

    public void setLow(Double low) {
        this.low = low;
    }

    public void setHighGap(Double highGap) {
        this.highGap = highGap;
    }

    public void setHighGapRate(Double highGapRate) {
        this.highGapRate = highGapRate;
    }

    public void setLowGap(Double lowGap) {
        this.lowGap = lowGap;
    }

    public void setLowGapRate(Double lowGapRate) {
        this.lowGapRate = lowGapRate;
    }

    public void setExrights(Double exrights) {
        this.exrights = exrights;
    }

    public void setThisExrights(Double thisExrights) {
        this.thisExrights = thisExrights;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public void setVolume(Double volume) {
        this.volume = volume;
    }

    public List<StockMinute> getMinutes() {
        return minutes;
    }

    public void setMinutes(List<StockMinute> minutes) {
        this.minutes = minutes;
    }

    @Override
    public String toString() {
        return "StockDaily [dailyId=" + dailyId + ", stockCode=" + stockCode + ", stockName=" + stockName + ", date=" + date + ", lastClose=" + lastClose + ", open=" + open + ", close=" + close + ", closeGap=" + closeGap + ", closeGapRate="
               + closeGapRate + ", high=" + high + ", low=" + low + ", highGap=" + highGap + ", highGapRate=" + highGapRate + ", lowGap=" + lowGap + ", lowGapRate=" + lowGapRate + ", exrights=" + exrights + ", thisExrights=" + thisExrights
               + ", amount=" + amount + ", volume=" + volume + ", asset=" + asset + ", volumeRatio=" + volumeRatio + ", ma5=" + ma5 + ", ma10=" + ma10 + ", ma20=" + ma20 + ", ma60=" + ma60 + ", created=" + created + ", updated=" + updated
               + ", minutes=" + minutes + "]";
    }

    @Override
    public int compareTo(StockDaily stockDaily) {
        return this.getDate().compareTo(stockDaily.getDate());
    }

}
