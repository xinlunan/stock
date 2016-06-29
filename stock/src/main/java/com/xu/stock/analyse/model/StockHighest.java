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
    private Double                open;
    // 最高价
    private Double                high;
    // 最低价
    private Double                low;
    // 收盘价
    private Double                close;
    // 最低价
    private Double                exrights;
    // 参数
    private String                parameters;
    // 分析状态
    private String                analyseStatus;
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
        return BigDecimal.valueOf(open);
    }

    public void setOpen(BigDecimal open) {
        this.open = open.doubleValue();
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

    public BigDecimal getClose() {
        return BigDecimal.valueOf(close);
    }

    public void setClose(BigDecimal close) {
        this.close = close.doubleValue();
    }

    public BigDecimal getExrights() {
        return BigDecimal.valueOf(exrights);
    }

    public void setExrights(BigDecimal exrights) {
        this.exrights = exrights.doubleValue();
    }

    public String getParameters() {
        return parameters;
    }

    public void setParameters(String parameters) {
        this.parameters = parameters;
    }

    public String getAnalyseStatus() {
        return analyseStatus;
    }

    public void setAnalyseStatus(String analyseStatus) {
        this.analyseStatus = analyseStatus;
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
               + ", parameters=" + parameters + ", analyseStatus=" + analyseStatus + ", analyseDate=" + analyseDate + ", created=" + created + ", updated=" + updated + ", watchBegins=" + watchBegins + "]";
    }

}
