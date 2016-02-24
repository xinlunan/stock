package com.xu.stock.command;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.xu.stock.data.controller.StockAnalyseController;

/**
 * 股票指数获取
 * 
 * @version Revision History
 * 
 *          <pre>
 * Author     Version       Date        Changes
 * lunan.xu    1.0           2015-5-29     Created
 * 
 * </pre>
 * @since 1.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:config/applicationContext.xml")
public class StockAnalyse {
	static Logger log = Logger.getLogger(StockAnalyse.class);

	@Resource
	private StockAnalyseController stockAnalyseController;

	@Test
	public void highLowAnalyse() {
		log.info("开始更新股票指数...");
		stockAnalyseController.highLowAnalyse();
		log.info("更新股票指数完成.");
	}

}
