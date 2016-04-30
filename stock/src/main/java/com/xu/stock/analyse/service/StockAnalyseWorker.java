package com.xu.stock.analyse.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xu.stock.data.dao.IStockDailyDao;
import com.xu.stock.data.model.Stock;
import com.xu.stock.data.model.StockDaily;

public class StockAnalyseWorker extends Thread {

    protected Logger             log = LoggerFactory.getLogger(this.getClass());

    private List<Stock>          stocks;
    private IStockAnalyseService stockAnalyseService;
    private IStockDailyDao       stockDailyDao;

    @Override
    public void run() {
        log.info("StockDailyWorker run size" + stocks.size());

        for (Stock stock : stocks) {
            int retry = 3;
            for (int i = 0; i == 0 || i < retry; i++) {
                try {
                    // 所有交易记录
                    List<StockDaily> dailys = stockDailyDao.getRrightStockDailys(stock.getStockCode());
                    if (!dailys.isEmpty()) {
                        stockAnalyseService.analyse(dailys);
                    }

                } catch (Exception e) {
                    if (i >= retry - 1) {
                        log.error("stock fetch daily error stock :" + stock.getStockCode(), e);
                    }
                }
            }
        }
    }

    public void setStocks(List<Stock> stocks) {
        this.stocks = stocks;
    }

    public void setStockDailyDao(IStockDailyDao stockDailyDao) {
        this.stockDailyDao = stockDailyDao;
    }

    public void setStockAnalyseService(IStockAnalyseService stockAnalyseService) {
        this.stockAnalyseService = stockAnalyseService;
    }

}
