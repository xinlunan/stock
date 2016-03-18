package com.xu.stock.command;

import javax.annotation.Resource;

import org.junit.Test;

import com.xu.stock.data.controller.StockAnalyseController;
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

	@Resource
	private StockAnalyseController stockAnalyseController;

	@Test
	public void highLowAnalyse() {
		log.info("开始更新股票指数...");
		stockAnalyseController.highLowAnalyse();
		log.info("更新股票指数完成.");
	}

}
