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
public class StockTrade implements Serializable {

    private static final long serialVersionUID = -352228891672124416L;

    // 股票每分钟指数Id
    private Long              tradeId;
    // 股票代码
    private String            stockCode;
    // 股票名称
    private String            stockName;
    // 日期
    private Date              buyDate;
    // 小时
    private Integer           buyHour;
    // 分钟
    private Integer           buyMinute;
    // 购买价
    private BigDecimal        buyTradePrice;
    // 购买日最高价
    private BigDecimal        buyHighPrice;
    // 购买日收盘价
    private BigDecimal        buyClosePrice;
    // 日期
    private Date              sellDate;
    // 小时
    private Integer           sellHour;
    // 分钟
    private Integer           sellMinute;
    // 购买价
    private BigDecimal        sellTradePrice;
    // 购买日最高价
    private BigDecimal        sellHighPrice;
    // 购买日收盘价
    private BigDecimal        sellClosePrice;
    // 利润
    private BigDecimal        profit;
    // 利润率
    private BigDecimal        profitRate;
    // 最高价收益比例
    private BigDecimal        highProfitRate;
    // 收盘价收益比例
    private BigDecimal        closeProfitRate;
    // 交易类型
    private String            tradeType;
    // 交易性质
    private String            tradeNature;
    // 策略
    private String            strategy;
    // 策略版本
    private Integer           version;
    // 参数
    private String            parameters;
    // 创建日期
    private Date              created;
    // 更新日期
    private Date              updated;

    private BigDecimal        exrights;

    public Long getTradeId() {
        return tradeId;
    }

    public void setTradeId(Long tradeId) {
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

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public Date getBuyDate() {
        return buyDate;
    }

    public void setBuyDate(Date buyDate) {
        this.buyDate = buyDate;
    }

    public Integer getBuyHour() {
        return buyHour;
    }

    public void setBuyHour(Integer buyHour) {
        this.buyHour = buyHour;
    }

    public Integer getBuyMinute() {
        return buyMinute;
    }

    public void setBuyMinute(Integer buyMinute) {
        this.buyMinute = buyMinute;
    }

    public BigDecimal getBuyTradePrice() {
        return buyTradePrice;
    }

    public void setBuyTradePrice(BigDecimal buyTradePrice) {
        this.buyTradePrice = buyTradePrice;
    }

    public BigDecimal getBuyHighPrice() {
        return buyHighPrice;
    }

    public void setBuyHighPrice(BigDecimal buyHighPrice) {
        this.buyHighPrice = buyHighPrice;
    }

    public BigDecimal getBuyClosePrice() {
        return buyClosePrice;
    }

    public void setBuyClosePrice(BigDecimal buyClosePrice) {
        this.buyClosePrice = buyClosePrice;
    }

    public Date getSellDate() {
        return sellDate;
    }

    public void setSellDate(Date sellDate) {
        this.sellDate = sellDate;
    }

    public Integer getSellHour() {
        return sellHour;
    }

    public void setSellHour(Integer sellHour) {
        this.sellHour = sellHour;
    }

    public Integer getSellMinute() {
        return sellMinute;
    }

    public void setSellMinute(Integer sellMinute) {
        this.sellMinute = sellMinute;
    }

    public BigDecimal getSellTradePrice() {
        return sellTradePrice;
    }

    public void setSellTradePrice(BigDecimal sellTradePrice) {
        this.sellTradePrice = sellTradePrice;
    }

    public BigDecimal getSellHighPrice() {
        return sellHighPrice;
    }

    public void setSellHighPrice(BigDecimal sellHighPrice) {
        this.sellHighPrice = sellHighPrice;
    }

    public BigDecimal getSellClosePrice() {
        return sellClosePrice;
    }

    public void setSellClosePrice(BigDecimal sellClosePrice) {
        this.sellClosePrice = sellClosePrice;
    }

    public BigDecimal getProfit() {
        return profit;
    }

    public void setProfit(BigDecimal profit) {
        this.profit = profit;
    }

    public BigDecimal getProfitRate() {
        return profitRate;
    }

    public void setProfitRate(BigDecimal profitRate) {
        this.profitRate = profitRate;
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public String getTradeNature() {
        return tradeNature;
    }

    public void setTradeNature(String tradeNature) {
        this.tradeNature = tradeNature;
    }

    public String getStrategy() {
        return strategy;
    }

    public void setStrategy(String strategy) {
        this.strategy = strategy;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getParameters() {
        return parameters;
    }

    public void setParameters(String parameters) {
        this.parameters = parameters;
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

    public BigDecimal getHighProfitRate() {
        return highProfitRate;
    }

    public void setHighProfitRate(BigDecimal highProfitRate) {
        this.highProfitRate = highProfitRate;
    }

    public BigDecimal getCloseProfitRate() {
        return closeProfitRate;
    }

    public void setCloseProfitRate(BigDecimal closeProfitRate) {
        this.closeProfitRate = closeProfitRate;
    }

    @Override
    public String toString() {
        return "StockTrade [tradeId=" + tradeId + ", stockCode=" + stockCode + ", stockName=" + stockName + ", buyDate=" + buyDate + ", buyHour=" + buyHour + ", buyMinute=" + buyMinute + ", buyTradePrice=" + buyTradePrice
               + ", buyHighPrice=" + buyHighPrice + ", buyClosePrice=" + buyClosePrice + ", sellDate=" + sellDate + ", sellHour=" + sellHour + ", sellMinute=" + sellMinute + ", sellTradePrice=" + sellTradePrice + ", sellHighPrice="
               + sellHighPrice + ", sellClosePrice=" + sellClosePrice + ", profit=" + profit + ", profitRate=" + profitRate + ", highProfitRate=" + highProfitRate + ", closeProfitRate=" + closeProfitRate + ", tradeType=" + tradeType
               + ", tradeNature=" + tradeNature + ", strategy=" + strategy + ", version=" + version + ", parameters=" + parameters + ", created=" + created + ", updated=" + updated + ", exrights=" + exrights + "]";
    }

    public BigDecimal getExrights() {
        return exrights;
    }

    public void setExrights(BigDecimal exrights) {
        this.exrights = exrights;
    }

}
