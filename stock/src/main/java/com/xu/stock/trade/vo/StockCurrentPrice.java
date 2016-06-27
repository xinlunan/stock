package com.xu.stock.trade.vo;

import java.io.Serializable;
import java.math.BigDecimal;

public class StockCurrentPrice implements Serializable {

    private static final long serialVersionUID = 8784373552456719916L;
    private String            stockCode;
    private String            stockName;
    private BigDecimal        currentPrice;
    private BigDecimal        riseStopPrice;
    private BigDecimal        lossStopPrice;
    private BigDecimal        lastClose;
    private BigDecimal        sell1Price;
    private BigDecimal        sell2Price;
    private BigDecimal        sell3Price;
    private BigDecimal        sell4Price;
    private BigDecimal        sell5Price;
    private BigDecimal        buy1Price;
    private BigDecimal        buy2Price;
    private BigDecimal        buy3Price;
    private BigDecimal        buy4Price;
    private BigDecimal        buy5Price;

    public static StockCurrentPrice parseCurrentPrice(String html) {
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

    public BigDecimal getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(BigDecimal currentPrice) {
        this.currentPrice = currentPrice;
    }

    public BigDecimal getRiseStopPrice() {
        return riseStopPrice;
    }

    public void setRiseStopPrice(BigDecimal riseStopPrice) {
        this.riseStopPrice = riseStopPrice;
    }

    public BigDecimal getLossStopPrice() {
        return lossStopPrice;
    }

    public void setLossStopPrice(BigDecimal lossStopPrice) {
        this.lossStopPrice = lossStopPrice;
    }

    public BigDecimal getLastClose() {
        return lastClose;
    }

    public void setLastClose(BigDecimal lastClose) {
        this.lastClose = lastClose;
    }

    public BigDecimal getSell1Price() {
        return sell1Price;
    }

    public void setSell1Price(BigDecimal sell1Price) {
        this.sell1Price = sell1Price;
    }

    public BigDecimal getSell2Price() {
        return sell2Price;
    }

    public void setSell2Price(BigDecimal sell2Price) {
        this.sell2Price = sell2Price;
    }

    public BigDecimal getSell3Price() {
        return sell3Price;
    }

    public void setSell3Price(BigDecimal sell3Price) {
        this.sell3Price = sell3Price;
    }

    public BigDecimal getSell4Price() {
        return sell4Price;
    }

    public void setSell4Price(BigDecimal sell4Price) {
        this.sell4Price = sell4Price;
    }

    public BigDecimal getSell5Price() {
        return sell5Price;
    }

    public void setSell5Price(BigDecimal sell5Price) {
        this.sell5Price = sell5Price;
    }

    public BigDecimal getBuy1Price() {
        return buy1Price;
    }

    public void setBuy1Price(BigDecimal buy1Price) {
        this.buy1Price = buy1Price;
    }

    public BigDecimal getBuy2Price() {
        return buy2Price;
    }

    public void setBuy2Price(BigDecimal buy2Price) {
        this.buy2Price = buy2Price;
    }

    public BigDecimal getBuy3Price() {
        return buy3Price;
    }

    public void setBuy3Price(BigDecimal buy3Price) {
        this.buy3Price = buy3Price;
    }

    public BigDecimal getBuy4Price() {
        return buy4Price;
    }

    public void setBuy4Price(BigDecimal buy4Price) {
        this.buy4Price = buy4Price;
    }

    public BigDecimal getBuy5Price() {
        return buy5Price;
    }

    public void setBuy5Price(BigDecimal buy5Price) {
        this.buy5Price = buy5Price;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    @Override
    public String toString() {
        return "StockRealtimeIndex [stockCode=" + stockCode + ", stockName=" + stockName + ", currentPrice=" + currentPrice + ", riseStopPrice=" + riseStopPrice + ", lossStopPrice=" + lossStopPrice + ", lastClose=" + lastClose
               + ", sell1Price=" + sell1Price + ", sell2Price=" + sell2Price + ", sell3Price=" + sell3Price + ", sell4Price=" + sell4Price + ", sell5Price=" + sell5Price + ", buy1Price=" + buy1Price + ", buy2Price=" + buy2Price
               + ", buy3Price=" + buy3Price + ", buy4Price=" + buy4Price + ", buy5Price=" + buy5Price + ", getStockCode()=" + getStockCode() + ", getStockName()=" + getStockName() + ", getCurrentPrice()=" + getCurrentPrice()
               + ", getRiseStopPrice()=" + getRiseStopPrice() + ", getLossStopPrice()=" + getLossStopPrice() + ", getLastClose()=" + getLastClose() + ", getSell1Price()=" + getSell1Price() + ", getSell2Price()=" + getSell2Price()
               + ", getSell3Price()=" + getSell3Price() + ", getSell4Price()=" + getSell4Price() + ", getSell5Price()=" + getSell5Price() + ", getBuy1Price()=" + getBuy1Price() + ", getBuy2Price()=" + getBuy2Price() + ", getBuy3Price()="
               + getBuy3Price() + ", getBuy4Price()=" + getBuy4Price() + ", getBuy5Price()=" + getBuy5Price() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
    }

}
