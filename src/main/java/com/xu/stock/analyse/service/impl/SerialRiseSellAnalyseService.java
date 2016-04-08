package com.xu.stock.analyse.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.xu.stock.analyse.model.StockAnalyseStrategy;
import com.xu.stock.analyse.model.StockSimulateTrade;
import com.xu.stock.analyse.service.StockAnalyseConstants.SerialRiseSellArgs;
import com.xu.stock.analyse.service.StockAnalyseConstants.StrategyType;
import com.xu.stock.analyse.service.StockAnalyseConstants.TradeNature;
import com.xu.stock.analyse.service.StockAnalyseConstants.TradeType;
import com.xu.stock.data.model.StockDaily;

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
@Service("serialRiseSellAnalyseService")
public class SerialRiseSellAnalyseService extends BaseStockAnalyseService {

	private Integer holdDay;

	@Override
	public void setAnalyseStrategy(StockAnalyseStrategy strategy) {
		super.setAnalyseStrategy(strategy);
		this.holdDay = strategy.getIntValue(SerialRiseSellArgs.HOLD_DAY);
	}

	@Override
	public List<StockSimulateTrade> doAnalyse(List<StockDaily> dailys) {
		log.info("analyse stock code:" + dailys.get(0).getStockCode());

		// 获取买入点
		List<StockSimulateTrade> buys = stockSimulateTradeDao.getBuyTrades(StrategyType.SERIAL_RISE_BUY, dailys.get(0).getStockCode());

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
		for (StockSimulateTrade stockSimulateTrade : buys) {
            StockDaily nextDaily = StockAnalyseUtil.getSellStockDaily(dailys, stockSimulateTrade.getBuyDate(), holdDay);
			if (nextDaily != null) {
				StockSimulateTrade trade = new StockSimulateTrade();

				trade.setStockCode(nextDaily.getStockCode());
				trade.setStockName(nextDaily.getStockName());

				trade.setBuyDate(stockSimulateTrade.getBuyDate());
				trade.setBuyHour(stockSimulateTrade.getBuyHour());
				trade.setBuyMinute(stockSimulateTrade.getBuyMinute());
				trade.setBuyTradePrice(stockSimulateTrade.getBuyTradePrice());
				trade.setBuyHighPrice(stockSimulateTrade.getBuyHighPrice());
				trade.setBuyClosePrice(stockSimulateTrade.getBuyClosePrice());

				trade.setSellDate(nextDaily.getDate());
				trade.setSellHour(15);
				trade.setSellMinute(0);
				BigDecimal expectRate = BigDecimal.valueOf(strategy.getDoubleValue(SerialRiseSellArgs.EXPECT_RATE)).divide(BD_100,4, BigDecimal.ROUND_HALF_UP);// 期望收益
				BigDecimal expectSellPrice = stockSimulateTrade.getBuyTradePrice().add(stockSimulateTrade.getBuyTradePrice().multiply(expectRate));
				if (expectSellPrice.compareTo(nextDaily.getHigh()) <= 0) {
					trade.setSellTradePrice(expectSellPrice);
				} else {
					trade.setSellTradePrice(nextDaily.getClose());
				}
				trade.setSellHighPrice(nextDaily.getHigh());
				trade.setSellClosePrice(nextDaily.getClose());

				trade.setProfit(trade.getSellTradePrice().subtract(trade.getBuyTradePrice()));
				trade.setProfitRate(trade.getProfit().multiply(BD_100).divide(trade.getBuyTradePrice(),2, BigDecimal.ROUND_HALF_UP));
				trade.setHighProfitRate(trade.getSellHighPrice().subtract(trade.getBuyTradePrice()).multiply(BD_100).divide(trade.getBuyTradePrice(), 4, BigDecimal.ROUND_HALF_UP));
				trade.setCloseProfitRate(trade.getSellClosePrice().subtract(trade.getBuyTradePrice()).multiply(BD_100).divide(trade.getBuyTradePrice(), 4, BigDecimal.ROUND_HALF_UP));
				
				trade.setTradeType(TradeType.SELL);
				trade.setTradeNature(TradeNature.VIRTUAL);
				trade.setStrategy(StrategyType.SERIAL_RISE_SELL.toString());
				trade.setVersion(strategy.getVersion());
				trade.setParameters(strategy.getParameters());
				log.info(trade.toString());

				// if
				// (trade.getProfitRate().compareTo(BigDecimal.valueOf(Double.valueOf(-10.1)))
				// == 1) {
					sells.add(trade);
				// }
			}
		}

		return sells;
	}


	@Override
	public List<StockAnalyseStrategy> getAnalyseStrategys() {
		return stockAnalyseStrategyDao.getAnalyseStrategys(StrategyType.SERIAL_RISE_SELL);
	}
}
