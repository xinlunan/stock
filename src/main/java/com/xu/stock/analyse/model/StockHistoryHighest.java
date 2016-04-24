package com.xu.stock.analyse.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.xu.stock.data.model.StockDaily;

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
public class StockHistoryHighest implements Serializable {

    private static final long serialVersionUID = -352228891672124416L;

    private Integer           historyId;
    // 股票代码
    private String            stockCode;
    // 股票名称
    private String            stockName;
    // 日期
    private Date              date;
    // 小时
    private Integer           hour;
    // 分钟
    private Integer           minute;
    // 最高价
    private BigDecimal        open;
    // 最高价
    private BigDecimal        high;
    // 最低价
    private BigDecimal        low;
    // 收盘价
    private BigDecimal        close;
    // 最低价
    private BigDecimal        exrights;
    // 创建日期
    private Date              created;
    // 更新日期
    private Date              updated;
    // 可能购买的日期
    private List<StockDaily>  buyableDailys;

    public Integer getHistoryId() {
        return historyId;
    }

    public void setHistoryId(Integer historyId) {
        this.historyId = historyId;
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

    public BigDecimal getOpen() {
        return open;
    }

    public void setOpen(BigDecimal open) {
        this.open = open;
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

    public BigDecimal getClose() {
        return close;
    }

    public void setClose(BigDecimal close) {
        this.close = close;
    }

    public BigDecimal getExrights() {
        return exrights;
    }

    public void setExrights(BigDecimal exrights) {
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

    
    public List<StockDaily> getBuyableDailys() {
        return buyableDailys;
    }

    
    public void setBuyableDailys(List<StockDaily> buyableDailys) {
        this.buyableDailys = buyableDailys;
    }

    @Override
    public String toString() {
        return "StockHistoryHighest [historyId=" + historyId + ", stockCode=" + stockCode + ", stockName=" + stockName + ", date=" + date + ", hour=" + hour + ", minute=" + minute + ", open=" + open + ", high=" + high + ", low=" + low
               + ", close=" + close + ", exrights=" + exrights + ", created=" + created + ", updated=" + updated + "]";
    }

}
