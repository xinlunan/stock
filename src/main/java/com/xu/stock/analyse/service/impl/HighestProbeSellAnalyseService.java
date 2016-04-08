package com.xu.stock.analyse.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.xu.stock.analyse.model.StockAnalyseStrategy;
import com.xu.stock.analyse.model.StockSimulateTrade;
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
	public List<StockSimulateTrade> doAnalyse(List<StockDaily> dailys) {
		log.info("analyse stock code:" + dailys.get(0).getStockCode());

		// 获取买入点
		List<StockSimulateTrade> buys = stockSimulateTradeDao.getBuyTrades(StrategyType.HIGHEST_PROBE_BUY,
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
	private List<StockSimulateTrade> analyseSellPoints(List<StockDaily> dailys, List<StockSimulateTrade> buys) {
		List<StockSimulateTrade> sells = new ArrayList<StockSimulateTrade>();
		for (StockSimulateTrade buy : buys) {
            StockDaily sellDaily = StockAnalyseUtil.getSellStockDaily(dailys, buy.getBuyDate(), holdDay);
			if (sellDaily != null) {
				StockSimulateTrade sell = new StockSimulateTrade();

				sell.setStockCode(sellDaily.getStockCode());
				sell.setStockName(sellDaily.getStockName());

				sell.setBuyDate(buy.getBuyDate());
				sell.setBuyHour(buy.getBuyHour());
				sell.setBuyMinute(buy.getBuyMinute());
				sell.setBuyTradePrice(buy.getBuyTradePrice());
				sell.setBuyHighPrice(buy.getBuyHighPrice());
				sell.setBuyClosePrice(buy.getBuyClosePrice());
                sell.setExrights(buy.getExrights());

				sell.setSellDate(sellDaily.getDate());
				sell.setSellHour(15);
				sell.setSellMinute(0);
				BigDecimal expectRate = BigDecimal.valueOf(strategy.getDoubleValue(HighestProbeSellArgs.EXPECT_RATE)).divide(BD_100).setScale(4, BigDecimal.ROUND_HALF_UP);// 期望收益
                BigDecimal expectSellPrice = buy.getBuyTradePrice().add(buy.getBuyTradePrice().multiply(expectRate)).multiply(buy.getExrights()).divide(sell.getExrights());
                BigDecimal stopLossPrice = buy.getBuyTradePrice().subtract(buy.getBuyTradePrice().multiply(stopLoss)).multiply(buy.getExrights()).divide(sell.getExrights());
                if (sellDaily.getLow().compareTo(stopLossPrice) == -1) {
                    sell.setSellTradePrice(stopLossPrice);
                } else if (expectSellPrice.compareTo(sellDaily.getHigh()) <= 0) {
                    sell.setSellTradePrice(expectSellPrice);
                } else {
					sell.setSellTradePrice(sellDaily.getClose());
				}
				sell.setSellHighPrice(sellDaily.getHigh());
				sell.setSellClosePrice(sellDaily.getClose());

				sell.setProfit(sell.getSellTradePrice().subtract(sell.getBuyTradePrice()));
				sell.setProfitRate(sell.getProfit().multiply(BD_100).divide(sell.getBuyTradePrice(), 4, BigDecimal.ROUND_HALF_UP));
				sell.setHighProfitRate(sell.getSellHighPrice().subtract(sell.getBuyTradePrice()).multiply(BD_100).divide(sell.getBuyTradePrice(), 4, BigDecimal.ROUND_HALF_UP));
				sell.setCloseProfitRate(sell.getSellClosePrice().subtract(sell.getBuyTradePrice()).multiply(BD_100).divide(sell.getBuyTradePrice(), 4, BigDecimal.ROUND_HALF_UP));

				sell.setTradeType(TradeType.SELL);
				sell.setTradeNature(TradeNature.VIRTUAL);
				sell.setStrategy(StrategyType.HIGHEST_PROBE_SELL.toString());
				sell.setVersion(strategy.getVersion());
				sell.setParameters(strategy.getParameters());
				log.info(sell.toString());

				sells.add(sell);
			}
		}

		return sells;
	}



	@Override
	public List<StockAnalyseStrategy> getAnalyseStrategys() {
		return stockAnalyseStrategyDao.getAnalyseStrategys(StrategyType.HIGHEST_PROBE_SELL);
	}

}
