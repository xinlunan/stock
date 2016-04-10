package com.xu.stock.analyse.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xu.stock.analyse.model.StockAnalyseStrategy;
import com.xu.stock.analyse.model.StockBuyTrade;
import com.xu.stock.analyse.service.StockAnalyseConstants.HighestProbeBuyArgs;
import com.xu.stock.analyse.service.StockAnalyseConstants.StrategyType;
import com.xu.stock.analyse.service.StockAnalyseConstants.TradeNature;
import com.xu.stock.analyse.service.StockAnalyseConstants.TradeType;
import com.xu.stock.data.model.StockDaily;
import com.xu.stock.data.model.StockMinute;
import com.xu.stock.data.service.IStockMinuteService;

/**
 * 最高点探测分析
 * 
 * @version
 * 
 * <pre>
 * Author	Version		Date		Changes
 * lunan.xu 	1.0  		2016年3月19日 	Created
 * </pre>
 * 
 * @since 1.
 */
@SuppressWarnings("restriction")
@Service("highestProbeBuyAnalyseService")
public class HighestProbeBuyAnalyst extends BaseStockAnalyseService {

    @Resource
    private IStockMinuteService stockMinuteService;

    private Integer             lastWaveCycle;
    private Integer             thisWaveCycle;
    private BigDecimal          thisFallRate;
    private BigDecimal          buyRateHigh;
    private BigDecimal          buyRateLow;
    private BigDecimal          warnRateLow;

    @Override
    public List<StockAnalyseStrategy> getAnalyseStrategys() {
        return stockAnalyseStrategyDao.getAnalyseStrategys(StrategyType.HIGHEST_PROBE_BUY);
    }

    @Override
    public void setAnalyseStrategy(StockAnalyseStrategy strategy) {
        super.setAnalyseStrategy(strategy);
        this.lastWaveCycle = strategy.getIntValue(HighestProbeBuyArgs.D1_LAST_WAVE_CYCLE);
        this.thisWaveCycle = strategy.getIntValue(HighestProbeBuyArgs.D2_THIS_WAVE_CYCLE);
        this.thisFallRate = BigDecimal.valueOf(strategy.getDoubleValue(HighestProbeBuyArgs.F1_THIS_FALL_RATE));
        this.warnRateLow = BigDecimal.valueOf(strategy.getDoubleValue(HighestProbeBuyArgs.F2_WARN_RATE_LOW));
        this.buyRateLow = BigDecimal.valueOf(strategy.getDoubleValue(HighestProbeBuyArgs.F3_BUY_RATE_LOW));
        this.buyRateHigh = BigDecimal.valueOf(strategy.getDoubleValue(HighestProbeBuyArgs.F4_BUY_RATE_HIGH));
    }

    @Override
    public List<StockBuyTrade> doAnalyse(List<StockDaily> dailys) {
        log.info("analyse stock code:" + dailys.get(0).getStockCode());

        // 找出当前股票的历史最高点的日期
        List<Integer> highestPoints = scanHighestPoints(dailys);

        // 根据最高点找出可能试探突破点的日期
        List<Integer> buyDailyPoints = scanBuyableDailyPoints(dailys, highestPoints);

        // 根据最高点找出试探突破点的分钟
        List<StockMinute> buyMinutePoints = scanBuyMinutePoints(dailys, buyDailyPoints);

        // 得到购入时间点
        return buildStockSimulateTrades(buyMinutePoints);
    }

    /**
     * 分析购买的时间点
     * 
     * @param dailys
     * @param buyDailyPoints
     * @return
     */
    private List<StockMinute> scanBuyMinutePoints(List<StockDaily> dailys, List<Integer> buyDailyPoints) {

        List<StockMinute> buyMinutes = new ArrayList<StockMinute>();
        for (Integer dailyPoint : buyDailyPoints) {
            
            StockDaily daily = dailys.get(dailyPoint);
            BigDecimal buyLowExr = daily.getClose().subtract(daily.getClose().multiply(buyRateLow).divide(BD_100)).multiply(daily.getExrights());
            BigDecimal buyHighExr = daily.getClose().subtract(daily.getClose().multiply(buyRateHigh).divide(BD_100)).multiply(daily.getExrights());
            StockMinute buyMinute = scanBuyMinutePoint(dailys, dailyPoint, buyLowExr, buyHighExr);
            if (buyMinute != null) {
                buyMinutes.add(buyMinute);
            }

        }
        return buyMinutes;
    }

    /**
     * 分析购买的时间点
     * 
     * @param dailys
     * @param index
     * @param buyLowRate
     * @param buyHighRate
     * @return
     */
    private StockMinute scanBuyMinutePoint(List<StockDaily> dailys, Integer index, BigDecimal buyLowExr, BigDecimal buyHighExr) {
        for (int i = index; index < dailys.size(); i++) {
            StockDaily buyableDaily = dailys.get(i);
            StockMinute buyMinute = stockMinuteService.getNearCloseBuyMinute(buyableDaily, dailys.get(i + 1).getDate());
            if (buyMinute != null && buyLowExr.compareTo(BigDecimal.valueOf(buyMinute.getPrice() * buyMinute.getExrights())) < 0 && buyHighExr.compareTo(BigDecimal.valueOf(buyMinute.getPrice() * buyMinute.getExrights())) < 0) {
                buyMinute.setStockDaily(buyableDaily);
                return buyMinute;
            }
        }
        return null;
    }

