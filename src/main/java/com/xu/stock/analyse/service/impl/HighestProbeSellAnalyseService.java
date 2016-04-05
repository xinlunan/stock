package com.xu.stock.analyse.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.xu.stock.analyse.model.StockAnalyseStrategy;
import com.xu.stock.analyse.model.StockTrade;
import com.xu.stock.analyse.service.StockAnalyseConstants.HighestProbeSellArgs;
import com.xu.stock.analyse.service.StockAnalyseConstants.StrategyType;
import com.xu.stock.analyse.service.StockAnalyseConstants.TradeNature;
import com.xu.stock.analyse.service.StockAnalyseConstants.TradeType;
import com.xu.stock.data.model.StockDaily;

@Service("highestProbeSellAnalyseService")
public class HighestProbeSellAnalyseService extends BaseStockAnalyseService {
	private Integer holdDay;
	private BigDecimal stopLoss;

	@Override
	public void setAnalyseStrategy(StockAnalyseStrategy strategy) {
		super.setAnalyseStrategy(strategy);
		this.holdDay = strategy.getIntValue(HighestProbeSellArgs.HOLD_DAY);
		this.stopLoss = BigDecimal.valueOf(strategy.getDoubleValue(HighestProbeSellArgs.STOP_LOSS));
	}

	@Override
	public List<StockTrade> doAnalyse(List<StockDaily> dailys) {
		log.info("analyse stock code:" + dailys.get(0).getStockCode());

		// 获取买入点
		List<StockTrade> buys = stockTradeDao.getBuyTrades(StrategyType.HIGHEST_PROBE_BUY,
				dailys.get(0).getStockCode());

		// 分析卖出点
		return analyseSellPoints(dailys, buys);
	}

	/**
	 * 分析卖出点
	 * 
	 * @param dailys
	 * @param buys
	 * @return
	 */
	private List<StockTrade> analyseSellPoints(List<StockDaily> dailys, List<StockTrade> buys) {
		List<StockTrade> sells = new ArrayList<StockTrade>();
		for (StockTrade stockTrade : buys) {
            StockDaily nextDaily = StockAnalyseUtil.getSellStockDaily(dailys, stockTrade.getBuyDate(), holdDay);
			if (nextDaily != null) {
				StockTrade trade = new StockTrade();

				trade.setStockId(nextDaily.getStockId());
				trade.setStockCode(nextDaily.getStockCode());
				trade.setStockName(nextDaily.getStockName());

				trade.setBuyDate(stockTrade.getBuyDate());
				trade.setBuyHour(stockTrade.getBuyHour());
				trade.setBuyMinute(stockTrade.getBuyMinute());
				trade.setBuyTradePrice(stockTrade.getBuyTradePrice());
				trade.setBuyHighPrice(stockTrade.getBuyHighPrice());
				trade.setBuyClosePrice(stockTrade.getBuyClosePrice());

				trade.setSellDate(nextDaily.getDate());
				trade.setSellHour(15);
				trade.setSellMinute(0);
				BigDecimal expectRate = BigDecimal.valueOf(strategy.getDoubleValue(HighestProbeSellArgs.EXPECT_RATE)).divide(BD_100).setScale(4, BigDecimal.ROUND_HALF_UP);// 期望收益
				BigDecimal expectSellPrice = stockTrade.getBuyTradePrice().add(stockTrade.getBuyTradePrice().multiply(expectRate));
				BigDecimal stopLossPrice = trade.getBuyTradePrice().subtract(trade.getBuyTradePrice().multiply(stopLoss).divide(BD_100));
                if (nextDaily.getLow().compareTo(stopLossPrice) == -1) {
					trade.setSellTradePrice(stopLossPrice);
                } else if (expectSellPrice.compareTo(nextDaily.getHigh()) <= 0) {
                    trade.setSellTradePrice(expectSellPrice);
                } else {
					trade.setSellTradePrice(nextDaily.getClose());
				}
				trade.setSellHighPrice(nextDaily.getHigh());
				trade.setSellClosePrice(nextDaily.getClose());

				trade.setProfit(trade.getSellTradePrice().subtract(trade.getBuyTradePrice()));
				trade.setProfitRate(trade.getProfit().multiply(BD_100).divide(trade.getBuyTradePrice(), 4, BigDecimal.ROUND_HALF_UP));
				trade.setHighProfitRate(trade.getSellHighPrice().subtract(trade.getBuyTradePrice()).multiply(BD_100).divide(trade.getBuyTradePrice(), 4, BigDecimal.ROUND_HALF_UP));
				trade.setCloseProfitRate(trade.getSellClosePrice().subtract(trade.getBuyTradePrice()).multiply(BD_100).divide(trade.getBuyTradePrice(), 4, BigDecimal.ROUND_HALF_UP));

				trade.setTradeType(TradeType.SELL);
				trade.setTradeNature(TradeNature.VIRTUAL);
				trade.setStrategy(StrategyType.HIGHEST_PROBE_SELL.toString());
				trade.setVersion(strategy.getVersion());
				trade.setParameters(strategy.getParameters());
				log.info(trade.toString());

				sells.add(trade);
			}
		}

		return sells;
	}



	@Override
	public List<StockAnalyseStrategy> getAnalyseStrategys() {
		return stockAnalyseStrategyDao.getAnalyseStrategys(StrategyType.HIGHEST_PROBE_SELL);
	}

}
