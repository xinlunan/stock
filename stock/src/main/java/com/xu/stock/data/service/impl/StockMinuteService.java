package com.xu.stock.data.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.xu.stock.data.dao.IStockMinuteDao;
import com.xu.stock.data.download.SinaStockMinuteDownloador;
import com.xu.stock.data.model.StockDaily;
import com.xu.stock.data.model.StockMinute;
import com.xu.stock.data.service.IStockMinuteService;

/**
 * 股票指数service实现
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
@SuppressWarnings("restriction")
@Service("stockMinuteService")
public class StockMinuteService implements IStockMinuteService {

    protected Logger        log = LoggerFactory.getLogger(this.getClass());

    @Resource
    private IStockMinuteDao stockMinuteDao;

    public List<StockMinute> getStockMinutes(StockDaily stockDaily, Date date) {

        List<StockMinute> stockMinutes = stockMinuteDao.getStockMinutes(stockDaily.getStockCode(), date);
        if (stockMinutes == null || stockMinutes.isEmpty()) {
            stockMinutes = downloadStockMinutes(stockDaily, date);
        }
        return stockMinutes;
    }

    public List<StockMinute> downloadStockMinutes(StockDaily stockDaily, Date date) {
        log.info("download begin stock minutes stock_code:" + stockDaily.getStockCode());
        List<StockMinute> stockMinutes = SinaStockMinuteDownloador.download(stockDaily, date);
        log.info("download end stock minutes stock_code:" + stockDaily.getStockCode() + "  size:" + stockMinutes.size());
        saveStockMinutes(stockMinutes);
        log.info("save end stock miutes");
        return stockMinutes;
    }

    public void saveStockMinutes(List<StockMinute> stockMinutes) {
        stockMinuteDao.saveStockMinutes(stockMinutes);
    }

    public StockMinute getNearCloseBuyMinute(StockDaily stockDaily, Date date) {
        StockMinute stockMinute = stockMinuteDao.getNearCloseBuyMinute(stockDaily.getStockCode(), date);
        if (stockMinute == null) {
            downloadStockMinutes(stockDaily, date);
            stockMinute = stockMinuteDao.getNearCloseBuyMinute(stockDaily.getStockCode(), date);
        }
        return stockMinute;
    }
}
