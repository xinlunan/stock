package com.xu.stock.trade.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class AuthorizeStock implements Serializable {

    private static final long serialVersionUID = 8784373552456719916L;
    private String     stockCode;
    private String     stockName;
    private String     tradeType;
    private BigDecimal authorizePrice;
    private BigDecimal authorizeCount;
    private BigDecimal tradePrice;
    private BigDecimal tradeCount;
    private String     authorizeStatus;
    private String     authorizeDate;
    private String     authorizeNo;
    private String     authorizeType;
    private String     ownerNo;

    public static List<AuthorizeStock> parseAuthorizeStocks(String html) {
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

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public BigDecimal getAuthorizePrice() {
        return authorizePrice;
    }

    public void setAuthorizePrice(BigDecimal authorizePrice) {
        this.authorizePrice = authorizePrice;
    }

    public BigDecimal getAuthorizeCount() {
        return authorizeCount;
    }

    public void setAuthorizeCount(BigDecimal authorizeCount) {
        this.authorizeCount = authorizeCount;
    }

    public BigDecimal getTradePrice() {
        return tradePrice;
    }

    public void setTradePrice(BigDecimal tradePrice) {
        this.tradePrice = tradePrice;
    }

    public BigDecimal getTradeCount() {
        return tradeCount;
    }

    public void setTradeCount(BigDecimal tradeCount) {
        this.tradeCount = tradeCount;
    }

    public String getAuthorizeStatus() {
        return authorizeStatus;
    }

    public void setAuthorizeStatus(String authorizeStatus) {
        this.authorizeStatus = authorizeStatus;
    }

    public String getAuthorizeDate() {
        return authorizeDate;
    }

    public void setAuthorizeDate(String authorizeDate) {
        this.authorizeDate = authorizeDate;
    }

    public String getAuthorizeNo() {
        return authorizeNo;
    }

    public void setAuthorizeNo(String authorizeNo) {
        this.authorizeNo = authorizeNo;
    }

    public String getAuthorizeType() {
        return authorizeType;
    }

    public void setAuthorizeType(String authorizeType) {
        this.authorizeType = authorizeType;
    }

    public String getOwnerNo() {
        return ownerNo;
    }

    public void setOwnerNo(String ownerNo) {
        this.ownerNo = ownerNo;
    }

    @Override
    public String toString() {
        return "AuthorizeStock [stockCode=" + stockCode + ", stockName=" + stockName + ", tradeType=" + tradeType + ", authorizePrice=" + authorizePrice + ", authorizeCount=" + authorizeCount + ", tradePrice=" + tradePrice + ", tradeCount="
               + tradeCount + ", authorizeStatus=" + authorizeStatus + ", authorizeDate=" + authorizeDate + ", authorizeNo=" + authorizeNo + ", authorizeType=" + authorizeType + ", ownerNo=" + ownerNo + "]";
    }

}
