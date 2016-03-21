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
import com.xu.util.DateUtil;

@SuppressWarnings("restriction")
@Service("stockAnalyseHighestProbeService")
public class StockAnalyseHighestProbeService implements IStockAnalyseService {
	protected static Logger log = LoggerFactory.getLogger(StockAnalyseHighLowService.class);

	@Resource
	private IStockDao stockDao;
	@Resource
	private IStockIndexDao stockIndexDao;

	public int analyse(Stock stock) {
		log.info("analyse stock code:" + stock.getStockCode());

		HighestProbeAnalyst probe = HighestProbeAnalyst.newInstance();

		List<StockIndex> indexs = stockIndexDao.getStockIndexs(stock.getStockCode());
		for (StockIndex stockIndex : indexs) {
			probe.putStockIndex(stockIndex);
		}

		List<StockIndex> points = probe.getBuyPoints();

		for (StockIndex stockIndex : points) {
			log.info(stock.getStockCode() + "适合购入点：" + DateUtil.date2String(stockIndex.getDate()));
		}

		return points.size();
	}

}
