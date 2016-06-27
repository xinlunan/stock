package com.xu.stock.trade.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class HoldStock implements Serializable {

    private static final long serialVersionUID = 614981233122060832L;
    private String     stockCode;
    private String     stockName;
    private BigDecimal stockCount;
    private BigDecimal sellableCount;
    private String     currency;
    private BigDecimal costPrice;
    private BigDecimal costTotal;
    private BigDecimal currentTotal;
    private BigDecimal currentPrice;
    private BigDecimal profit;
    private BigDecimal profitRate;
    private BigDecimal exchangeRate; // 汇率
    private BigDecimal buyCount;
    private BigDecimal sellCount;
    private String     ownerNo;
    private String     remark;

    public static List<HoldStock> parse(String html) {
        // TODO Auto-generated method stub
        return null;
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

    public BigDecimal getStockCount() {
        return stockCount;
    }

    public void setStockCount(BigDecimal stockCount) {
        this.stockCount = stockCount;
    }

    public BigDecimal getSellableCount() {
        return sellableCount;
    }

    public void setSellableCount(BigDecimal sellableCount) {
        this.sellableCount = sellableCount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public BigDecimal getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(BigDecimal costPrice) {
        this.costPrice = costPrice;
    }

    public BigDecimal getCostTotal() {
        return costTotal;
    }

    public void setCostTotal(BigDecimal costTotal) {
        this.costTotal = costTotal;
    }

    public BigDecimal getCurrentTotal() {
        return currentTotal;
    }

    public void setCurrentTotal(BigDecimal currentTotal) {
        this.currentTotal = currentTotal;
    }

    public BigDecimal getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(BigDecimal currentPrice) {
        this.currentPrice = currentPrice;
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

    public BigDecimal getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(BigDecimal exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public BigDecimal getBuyCount() {
        return buyCount;
    }

    public void setBuyCount(BigDecimal buyCount) {
        this.buyCount = buyCount;
    }

    public BigDecimal getSellCount() {
        return sellCount;
    }

    public void setSellCount(BigDecimal sellCount) {
        this.sellCount = sellCount;
    }

    public String getOwnerNo() {
        return ownerNo;
    }

    public void setOwnerNo(String ownerNo) {
        this.ownerNo = ownerNo;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "HoldStock [stockCode=" + stockCode + ", stockName=" + stockName + ", stockCount=" + stockCount + ", sellableCount=" + sellableCount + ", currency=" + currency + ", costPrice=" + costPrice + ", costTotal=" + costTotal
               + ", currentTotal=" + currentTotal + ", currentPrice=" + currentPrice + ", profit=" + profit + ", profitRate=" + profitRate + ", exchangeRate=" + exchangeRate + ", buyCount=" + buyCount + ", sellCount=" + sellCount
               + ", ownerNo=" + ownerNo + ", remark=" + remark + "]";
    }

}
