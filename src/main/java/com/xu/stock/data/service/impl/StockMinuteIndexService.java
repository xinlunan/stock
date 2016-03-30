package com.xu.stock.data.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.xu.stock.data.dao.IStockMinuteIndexDao;
import com.xu.stock.data.model.StockMinuteIndex;
import com.xu.stock.data.service.IStockMinuteIndexService;

/**
 * 股票指数service实现
 * 
 * 
 * @version
 * 
 * 			<pre>
 * Author	Version		Date		Changes
 * lunan.xu 	1.0  		2016年3月30日 	Created
 *
 *          </pre>
 * 
 * @since 1.
 */
@SuppressWarnings("restriction")
@Service("stockMinuteIndexService")
public class StockMinuteIndexService implements IStockMinuteIndexService {
	static Logger log = LoggerFactory.getLogger(StockMinuteIndexService.class);

	@Resource
	private IStockMinuteIndexDao stockMinuteIndexDao;

	public List<StockMinuteIndex> getStockMinuteIndexs(String stockCode, Date date) {
		return stockMinuteIndexDao.getStockMinuteIndexs(stockCode, date);
	}

	public void saveStockMinuteIndexs(List<StockMinuteIndex> stockMinuteIndexs) {
		stockMinuteIndexDao.saveStockMinuteIndexs(stockMinuteIndexs);
	}
}