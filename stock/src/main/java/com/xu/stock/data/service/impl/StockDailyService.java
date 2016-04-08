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
	protected Logger log = LoggerFactory.getLogger(this.getClass());
	public static final BigDecimal BD_100 = BigDecimal.valueOf(100);
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
				stockDaily.setStockCode(lastIdex.getStockCode());
				stockDaily.setStockName(lastIdex.getStockName());
				stockDaily.setLastClose(lastIdex.getClose());
				stockDaily.setCloseGap(stockDaily.getClose().subtract(stockDaily.getLastClose()));
				BigDecimal closeGapRate = stockDaily.getCloseGap().multiply(BD_100).divide(stockDaily.getLastClose(), 2, BigDecimal.ROUND_HALF_UP);
				stockDaily.setCloseGapRate(closeGapRate);
				stockDaily.setHighGap(stockDaily.getHigh().subtract(stockDaily.getLastClose()));
				BigDecimal highGapRate = stockDaily.getHighGap().multiply(BD_100).divide(stockDaily.getLastClose(), 2, BigDecimal.ROUND_HALF_UP);
				stockDaily.setHighGapRate(highGapRate);
				stockDaily.setLowGap(stockDaily.getLow().subtract(stockDaily.getLastClose()));
				BigDecimal lowGapRate = stockDaily.getLowGap().multiply(BD_100).divide(stockDaily.getLastClose(), 2, BigDecimal.ROUND_HALF_UP);
				stockDaily.setLowGapRate(lowGapRate);
				Long asset = null;
				stockDaily.setAsset(asset);

				if (i == repairIndexs.size() - 1) {
					daily.setLastClose(stockDaily.getClose());
					daily.setCloseGap(daily.getClose().subtract(daily.getLastClose()));
					closeGapRate = daily.getCloseGap().multiply(BD_100).divide(stockDaily.getLastClose(), 2, BigDecimal.ROUND_HALF_UP);
					daily.setCloseGapRate(closeGapRate);
					daily.setHighGap(daily.getHigh().subtract(daily.getLastClose()));
					highGapRate = daily.getHighGap().multiply(BD_100).divide(stockDaily.getLastClose(), 2, BigDecimal.ROUND_HALF_UP);
					daily.setHighGapRate(highGapRate);
					daily.setLowGap(daily.getLow().subtract(daily.getLastClose()));
					lowGapRate = daily.getLowGap().multiply(BD_100).divide(stockDaily.getLastClose(), 2, BigDecimal.ROUND_HALF_UP);
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