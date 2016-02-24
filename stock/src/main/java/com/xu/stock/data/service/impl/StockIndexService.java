package com.xu.stock.data.service.impl;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.xu.stock.data.dao.IStockIndexDao;
import com.xu.stock.data.model.StockIndex;
import com.xu.stock.data.service.IStockIndexService;

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
@Service("stockIndexService")
public class StockIndexService implements IStockIndexService {
	static Logger log = Logger.getLogger(StockIndexService.class);

	@Resource
	private IStockIndexDao stockIndexDao;

	@Override
	public List<StockIndex> getStockIndex(String stockCode) {
		return stockIndexDao.getStockIndexs(stockCode);
	}

	@Override
	public void repairStockIndex(StockIndex index, List<StockIndex> repairIndexs) {
		if (repairIndexs.size() > 0) {
			StockIndex lastIdex = stockIndexDao.getLastStockIndex(repairIndexs.get(0).getStockCode(),
					repairIndexs.get(0).getDate());
			for (int i = 0; i < repairIndexs.size(); i++) {
				if (i > 0) {
					lastIdex = repairIndexs.get(i - 1);
				}

				StockIndex stockIndex = repairIndexs.get(i);
				stockIndex.setStockId(lastIdex.getStockId());
				stockIndex.setStockCode(lastIdex.getStockCode());
				stockIndex.setStockName(lastIdex.getStockName());
				stockIndex.setLastClose(lastIdex.getClose());
				stockIndex.setCloseGap(stockIndex.getClose() - stockIndex.getLastClose());
				BigDecimal closeGap = new BigDecimal(stockIndex.getCloseGap());
				BigDecimal lastClose = new BigDecimal(stockIndex.getLastClose());
				Float closeGapRate = closeGap.divide(lastClose, 4, BigDecimal.ROUND_HALF_UP).floatValue() * 100;
				stockIndex.setCloseGapRate(closeGapRate);
				stockIndex.setHighGap(stockIndex.getHigh() - stockIndex.getLastClose());
				BigDecimal highGap = new BigDecimal(stockIndex.getHighGap());
				Float highGapRate = highGap.divide(lastClose, 4, BigDecimal.ROUND_HALF_UP).floatValue() * 100;
				stockIndex.setHighGapRate(highGapRate);
				stockIndex.setLowGap(stockIndex.getLow() - stockIndex.getLastClose());
				BigDecimal lowGap = new BigDecimal(stockIndex.getLowGap());
				Float lowGapRate = lowGap.divide(lastClose, 4, BigDecimal.ROUND_HALF_UP).floatValue() * 100;
				stockIndex.setLowGapRate(lowGapRate);
				Long asset = null;
				stockIndex.setAsset(asset);

				if (i == repairIndexs.size() - 1) {
					index.setLastClose(stockIndex.getClose());
					index.setCloseGap(index.getClose() - index.getLastClose());
					closeGap = new BigDecimal(index.getCloseGap());
					lastClose = new BigDecimal(index.getLastClose());
					closeGapRate = closeGap.divide(lastClose, 4, BigDecimal.ROUND_HALF_UP).floatValue() * 100;
					index.setCloseGapRate(closeGapRate);
					index.setHighGap(index.getHigh() - index.getLastClose());
					highGap = new BigDecimal(index.getHighGap());
					highGapRate = highGap.divide(lastClose, 4, BigDecimal.ROUND_HALF_UP).floatValue() * 100;
					index.setHighGapRate(highGapRate);
					index.setLowGap(index.getLow() - index.getLastClose());
					lowGap = new BigDecimal(index.getLowGap());
					lowGapRate = lowGap.divide(lastClose, 4, BigDecimal.ROUND_HALF_UP).floatValue() * 100;
					index.setLowGapRate(lowGapRate);
					index.setAsset(asset);
				}
			}
		}

		//添加最新的
		stockIndexDao.saveStockIndexs(repairIndexs);
		//更新最新的
		stockIndexDao.updateStockIndex(index);
	}
}