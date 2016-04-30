package com.xu.stock.data.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.xu.stock.analyse.model.StockWatchBegin;
import com.xu.stock.analyse.service.StockAnalyseConstants.StockTradeBuyTime;
import com.xu.stock.data.dao.IStockMinuteDao;
import com.xu.stock.data.download.SinaStockMinuteHistoryDownloador;
import com.xu.stock.data.download.SinaStockMinuteRealtimeDownloador;
import com.xu.stock.data.model.StockDaily;
import com.xu.stock.data.model.StockMinute;
import com.xu.stock.data.service.IStockMinuteService;
import com.xu.util.DateUtil;

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

    public List<StockMinute> getStockMinutes(StockDaily daily) {
        List<StockMinute> stockMinutes = stockMinuteDao.getStockMinutes(daily.getStockCode(), daily.getDate());
        if (stockMinutes == null || stockMinutes.isEmpty() || !hasLastMinutes(stockMinutes)) {
            // 实时下载
            stockMinutes = downloadHistoryStockMinutes(daily);
        }
        return stockMinutes;
    }

    private boolean hasLastMinutes(List<StockMinute> stockMinutes) {
        StockMinute lastMinute = stockMinutes.get(stockMinutes.size()-1);
        if (lastMinute.getHour()==15){
            return true;
        }else if(lastMinute.getHour()<14){
            return false;
        } else if (lastMinute.getMinute() < 55) {
            return false;
        }
        return true;
    }

    public List<StockMinute> downloadHistoryStockMinutes(StockDaily daily) {
        log.info("download begin stock minutes stock_code\t" + daily.getStockCode() + "\t" + DateUtil.date2String(daily.getDate()));
        List<StockMinute> stockMinutes = SinaStockMinuteHistoryDownloador.download(daily);
        log.info("download end stock minutes stock_code:" + daily.getStockCode() + "  size:" + stockMinutes.size());
        saveStockDailyMinutes(daily.getStockCode(), daily.getDate(), stockMinutes);
        log.info("save end stock miutes");
        return stockMinuteDao.getStockMinutes(daily.getStockCode(), daily.getDate());
    }

    public void saveStockDailyMinutes(String stockCode, Date date, List<StockMinute> stockMinutes) {
        if (DateUtil.date2String(date).equals(DateUtil.date2String(new Date())) || (stockMinutes.size() < 220)) {
            List<StockMinute> existMinutes = stockMinuteDao.getStockMinutes(stockCode, date);
            List<StockMinute> removeMiutes = new ArrayList<StockMinute>();
            for (StockMinute existMinute : existMinutes) {
                for (StockMinute removeMiute : stockMinutes) {
                    if (removeMiute.getMinute() == existMinute.getMinute() && removeMiute.getHour() == existMinute.getHour()) {
                        removeMiutes.add(removeMiute);
                    }
                }
            }
            stockMinutes.removeAll(removeMiutes);
        }
        if (stockMinutes.size() > 0) {
            stockMinuteDao.saveStockMinutes(stockMinutes);
        }
    }

    public StockMinute fetchHistoryNearCloseBuyMinute(StockDaily daily) {

        StockMinute stockMinute = stockMinuteDao.getHistoryNearCloseBuyMinute(daily.getStockCode(), daily.getDate(), StockTradeBuyTime.HOUR, StockTradeBuyTime.MINUTE);
        if (stockMinute == null) {
            List<StockMinute> stockMinutes = downloadHistoryStockMinutes(daily);
            for (StockMinute stockMinute2 : stockMinutes) {
                if (stockMinute2.getHour() >= StockTradeBuyTime.HOUR && stockMinute2.getMinute() >= StockTradeBuyTime.MINUTE) {
                    return stockMinute2;
                }
            }
        }
        return stockMinute;
    }

    public StockMinute fetchRealNearCloseBuyMinute(StockWatchBegin watchBegin) {
        StockMinute stockMinute = stockMinuteDao.getNearCloseBuyMinute(watchBegin.getStockCode(), watchBegin.getDate(), StockTradeBuyTime.HOUR, StockTradeBuyTime.MINUTE);

        if (stockMinute == null) {
            // 实时下载
            downloadRealtimeStockMinute(watchBegin);
            stockMinuteDao.getNearCloseBuyMinute(watchBegin.getStockCode(), watchBegin.getDate(), StockTradeBuyTime.HOUR, StockTradeBuyTime.MINUTE);
        }
        return stockMinute;
    }

    private void downloadRealtimeStockMinute(StockWatchBegin watchBegin) {
        StockMinute stockMinute = SinaStockMinuteRealtimeDownloador.download(watchBegin);
        StockMinute existMinute = stockMinuteDao.getStockMinute(stockMinute.getStockCode(), stockMinute.getDate(), stockMinute.getHour(), stockMinute.getMinute());
        if (existMinute == null) {
            List<StockMinute> stockMinutes = new ArrayList<StockMinute>();
            stockMinutes.add(stockMinute);
            stockMinuteDao.saveStockMinutes(stockMinutes);
        }
    }

}
