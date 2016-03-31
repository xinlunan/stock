package com.xu.stock.analyse.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.xu.stock.analyse.dao.IStockAnalyseStrategyDao;
import com.xu.stock.analyse.model.StockAnalyseStrategy;
import com.xu.stock.analyse.service.IStockAnalyseService;
import com.xu.stock.analyse.service.StockAnalyseConstants.StrategyType;
import com.xu.stock.data.dao.IStockDailyDao;
import com.xu.stock.data.dao.IStockDao;
import com.xu.stock.data.model.Stock;
import com.xu.stock.data.model.StockDaily;

@SuppressWarnings("restriction")
@Service("highestProbeAnalyseService")
public class HighestProbeAnalyseService implements IStockAnalyseService {
	protected static Logger log = LoggerFactory.getLogger(HighestProbeAnalyseService.class);

	@Resource
	private IStockDao stockDao;
	@Resource
	private IStockDailyDao stockDailyDao;
	@Resource
	private IStockAnalyseStrategyDao stockAnalyseStrategyDao;

	public List<StockDaily> analyse(Stock stock) {
		log.info("analyse stock code:" + stock.getStockCode());

		List<StockDaily> dailys = stockDailyDao.getStockDailys(stock.getStockCode());

		StockAnalyseStrategy strategy = stockAnalyseStrategyDao.getAnalyseStrategy(StrategyType.HIGHEST_PROBE);

		HighestProbeAnalyst probe = HighestProbeAnalyst.newInstance(strategy);
		return probe.putStockDailys(dailys);

	}

}
