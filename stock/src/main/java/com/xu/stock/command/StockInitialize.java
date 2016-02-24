/**
 * Copyright © 2014 Winit Corp. All rights reserved.
 * This software is proprietary to and embodies the confidential
 * technology of Winit Corp.  Possession, use, or copying
 * of this software and media is authorized only pursuant to a
 * valid written license from Winit Corp or an authorized sublicensor.
 */
package com.xu.stock.command;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.xu.stock.data.controller.StockController;
import com.xu.stock.data.controller.StockIndexController;

/**
 * 股票初始化，将A股所有股票初始化到系统中。
 * 
 * @version Revision History
 * 
 *          <pre>
 * Author     Version       Date        Changes
 * lunan.xu    1.0           2015-5-24     Created
 * 
 * </pre>
 * @since 1.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:config/applicationContext.xml")
public class StockInitialize {
	static Logger log = Logger.getLogger(StockInitialize.class);

	@Resource
	private StockController stockController;
	@Resource
	private StockIndexController stockIndexController;

	/**
	 * 获取股票指数启动命令
	 */
	@Test
	public void initStock() {
		log.info("开始初始化股票...");
		stockController.initStock();
		log.info("获取股票完成.");
	}

	/**
	 * 获取股票指数启动命令
	 */
	@Test
	public void initStockIndex() {
		log.info("开始初始化股票指数...");
		stockIndexController.initStockIndex();
		log.info("获取股票指数完成.");
	}

	/**
	 * 更新股票指数启动命令
	 */
	@Test
	public void updateStockIndex() {
		log.info("开始更新股票指数...");
		stockIndexController.updateStockIndex();
		log.info("更新股票指数完成.");
	}

	/**
	 * 修复股票指数启动命令
	 * 
	 * http://ctxalgo.com/api/ohlc/ 有部分日期的指数为空
	 * 
	 */
	@Test
	public void repairStockIndex() {
		log.info("开始修复股票指数...");
		stockIndexController.repairStockIndex();
		log.info("更新修复股票指数.");
	}

}