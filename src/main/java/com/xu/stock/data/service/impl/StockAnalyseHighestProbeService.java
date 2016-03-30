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
import com.xu.stock.data.service.analyse.HighestProbeAnalyst;

@SuppressWarnings("restriction")
@Service("stockAnalyseHighestProbeService")
public class StockAnalyseHighestProbeService implements IStockAnalyseService {
	protected static Logger log = LoggerFactory.getLogger(StockAnalyseHighestProbeService.class);

	@Resource
	private IStockDao stockDao;
	@Resource
	private IStockIndexDao stockIndexDao;

	public List<StockIndex> analyse(Stock stock) {
		log.info("analyse stock code:" + stock.getStockCode());

		List<StockIndex> indexs = stockIndexDao.getStockIndexs(stock.getStockCode());

		HighestProbeAnalyst probe = HighestProbeAnalyst.newInstance();
		return probe.putStockIndexs(indexs);

	}

}
