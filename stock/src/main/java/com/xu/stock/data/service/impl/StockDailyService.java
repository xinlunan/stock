package com.xu.stock.data.service.impl;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.xu.stock.data.dao.IStockDailyDao;
import com.xu.stock.data.model.StockDaily;
import com.xu.stock.data.service.IStockDailyService;

/**
 * 股票指数service实现
 * 
 * @version Revision History
 * 
 *          <pre>
 * Author     Version       Date        Changes
 * lunan.xu    1.0           2015-5-23     Created
 * 
 * </pre>
 * @since 1.
 */
@SuppressWarnings("restriction")
@Service("stockDailyService")
public abstract class StockDailyService implements IStockDailyService {
	static Logger log = LoggerFactory.getLogger(StockDailyService.class);

	@Resource
	private IStockDailyDao stockDailyDao;

	public List<StockDaily> getStockDaily(String stockCode) {
		return stockDailyDao.getRrightStockDailys(stockCode);
	}

	public void repairStockDaily(StockDaily daily, List<StockDaily> repairIndexs) {
		if (repairIndexs.size() > 0) {
			StockDaily lastIdex = stockDailyDao.getLastStockDaily(repairIndexs.get(0).getStockCode(),
					repairIndexs.get(0).getDate());
			for (int i = 0; i < repairIndexs.size(); i++) {
				if (i > 0) {
					lastIdex = repairIndexs.get(i - 1);
				}

				StockDaily stockDaily = repairIndexs.get(i);
				stockDaily.setStockId(lastIdex.getStockId());
				stockDaily.setStockCode(lastIdex.getStockCode());
				stockDaily.setStockName(lastIdex.getStockName());
				stockDaily.setLastClose(lastIdex.getClose());
				stockDaily.setCloseGap(stockDaily.getClose() - stockDaily.getLastClose());
				BigDecimal closeGap = new BigDecimal(stockDaily.getCloseGap());
				BigDecimal lastClose = new BigDecimal(stockDaily.getLastClose());
				Float closeGapRate = closeGap.divide(lastClose, 4, BigDecimal.ROUND_HALF_UP).floatValue() * 100;
				stockDaily.setCloseGapRate(closeGapRate);
				stockDaily.setHighGap(stockDaily.getHigh() - stockDaily.getLastClose());
				BigDecimal highGap = new BigDecimal(stockDaily.getHighGap());
				Float highGapRate = highGap.divide(lastClose, 4, BigDecimal.ROUND_HALF_UP).floatValue() * 100;
				stockDaily.setHighGapRate(highGapRate);
				stockDaily.setLowGap(stockDaily.getLow() - stockDaily.getLastClose());
				BigDecimal lowGap = new BigDecimal(stockDaily.getLowGap());
				Float lowGapRate = lowGap.divide(lastClose, 4, BigDecimal.ROUND_HALF_UP).floatValue() * 100;
				stockDaily.setLowGapRate(lowGapRate);
				Long asset = null;
				stockDaily.setAsset(asset);

				if (i == repairIndexs.size() - 1) {
					daily.setLastClose(stockDaily.getClose());
					daily.setCloseGap(daily.getClose() - daily.getLastClose());
					closeGap = new BigDecimal(daily.getCloseGap());
					lastClose = new BigDecimal(daily.getLastClose());
					closeGapRate = closeGap.divide(lastClose, 4, BigDecimal.ROUND_HALF_UP).floatValue() * 100;
					daily.setCloseGapRate(closeGapRate);
					daily.setHighGap(daily.getHigh() - daily.getLastClose());
					highGap = new BigDecimal(daily.getHighGap());
					highGapRate = highGap.divide(lastClose, 4, BigDecimal.ROUND_HALF_UP).floatValue() * 100;
					daily.setHighGapRate(highGapRate);
					daily.setLowGap(daily.getLow() - daily.getLastClose());
					lowGap = new BigDecimal(daily.getLowGap());
					lowGapRate = lowGap.divide(lastClose, 4, BigDecimal.ROUND_HALF_UP).floatValue() * 100;
					daily.setLowGapRate(lowGapRate);
					daily.setAsset(asset);
				}
			}
		}

		//添加最新的
		stockDailyDao.saveStockDailys(repairIndexs);
		//更新最新的
		stockDailyDao.updateStockDaily(daily);
	}
}