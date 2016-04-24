package com.xu.stock.data.model;

import java.io.Serializable;
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
    // 当前价
    private Double            high;
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getHigh() {
        return high;
    }

    public void setHigh(Double high) {
        this.high = high;
    }

    public Double getExrights() {
        return exrights;
    }

    public void setExrights(Double exrights) {
        this.exrights = exrights;
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
        return "StockMinute [minuteId=" + minuteId + ", stockCode=" + stockCode + ", date=" + date + ", hour=" + hour + ", minute=" + minute + ", price=" + price + ", exrights=" + exrights + ", volume=" + volume + ", amount=" + amount
               + ", created=" + created + ", updated=" + updated + ", stockDaily=" + stockDaily + "]";
    }

}
