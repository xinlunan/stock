package com.xu.stock.data.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.xu.stock.data.dao.IStockDao;
import com.xu.stock.data.dao.IStockDailyDao;
import com.xu.stock.data.model.Stock;
import com.xu.stock.data.model.StockDaily;
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
 *          </pre>
 * 
 * @since 1.
 */
@SuppressWarnings("restriction")
@Service("stockAnalyseHighLowService")
public class StockAnalyseHighLowService implements IStockAnalyseService {
	protected static Logger log = LoggerFactory.getLogger(StockAnalyseHighLowService.class);

	@Resource
	private IStockDao stockDao;
	@Resource
	private IStockDailyDao stockDailyDao;

	public List<StockDaily> analyse(Stock stock) {
		log.info("analyse stock code:" + stock.getStockCode());

		return null;
	}
}
