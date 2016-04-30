package com.xu.stock.analyse.model;

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
public class StockWatchBegin implements Serializable {

    private static final long serialVersionUID = -352228891672124416L;

    private Integer           watchBeginId;
    // 股票代码
    private String            stockCode;
    // 股票名称
    private String            stockName;
    // 策略
    private String            strategy;
    // 参数
    private String            parameters;
    // 参考日期
    private Date              refDate;
    // 参考收盘价
    private BigDecimal        refClose;
    // 参考除权系数
    private BigDecimal        refExrights;
    // 上一日期
    private Date              date;
    // 上一收盘价
    private BigDecimal        close;
    // 上一除权系数
    private BigDecimal        exrights;
    // 购买区间最低值
    private BigDecimal        buyRefLowExr;
    // 购买区间最高值
    private BigDecimal        buyRefHighExr;
    // 分析状态：TRADED,UNTRADED,ANALYZING
    private String            analyseStatus;
    // 创建日期
    private Date              created;
    // 更新日期
    private Date              updated;

    public Integer getWatchBeginId() {
        return watchBeginId;
    }

    public void setWatchBeginId(Integer watchBeginId) {
        this.watchBeginId = watchBeginId;
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

    public Date getRefDate() {
        return refDate;
    }

    public void setRefDate(Date refDate) {
        this.refDate = refDate;
    }

    public BigDecimal getRefClose() {
        return refClose;
    }

    public void setRefClose(BigDecimal refClose) {
        this.refClose = refClose;
    }

    public BigDecimal getRefExrights() {
        return refExrights;
    }

    public void setRefExrights(BigDecimal refExrights) {
        this.refExrights = refExrights;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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

    public BigDecimal getBuyRefLowExr() {
        return buyRefLowExr;
    }

    public void setBuyRefLowExr(BigDecimal buyRefLowExr) {
        this.buyRefLowExr = buyRefLowExr;
    }

    public BigDecimal getBuyRefHighExr() {
        return buyRefHighExr;
    }

    public void setBuyRefHighExr(BigDecimal buyRefHighExr) {
        this.buyRefHighExr = buyRefHighExr;
    }

    public String getAnalyseStatus() {
        return analyseStatus;
    }

    public void setAnalyseStatus(String analyseStatus) {
        this.analyseStatus = analyseStatus;
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
        return "StockWatchBegin [watchBeginId=" + watchBeginId + ", stockCode=" + stockCode + ", stockName=" + stockName + ", strategy=" + strategy + ", parameters=" + parameters + ", refDate=" + refDate + ", refClose=" + refClose
               + ", refExrights=" + refExrights + ", lastDate=" + date + ", lastClose=" + close + ", lastExrights=" + exrights + ", buyRefLowExr=" + buyRefLowExr + ", buyRefHighExr=" + buyRefHighExr + ", analyseStatus="
               + analyseStatus + ", created=" + created + ", updated=" + updated + "]";
    }

}
