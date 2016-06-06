package com.xu.stock.analyse.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

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
public class StockHighest implements Serializable {

    private static final long     serialVersionUID = -352228891672124416L;

    private Integer               highestId;
    // 股票代码
    private String                stockCode;
    // 股票名称
    private String                stockName;
    // 日期
    private Date                  date;
    // 最高价
    private BigDecimal            open;
    // 最高价
    private BigDecimal            high;
    // 最低价
    private BigDecimal            low;
    // 收盘价
    private BigDecimal            close;
    // 最低价
    private BigDecimal            exrights;
    // 参数
    private String                parameters;
    // 最后分析日期
    private Date                  analyseDate;
    // 创建日期
    private Date                  created;
    // 更新日期
    private Date                  updated;
    // 开始观察点
    private List<StockWatchBegin> watchBegins;

    public Integer getHighestId() {
        return highestId;
    }

    public void setHighestId(Integer highestId) {
        this.highestId = highestId;
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

    public String getParameters() {
        return parameters;
    }

    public void setParameters(String parameters) {
        this.parameters = parameters;
    }

    public Date getAnalyseDate() {
        return analyseDate;
    }

    public void setAnalyseDate(Date analyseDate) {
        this.analyseDate = analyseDate;
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

    public List<StockWatchBegin> getWatchBegins() {
        return watchBegins;
    }

    public void setWatchBegins(List<StockWatchBegin> watchBegins) {
        this.watchBegins = watchBegins;
    }

    @Override
    public String toString() {
        return "StockHighest [highestId=" + highestId + ", stockCode=" + stockCode + ", stockName=" + stockName + ", date=" + date + ", open=" + open + ", high=" + high + ", low=" + low + ", close=" + close + ", exrights=" + exrights
               + ", parameters=" + parameters + ", analyseDate=" + analyseDate + ", created=" + created + ", updated=" + updated + ", watchBegins=" + watchBegins + "]";
    }

}
