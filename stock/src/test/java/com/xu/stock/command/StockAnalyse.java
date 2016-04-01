package com.xu.stock.command;

import javax.annotation.Resource;

import org.junit.Test;

import com.xu.stock.analyse.controller.HighLowBuyAnalyseController;
import com.xu.stock.analyse.controller.HighLowSellAnalyseController;
import com.xu.stock.analyse.controller.HighestProbeBuyAnalyseController;
import com.xu.stock.analyse.controller.HighestProbeSellAnalyseController;
import com.xu.test.BaseTestCase;

/**
 * 股票指数获取
 * 
 * @version Revision History
 * 
 *          <pre>
 * Author     Version       Date        Changes
 * lunan.xu    1.0           2015-5-29     Created
 * 
 *          </pre>
 * 
 * @since 1.
 */
@SuppressWarnings("restriction")
public class StockAnalyse extends BaseTestCase {
	public static final int NUM_THREADS = 10;

	@Resource
	private HighLowBuyAnalyseController highLowBuyAnalyseController;
	@Resource
	private HighLowSellAnalyseController highLowSellAnalyseController;
	@Resource
	private HighestProbeBuyAnalyseController highestProbeBuyAnalyseController;
	@Resource
	private HighestProbeSellAnalyseController highestProbeSellAnalyseController;

	@Test
	public void highestProbeBuyAnalyse() {
		log.info("最高点试探分析购买开始...");
		highestProbeBuyAnalyseController.analyse(NUM_THREADS);
		log.info("最高点试探分析购买完成.");
	}

	@Test
	public void highestProbeSellAnalyse() {
		log.info("最高点试探分析卖出开始...");
		highestProbeSellAnalyseController.analyse(NUM_THREADS);
		log.info("最高点试探分析卖出完成.");
	}

	@Test
	public void highLowBuyAnalyse() {
		log.info("高低分析购买开始");
		highLowBuyAnalyseController.analyse(NUM_THREADS);
		log.info("高低分析购买完成.");
	}

	@Test
	public void highLowSellAnalyse() {
		log.info("高低分析卖出开始");
		highLowSellAnalyseController.analyse(NUM_THREADS);
		log.info("高低分析卖出完成.");
	}

}
