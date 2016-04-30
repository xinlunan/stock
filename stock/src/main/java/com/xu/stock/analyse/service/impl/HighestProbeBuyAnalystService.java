package com.xu.stock.analyse.service.impl;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xu.stock.analyse.model.StockAnalyseStrategy;
import com.xu.stock.analyse.model.StockHighest;
import com.xu.stock.analyse.model.StockTrade;
import com.xu.stock.analyse.model.StockWatchBegin;
import com.xu.stock.analyse.service.IStockTradeBuyService;
import com.xu.stock.analyse.service.IStockHighestService;
import com.xu.stock.analyse.service.IStockWatchBeginService;
import com.xu.stock.analyse.service.StockAnalyseConstants.HighestProbeBuyArgs;
import com.xu.stock.analyse.service.StockAnalyseConstants.StrategyType;
import com.xu.stock.data.model.StockDaily;
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
public class HighestProbeBuyAnalystService extends BaseStockAnalyseService {

    private Integer                 lastWaveCycle;
    private Integer                 thisWaveCycle;
    private BigDecimal              thisFallRate;
    private BigDecimal              buyRateHigh;
    private BigDecimal              buyRateLow;
    private BigDecimal              warnRateLow;
    private String                  parameters;

    @Resource
    private IStockMinuteService     stockMinuteService;
    @Resource
    private IStockHighestService    stockHighestService;
    @Resource
    private IStockWatchBeginService stockWatchBeginService;
    @Resource
    private IStockTradeBuyService        stockTradeBuyService;

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
        this.parameters = this.lastWaveCycle + "," + this.thisWaveCycle + "," + this.thisFallRate + "," + this.warnRateLow + "," + this.buyRateLow + "," + this.buyRateHigh;
    }

    @Override
    public List<StockTrade> doAnalyse(List<StockDaily> dailys) {
        log.info("analyse stock code:" + dailys.get(0).getStockCode());

        // 找出当前股票的历史最高点的日期
        List<StockHighest> highestPoints = stockHighestService.analyseHighestPoints(dailys, parameters, lastWaveCycle, thisWaveCycle, thisFallRate);

        // 根据最高点找出可能试探突破的观察点
        List<StockWatchBegin> watchBegins = stockWatchBeginService.analyseBatchBeginByHighest(dailys, highestPoints, parameters, thisFallRate, warnRateLow, buyRateLow, buyRateHigh);

        // 根据最高点找出试探突破点的分钟
        stockTradeBuyService.analyseStockTradeBuy(dailys, watchBegins);

        // 得到购入时间点1
        return null;
    }

}
