package com.xu.stock.analyse.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.xu.stock.analyse.model.StockAnalyseStrategy;
import com.xu.stock.analyse.service.StockAnalyseConstants.GeneralRiseBuyArgs;
import com.xu.stock.analyse.service.StockAnalyseConstants.StrategyType;
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
@Service("generalRiseBuyAnalyseService")
public class GeneralRiseBuyAnalyseService extends SerialRiseBuyAnalyseService {
	private Integer serialDays;

	@Override
	public void setAnalyseStrategy(StockAnalyseStrategy strategy) {
		super.setAnalyseStrategy(strategy);
		this.serialDays = strategy.getIntValue(GeneralRiseBuyArgs.SERIAL_DAY);
	}
	/**
	 * 是否是购买点
	 * 
	 * @param dailys
	 * @param index
	 * @param riseDay2
	 * @return
	 */
	@Override
	protected boolean isBuyDaily(List<StockDaily> dailys, int index) {
		// 是否连接上涨
		if (!isGeneralRise(dailys, index)) {
			return false;
		}

		// 增长是否达到预期
		if (!isRistExpected(dailys, index)) {
			return false;
		}

		// 是否连接上涨
		if (isRistLimit(dailys, index)) {
			return false;
		}

		return true;
	}

	/**
	 * 是否整体上涨
	 * 
	 * @param dailys
	 * @param index
	 * @return
	 */
	protected boolean isGeneralRise(List<StockDaily> dailys, int index) {
		for (int i = index + riseDay - serialDays; i < index + riseDay; i++) {
			if (dailys.get(i).getClose().compareTo(dailys.get(i + 1).getClose()) == 1) {
				return false;
			}
		}
		return true;
	}

	@Override
	public List<StockAnalyseStrategy> getAnalyseStrategys() {
		return stockAnalyseStrategyDao.getAnalyseStrategys(StrategyType.GENERAL_RISE_BUY);
	}
}
