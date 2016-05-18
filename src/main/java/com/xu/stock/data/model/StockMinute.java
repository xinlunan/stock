package com.xu.stock.data.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 股票分时指数
 * 
 * @version
 * 
 * <pre>
 * Author	Version		Date		Changes
 * lunan.xu 	1.0  		2016年3月30日 	Created
 * </pre>
 * 
 * @since 1.
 */
public class StockMinute implements Serializable {

    private static final long serialVersionUID = -352228891672124416L;

    // 股票每分钟指数Id
    private Long              minuteId;
    // 股票代码
    private String            stockCode;
    // 日期
    private Date              date;
    // 小时
    private Integer           hour;
    // 分钟
    private Integer           minute;
    // 当前价
    private Double            price;
    // 最高价
    private Double            high;
    // 最低价
    private Double            low;
    // 除权系统
    private Double            exrights;
    // 成交量
    private Double            volume;
    // 成交额
    private Double            amount;
    // 创建日期
    private Date              created;
    // 更新日期
    private Date              updated;

    private StockDaily        stockDaily;

    public Long getMinuteId() {
        return minuteId;
    }

    public void setMinuteId(Long minuteId) {
        this.minuteId = minuteId;
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

    public BigDecimal getPrice() {
        return BigDecimal.valueOf(price);
    }

    public void setPrice(BigDecimal price) {
        this.price = price.doubleValue();
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

    public BigDecimal getExrights() {
        return BigDecimal.valueOf(exrights);
    }

    public void setExrights(BigDecimal exrights) {
        this.exrights = exrights.doubleValue();
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

    public void setStockDaily(StockDaily stockDaily) {
        this.stockDaily = stockDaily;
    }

    public StockDaily getStockDaily() {
        return this.stockDaily;
    }

    public Double getVolume() {
        return volume;
    }

    public void setVolume(Double volume) {
        this.volume = volume;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "StockMinute [minuteId=" + minuteId + ", stockCode=" + stockCode + ", date=" + date + ", hour=" + hour + ", minute=" + minute + ", price=" + price + ", high=" + high + ", exrights=" + exrights + ", volume=" + volume
               + ", amount=" + amount + ", created=" + created + ", updated=" + updated + ", stockDaily=" + stockDaily + "]";
    }

}
