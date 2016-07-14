package com.xu.stock.trade.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

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

    public static StockCurrentPrice parse(String html) {
        try {
            Map<String, String> priceInfos = parseRuselt(html);
            StockCurrentPrice price = new StockCurrentPrice();
            price.setStockCode(priceInfos.get("ZQDM"));
            price.setStockName(priceInfos.get("ZQJC"));
            price.setCurrentPrice(BigDecimal.valueOf(Double.valueOf(priceInfos.get("XJ"))));
            price.setRiseStopPrice(BigDecimal.valueOf(Double.valueOf(priceInfos.get("ZT"))));
            price.setLossStopPrice(BigDecimal.valueOf(Double.valueOf(priceInfos.get("DT"))));
            price.setLastClose(BigDecimal.valueOf(Double.valueOf(priceInfos.get("ZRSP"))));
            price.setSell1Price(BigDecimal.valueOf(Double.valueOf(priceInfos.get("SJW1"))));
            price.setSell2Price(BigDecimal.valueOf(Double.valueOf(priceInfos.get("SJW2"))));
            price.setSell3Price(BigDecimal.valueOf(Double.valueOf(priceInfos.get("SJW3"))));
            price.setSell4Price(BigDecimal.valueOf(Double.valueOf(priceInfos.get("SJW4"))));
            price.setSell5Price(BigDecimal.valueOf(Double.valueOf(priceInfos.get("SJW5"))));
            price.setBuy1Price(BigDecimal.valueOf(Double.valueOf(priceInfos.get("BJW1"))));
            price.setBuy2Price(BigDecimal.valueOf(Double.valueOf(priceInfos.get("BJW2"))));
            price.setBuy3Price(BigDecimal.valueOf(Double.valueOf(priceInfos.get("BJW3"))));
            price.setBuy4Price(BigDecimal.valueOf(Double.valueOf(priceInfos.get("BJW4"))));
            price.setBuy5Price(BigDecimal.valueOf(Double.valueOf(priceInfos.get("BJW5"))));
            return price;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Map<String, String> parseRuselt(String html) {
        String tempString = html.replaceAll("&nbsp;", "").replaceAll(" ", "").replaceAll("\\{", "").replaceAll("\\}", "").replaceAll("'", "");
        Map<String, String> result = new HashMap<String, String>();
        String[] infos = tempString.split(",");
        for (String info : infos) {
            String[] infoMap = info.split(":");
            result.put(infoMap[0], infoMap[1]);
        }
        return result;
    }

    public static void main(String[] args) {
        String html = "{'SCDM':'2','ZQJC':'平安银行','ZQDM':'000001','XJ':'8.70','ZT':'9.56','DT':'7.82','ZRSP':'8.69','BJW1':'8.70','BJW2':'8.69','BJW3':'8.68','BJW4':'8.67','BJW5':'8.66','BSL1':'1341','BSL2':'4365','BSL3':'5120','BSL4':'2544','BSL5':'2545','SJW1':'8.71','SJW2':'8.72','SJW3':'8.73','SJW4':'8.74','SJW5':'8.75','SSL1':'2610','SSL2':'3612','SSL3':'10186','SSL4':'10360','SSL5':'13774'}";
        StockCurrentPrice price = parse(html);
        System.out.println(price);
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

    @Override
    public String toString() {
        return "StockCurrentPrice [stockCode=" + stockCode + ", stockName=" + stockName + ", currentPrice=" + currentPrice + ", riseStopPrice=" + riseStopPrice + ", lossStopPrice=" + lossStopPrice + ", lastClose=" + lastClose
               + ", sell1Price=" + sell1Price + ", sell2Price=" + sell2Price + ", sell3Price=" + sell3Price + ", sell4Price=" + sell4Price + ", sell5Price=" + sell5Price + ", buy1Price=" + buy1Price + ", buy2Price=" + buy2Price
               + ", buy3Price=" + buy3Price + ", buy4Price=" + buy4Price + ", buy5Price=" + buy5Price + "]";
    }

}
