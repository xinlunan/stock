package com.xu.stock.analyse.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.xu.stock.analyse.model.StockAnalyseStrategy;
import com.xu.stock.analyse.model.StockTrade;
import com.xu.stock.analyse.service.StockAnalyseConstants.SerialRiseSellArgs;
import com.xu.stock.analyse.service.StockAnalyseConstants.StrategyType;
import com.xu.stock.analyse.service.StockAnalyseConstants.TradeNature;
import com.xu.stock.analyse.service.StockAnalyseConstants.TradeType;
import com.xu.stock.data.model.StockDaily;
import com.xu.util.DateUtil;

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
	public List<StockTrade> doAnalyse(List<StockDaily> dailys) {
		log.info("analyse stock code:" + dailys.get(0).getStockCode());

		// 获取买入点
		List<StockTrade> buys = stockTradeDao.getBuyTrades(StrategyType.SERIAL_RISE_BUY, dailys.get(0).getStockCode());

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
			StockDaily nextDaily = getSellStockDaily(dailys, stockTrade.getBuyDate());
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
				BigDecimal expectRate = BigDecimal.valueOf(strategy.getDoubleValue(SerialRiseSellArgs.EXPECT_RATE)).divide(BD_100,4, BigDecimal.ROUND_HALF_UP);// 期望收益
				BigDecimal expectSellPrice = stockTrade.getBuyTradePrice().add(stockTrade.getBuyTradePrice().multiply(expectRate));
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

	private StockDaily getSellStockDaily(List<StockDaily> dailys, Date date) {
		for (int i = 0; i < dailys.size() - 1; i++) {
			StockDaily daily = dailys.get(i);
			if (DateUtil.dateToString(daily.getDate()).equals(DateUtil.dateToString(date))) {
				return dailys.get(i + holdDay);
			}
		}
		return null;
	}

	@Override
	public List<StockAnalyseStrategy> getAnalyseStrategys() {
		return stockAnalyseStrategyDao.getAnalyseStrategys(StrategyType.SERIAL_RISE_SELL);
	}
}
