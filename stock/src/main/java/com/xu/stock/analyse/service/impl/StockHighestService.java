package com.xu.stock.analyse.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.xu.stock.analyse.dao.IStockHighestDao;
import com.xu.stock.analyse.model.StockHighest;
import com.xu.stock.analyse.service.IStockHighestService;
import com.xu.stock.analyse.service.uitl.StockAnalyseUtil;
import com.xu.stock.data.model.StockDaily;
import com.xu.util.DateUtil;

/**
 * 历史最高点
 * 
 * @version
 * 
 * <pre>
 * Author	Version		Date		Changes
 * lunan.xu 	1.0  		2016年4月26日 	Created
 * </pre>
 * 
 * @since 1.
 */
@SuppressWarnings("restriction")
@Service("stockHighestService")
public class StockHighestService implements IStockHighestService {

    protected Logger         log = LoggerFactory.getLogger(this.getClass());

    @Resource
    private IStockHighestDao stockHighestDao;

    public void analyseHighestPoints(List<StockDaily> stockDailys, Integer lastWaveCycle, Integer thisWaveCycle, BigDecimal thisFallRate) {
        String parameters = StockAnalyseUtil.buildParameter(lastWaveCycle, thisWaveCycle, thisFallRate);
        List<StockHighest> highests = stockHighestDao.getHighests(stockDailys.get(0).getStockCode(), parameters);

        List<StockHighest> newHighests = new ArrayList<StockHighest>();
        int beginIndex = highestPointBeginIndex(stockDailys, highests, thisWaveCycle);
        int endIndex = stockDailys.size() - thisWaveCycle;
        for (int index = beginIndex; index < endIndex; index++) {
            // 指定是期是否最高点
            if (StockAnalyseUtil.isHighest(stockDailys, index, lastWaveCycle, thisWaveCycle)) {
                // 本次是否达到跌幅
                Date lowDate = StockAnalyseUtil.getFirstLowDate(stockDailys, index, thisFallRate);
                if (lowDate!=null) {
                    StockDaily daily = stockDailys.get(index);
                    StockHighest history = new StockHighest();

                    history.setStockCode(daily.getStockCode());
                    history.setStockName(daily.getStockName());
                    history.setDate(daily.getDate());
                    history.setOpen(daily.getOpen());
                    history.setHigh(daily.getHigh());
                    history.setLow(daily.getLow());
                    history.setClose(daily.getClose());
                    history.setExrights(daily.getExrights());
                    history.setParameters(parameters);
                    history.setAnalyseDate(lowDate);

                    newHighests.add(history);
                }

            }
        }
        stockHighestDao.saveHighest(newHighests);
    }

    /**
     * 需要设计最高点的起始点
     * 
     * @param stockDailys
     * @param highests
     * @param thisWaveCycle
     * @return
     */
    private Integer highestPointBeginIndex(List<StockDaily> stockDailys, List<StockHighest> highests, Integer thisWaveCycle) {
        if (highests.isEmpty()) {
            return thisWaveCycle;
        }
        Date highestDate = highests.get(highests.size() - 1).getDate();
        int thisIndex = stockDailys.size() - 1;
        for (int i = thisIndex; i >= 0; i--) {
            if (DateUtil.dateToString(stockDailys.get(i).getDate()).equals(DateUtil.dateToString(highestDate))) {
                return i + thisWaveCycle;
            }
        }
        return thisWaveCycle;
    }

}