    /**
     * 分析购买的时间点
     * 
     * @param buyPoints
     * @param buyDailyPoints
     * @return
     */
    private List<StockBuyTrade> buildStockSimulateTrades(List<StockMinute> stockMinutes) {
        List<StockBuyTrade> trades = new ArrayList<StockBuyTrade>();
        for (StockMinute stockMinute : stockMinutes) {
            StockDaily stockDaily = stockMinute.getStockDaily();
            StockBuyTrade trade = new StockBuyTrade();

            trade.setStockCode(stockDaily.getStockCode());
            trade.setStockName(stockDaily.getStockName());

            trade.setBuyDate(stockDaily.getDate());
            trade.setBuyHour(15);
            trade.setBuyMinute(0);
            trade.setBuyTradePrice(stockDaily.getClose());
            trade.setBuyHighPrice(stockDaily.getHigh());
            trade.setBuyClosePrice(stockDaily.getClose());

            trade.setSellDate(stockMinute.getDate());
            trade.setSellHour(stockMinute.getHour());
            trade.setSellMinute(stockMinute.getMinute());
            trade.setSellTradePrice(BigDecimal.valueOf(0));
            trade.setSellHighPrice(BigDecimal.valueOf(0));
            trade.setSellClosePrice(BigDecimal.valueOf(0));

            trade.setProfit(BigDecimal.valueOf(0));
            trade.setProfitRate(BigDecimal.valueOf(0));
            trade.setHighProfitRate(BigDecimal.valueOf(0));
            trade.setCloseProfitRate(BigDecimal.valueOf(0));

            trade.setTradeType(TradeType.BUY);
            trade.setTradeNature(TradeNature.VIRTUAL);
            trade.setStrategy(StrategyType.HIGHEST_PROBE_BUY.toString());
            trade.setVersion(strategy.getVersion());
            trade.setParameters(strategy.getParameters());
            log.info(trade.toString());

            trades.add(trade);
        }
        return trades;
    }

    /**
     * 根据最高点找出可能试探突破点的日期
     * 
     * @param stockDailys
     * @param highestPonits
     * @param thisFallRate
     * @param warnRateHigh
     * @param warnRateLow
     * @return
     */
    private List<Integer> scanBuyableDailyPoints(List<StockDaily> stockDailys, List<Integer> highestPonits) {
        List<Integer> buyPoints = new ArrayList<Integer>();

        for (Integer point : highestPonits) {

            StockDaily highestStockDaily = stockDailys.get(point);
            BigDecimal highestCloseExr = highestStockDaily.getClose().multiply(highestStockDaily.getExrights());
            BigDecimal warnLowExr = highestCloseExr.subtract(highestCloseExr.multiply(warnRateLow).divide(BD_100));
            BigDecimal warnHighExr = highestCloseExr.subtract(highestCloseExr.multiply(buyRateHigh).divide(BD_100));
            BigDecimal lowestExr = BigDecimal.valueOf(Integer.MAX_VALUE);
            BigDecimal rate = BigDecimal.valueOf(Integer.MIN_VALUE);
            for (int i = point + thisWaveCycle; i < stockDailys.size(); i++) {// 从指定点开始遍历
                StockDaily thisDaliy = stockDailys.get(i);
                BigDecimal thisCloseExr = thisDaliy.getClose().multiply(thisDaliy.getExrights());
                BigDecimal currentHighestExr = thisDaliy.getHigh().multiply(thisDaliy.getExrights());

                // 如果当前价小于最低价
                if (thisCloseExr.compareTo(lowestExr) == -1) {
                    lowestExr = thisDaliy.getClose().multiply(thisDaliy.getExrights());
                    BigDecimal closeGapExr = highestCloseExr.subtract(lowestExr);
                    rate = closeGapExr.multiply(BD_100).divide(highestCloseExr, 2, BigDecimal.ROUND_HALF_UP);
                }

                // 当前价高于设定范围
                if (thisCloseExr.compareTo(warnHighExr) == 1) {
                    break;
                }

                // 本次跌幅超设定幅度，与最高点相差比例介于设定的报警范围内，当前最高价小于历史最高价
                if (rate.compareTo(thisFallRate) == 1 && thisCloseExr.compareTo(warnLowExr) >= 0 && thisCloseExr.compareTo(warnHighExr) <= 0 && currentHighestExr.compareTo(highestCloseExr) <= 0) {
                    buyPoints.add(i);
                    break;// 跳出循环，不再找第二次接近的点
                }

            }

        }

        return buyPoints;
    }

    /**
     * 统计历史最高点
     * 
     * @param stockDailys
     * @param date1
     * @param date2
     * @param beginIndex
     * @param endIndex
     * @return
     */
    private List<Integer> scanHighestPoints(List<StockDaily> stockDailys) {
        int beginIndex = lastWaveCycle;
        int endIndex = stockDailys.size() - thisWaveCycle;

        List<Integer> highestPoints = new ArrayList<Integer>();
        for (int index = beginIndex; index < endIndex; index++) {
            if (StockAnalyseUtil.isHighest(stockDailys, index, lastWaveCycle, thisWaveCycle)) {
                highestPoints.add(index);
            }
        }
        return highestPoints;
    }

}
