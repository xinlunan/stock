package com.xu.stock.analyse.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.xu.stock.analyse.model.StockAnalyseStrategy;
import com.xu.stock.analyse.model.StockTrade;
import com.xu.stock.analyse.service.StockAnalyseConstants.SerialRiseSellArgs;
import com.xu.stock.analyse.service.StockAnalyseConstants.StrategyType;
import com.xu.stock.analyse.service.StockAnalyseConstants.TradeNature;
import com.xu.stock.analyse.service.StockAnalyseConstants.TradeType;
import com.xu.stock.analyse.service.uitl.StockAnalyseUtil;
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
    private BigDecimal stopLoss;

	@Override
	public void setAnalyseStrategy(StockAnalyseStrategy strategy) {
		super.setAnalyseStrategy(strategy);
		this.holdDay = strategy.getIntValue(SerialRiseSellArgs.HOLD_DAY);
        this.holdDay = strategy.getIntValue(SerialRiseSellArgs.STOP_LOSS);
	}

	@Override
    public void doAnalyse(List<StockDaily> dailys) {
		log.info("analyse stock code:" + dailys.get(0).getStockCode());

		// 获取买入点
        List<StockTrade> buys = stockTradeDao.getBuyTrades(StrategyType.SERIAL_RISE_BUY, dailys.get(0).getStockCode());

		// 分析卖出点
        analyseSellPoints(dailys, buys);
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
        for (StockTrade buy : buys) {
            StockDaily sellDaily = StockAnalyseUtil.getSellDaily(dailys, buy.getBuyDate(), holdDay);
            if (sellDaily != null) {
                StockTrade sell = new StockTrade();

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
				BigDecimal expectRate = BigDecimal.valueOf(strategy.getDoubleValue(SerialRiseSellArgs.EXPECT_RATE)).divide(BD_100,4, BigDecimal.ROUND_HALF_UP);// 期望收益
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
                sell.setProfitRate(sell.getProfit().multiply(BD_100).divide(sell.getBuyTradePrice(), 2, BigDecimal.ROUND_HALF_UP));
                sell.setHighProfitRate(sell.getSellHighPrice().subtract(sell.getBuyTradePrice()).multiply(BD_100).divide(sell.getBuyTradePrice(), 4, BigDecimal.ROUND_HALF_UP));
                sell.setCloseProfitRate(sell.getSellClosePrice().subtract(sell.getBuyTradePrice()).multiply(BD_100).divide(sell.getBuyTradePrice(), 4, BigDecimal.ROUND_HALF_UP));
				
                sell.setTradeType(TradeType.SELL);
                sell.setTradeNature(TradeNature.VIRTUAL);
                sell.setStrategy(StrategyType.SERIAL_RISE_SELL.toString());
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
		return stockAnalyseStrategyDao.getAnalyseStrategys(StrategyType.SERIAL_RISE_SELL);
	}
}
