package com.xu.stock.analyse.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 购买记录
 * 
 * @version
 * 
 * <pre>
 * Author	Version		Date		Changes
 * lunan.xu 	1.0  		2016年4月28日 	Created
 * </pre>
 * 
 * @since 1.
 */
public class StockTradeBuy implements Serializable {

    private static final long serialVersionUID = -352228891672124416L;

    private Long              tradeId;
    // 股票代码
    private String            stockCode;
    // 股票名称
    private String            stockName;
    // 策略
    private String            strategy;
    // 策略参数
    private String            parameters;
    // 交易性质：REAL,VIRTUAL
    private String            nature;
    // 交易状态：BOUGHT,SELLED
    private String            status;
    // 购买日期
    private Date              lastDate;
    // 购买日期
    private Date              date;
    // 购买小时
    private Integer           hour;
    // 购买分钟
    private Integer           minute;
    // 购买价
    private BigDecimal        price;
    // 除权系数
    private BigDecimal        exrights;
    // 分析类型：REALTIME,CLOSE
    private String            analyseType;
    // 创建日期
    private Date              created;
    // 更新日期
    private Date              updated;

    public Long getBuyId() {
        return tradeId;
    }

    public void setBuyId(Long tradeId) {
        this.tradeId = tradeId;
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

    public void setStockName(String stockNname) {
        this.stockName = stockNname;
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

    public String getNature() {
        return nature;
    }

    public void setNature(String nature) {
        this.nature = nature;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getLastDate() {
        return lastDate;
    }

    public void setLastDate(Date refDate) {
        this.lastDate = refDate;
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
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getExrights() {
        return exrights;
    }

    public void setExrights(BigDecimal exrights) {
        this.exrights = exrights;
    }

    public String getAnalyseType() {
        return analyseType;
    }

    public void setAnalyseType(String analyseType) {
        this.analyseType = analyseType;
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
        return "StockTradeBuy [tradeId=" + tradeId + ", stockCode=" + stockCode + ", stockName=" + stockName + ", strategy=" + strategy + ", parameters=" + parameters + ", nature=" + nature + ", status=" + status + ", refDate=" + lastDate
               + ", date=" + date + ", hour=" + hour + ", minute=" + minute + ", price=" + price + ", exrights=" + exrights + ", analyseType=" + analyseType + ", created=" + created + ", updated=" + updated + "]";
    }

}
