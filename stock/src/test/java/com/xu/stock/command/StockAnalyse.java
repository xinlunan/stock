package com.xu.stock.command;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;

import com.xu.stock.analyse.controller.GeneralRiseBuyAnalyseController;
import com.xu.stock.analyse.controller.HighestProbeBuyAnalyseController;
import com.xu.stock.analyse.controller.HighestProbeSellAnalyseController;
import com.xu.stock.analyse.controller.SerialRiseBuyAnalyseController;
import com.xu.stock.analyse.controller.SerialRiseSellAnalyseController;
import com.xu.stock.data.controller.StockDailyController;
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

    public static final int                   NUM_THREADS = 10;

    @Resource
    private StockDailyController              stockDailyController;
    @Resource
    private SerialRiseBuyAnalyseController    serialRiseBuyAnalyseController;
    @Resource
    private SerialRiseSellAnalyseController   serialRiseSellAnalyseController;
    @Resource
    private GeneralRiseBuyAnalyseController   generalRiseBuyAnalyseController;
    @Resource
    private HighestProbeBuyAnalyseController  highestProbeBuyAnalyseController;
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
	public void serialRiseBuyAnalyse() {
		log.info("连续上涨分析购买开始");
        serialRiseBuyAnalyseController.analyse(NUM_THREADS);
		log.info("连续上涨分析购买完成.");
	}

	@Test
	public void generalRiseBuyAnalyse() {
		log.info("一般上涨分析购买开始");
		generalRiseBuyAnalyseController.analyse(NUM_THREADS);
		log.info("一般上涨分析购买完成.");
	}

	@Test
	public void serialRiseSellAnalyse() {
		log.info("连续上涨分析卖出开始");
		serialRiseSellAnalyseController.analyse(NUM_THREADS);
		log.info("连续上涨分析卖出完成.");
	}

    @Before
    public void download() {
        // stockDailyController.downloadStockDaily(100);
    }

}
