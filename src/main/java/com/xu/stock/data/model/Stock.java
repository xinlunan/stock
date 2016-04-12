package com.xu.stock.data.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 股票
 * 
 * @version Revision History
 * 
 * <pre>
 * Author     Version       Date        Changes
 * lunan.xu    1.0           2015-5-23     Created
 * </pre>
 * 
 * @since 1.
 */
public class Stock implements Serializable, Comparable<Stock> {

    private static final long serialVersionUID = -1928043971469861083L;
    // 股票Id
    private Integer           stockId;
    // 交易所
    private String            exchange;
    // 股票代码
    private String            stockCode;
    // 股票名称
    private String            stockName;
    // 总资产
    private Long              asset;
    // 最后同步日期
    private Date              lastDate;
    // 最后收盘价
    private BigDecimal        lastClose;
    // 创建日期
    private Date              created;
    // 更新日期
    private Date              updated;

    // 除权系数
    private BigDecimal        exrights;
    private List<StockDaily>  stockDailys;
    private Boolean           hasException     = false;
    private Integer           dailySize        = 0;

    public Integer getStockId() {
        return stockId;
    }

    public void setStockId(Integer stockId) {
        this.stockId = stockId;
    }

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
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

    public Long getAsset() {
        return asset;
    }

    public void setAsset(Long asset) {
        this.asset = asset;
    }

    public Date getLastDate() {
        return lastDate;
    }

    public void setLastDate(Date lastDate) {
        this.lastDate = lastDate;
    }

    public BigDecimal getLastClose() {
        return lastClose;
    }

    public void setLastClose(BigDecimal lastClose) {
        this.lastClose = lastClose;
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

    public void setStockDailys(List<StockDaily> stockDailys) {
        this.stockDailys = stockDailys;
    }

    public List<StockDaily> getStockDailys() {
        return stockDailys;
    }

    public int compareTo(Stock stock) {
        return this.getStockCode().compareTo(stock.getStockCode());
    }

    public BigDecimal getExrights() {
        return exrights;
    }

    public void setExrights(BigDecimal exrights) {
        this.exrights = exrights;
    }

    public Boolean getHasException() {
        return hasException;
    }

    public void setHasException(Boolean hasException) {
        this.hasException = hasException;
    }

    public void setHasException(boolean hasException) {
        this.hasException = hasException;
    }

    public Integer getDailySize() {
        return dailySize;
    }

    public void setDailySize(Integer dailySize) {
        this.dailySize = dailySize;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }



}
