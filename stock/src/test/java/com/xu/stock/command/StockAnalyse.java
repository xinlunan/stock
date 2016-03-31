package com.xu.stock.command;

import javax.annotation.Resource;

import org.junit.Test;

import com.xu.stock.analyse.controller.HighLowAnalyseController;
import com.xu.stock.analyse.controller.HighestProbeAnalyseController;
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
	public static final int NUM_THREADS = 1;

	@Resource
	private HighLowAnalyseController highLowAnalyseController;
	@Resource
	private HighestProbeAnalyseController highestProbeAnalyseController;

	@Test
	public void highestProbeAnalyse() {
		log.info("最高点试探分析开始...");
		highestProbeAnalyseController.analyse(NUM_THREADS);
		log.info("最高点试探分析完成.");
	}

	@Test
	public void highLowAnalyse() {
		log.info("高低分析开始");
		highLowAnalyseController.analyse(NUM_THREADS);
		log.info("高低分析完成.");
	}

}
