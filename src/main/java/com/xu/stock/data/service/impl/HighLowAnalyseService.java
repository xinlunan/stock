package com.xu.stock.data.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.xu.stock.data.dao.IStockDao;
import com.xu.stock.data.dao.IStockIndexDao;
import com.xu.stock.data.model.Stock;
import com.xu.stock.data.model.StockIndex;
import com.xu.stock.data.service.IStockAnalyseService;

/**
 * 最高最低价差价分析
 * 
 * @version Revision History
 * 
 *          <pre>
 * Author     Version       Date        Changes
 * lunan.xu    1.0           2015-5-31     Created
 * 
 * </pre>
 * @since 1.
 */
@Service("highLowAnalyseService")
public class HighLowAnalyseService implements IStockAnalyseService {
	static Logger log = LoggerFactory.getLogger(HighLowAnalyseService.class);

	@Resource
	private IStockDao stockDao;
	@Resource
	private IStockIndexDao stockIndexDao;

	public int analyse() {
		List<Stock> stocks = stockDao.getAllStocks();

		List<StockIndex> stockIndexs = stockIndexDao.getStockIndexs(stocks.get(0).getStockCode());

		Integer revenue = 0;
		Integer lastClose = 0;
		Integer buy = 0;
		Boolean isBuy = false;
		Boolean isSell = false;

		for (StockIndex stockIndex : stockIndexs) {
			lastClose = stockIndex.getClose();

			if (!isBuy && buy != 0 && buy > stockIndex.getLow()) {
				isBuy = true;
			} else if (!isSell) {
				buy = lastClose - Float.valueOf("" + (lastClose.floatValue() * 0.01)).intValue();
				revenue = revenue + stockIndex.getClose() - buy;

			}
		}

		return 0;
	}
}
