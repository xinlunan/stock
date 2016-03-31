package com.xu.stock.analyse.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.xu.stock.analyse.service.IStockAnalyseService;
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

	public List<StockDaily> analyse(Stock stock) {
		log.info("analyse stock code:" + stock.getStockCode());

		List<StockDaily> dailys = stockDailyDao.getStockDailys(stock.getStockCode());

		HighestProbeAnalyst probe = HighestProbeAnalyst.newInstance();
		return probe.putStockDailys(dailys);

	}

}
