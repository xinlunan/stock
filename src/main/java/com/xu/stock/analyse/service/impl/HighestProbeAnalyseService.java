package com.xu.stock.analyse.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.xu.stock.analyse.dao.IStockAnalyseStrategyDao;
import com.xu.stock.analyse.dao.IStockTradeDao;
import com.xu.stock.analyse.model.StockAnalyseStrategy;
import com.xu.stock.analyse.model.StockTrade;
import com.xu.stock.analyse.service.IStockAnalyseService;
import com.xu.stock.analyse.service.StockAnalyseConstants.StrategyType;
import com.xu.stock.analyse.service.StockAnalyseConstants.TradeNature;
import com.xu.stock.analyse.service.StockAnalyseConstants.TradeType;
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
	@Resource
	private IStockTradeDao stockTradeDao;

	public List<StockDaily> analyse(Stock stock) {
		log.info("analyse stock code:" + stock.getStockCode());

		// 所有交易记录
		List<StockDaily> dailys = stockDailyDao.getStockDailys(stock.getStockCode());

		// 获取分析策略
		StockAnalyseStrategy strategy = stockAnalyseStrategyDao.getAnalyseStrategy(StrategyType.HIGHEST_PROBE);

		// 构建分析器
		HighestProbeAnalyst probe = HighestProbeAnalyst.newInstance(strategy);

		// 分析可购买点
		List<StockDaily> buyPoints = probe.putStockDailys(dailys);

		// 保存购买点
		saveBuyPonits(buyPoints, strategy);

		return buyPoints;

	}

	private void saveBuyPonits(List<StockDaily> buyPoints, StockAnalyseStrategy strategy) {
		List<StockTrade> trades = new ArrayList<StockTrade>();
		for (StockDaily stockDaily : buyPoints) {
			StockTrade trade = new StockTrade();
			trade.setDate(stockDaily.getDate());
			trade.setHour(15);
			trade.setMinute(00);
			trade.setParameters(strategy.getParameters());
			trade.setVersion(strategy.getVersion());
			trade.setPrice(stockDaily.getClose());
			trade.setStockCode(stockDaily.getStockCode());
			trade.setStockId(stockDaily.getStockId());
			trade.setStockName(stockDaily.getStockName());
			trade.setStrategy(strategy.getStrategyType());
			trade.setTradeNature(TradeNature.VIRTUAL);
			trade.setTradeType(TradeType.BUY);
			log.info(trade.toString());

			trades.add(trade);
		}
		stockTradeDao.saveStockTrades(trades);
	}

}
