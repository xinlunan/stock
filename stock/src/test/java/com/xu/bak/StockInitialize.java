/**
 * Copyright © 2014 Winit Corp. All rights reserved.
 * This software is proprietary to and embodies the confidential
 * technology of Winit Corp.  Possession, use, or copying
 * of this software and media is authorized only pursuant to a
 * valid written license from Winit Corp or an authorized sublicensor.
 */
package com.xu.bak;

import javax.annotation.Resource;

import org.junit.Test;

import com.xu.stock.data.controller.StockController;
import com.xu.stock.data.service.downloador.EqbQuantStockIndexDownloqdor;
import com.xu.test.BaseTestCase;

/**
 * 股票初始化，将A股所有股票初始化到系统中。
 * 
 * @version Revision History
 * 
 *          <pre>
 * Author     Version       Date        Changes
 * lunan.xu    1.0           2015-5-24     Created
 * 
 *          </pre>
 * 
 * @since 1.
 */
@SuppressWarnings("restriction")
public class StockInitialize extends BaseTestCase {

	@Resource
	private StockController stockController;
	@Resource
	private EqbQuantStockIndexDownloqdor stockIndexController;

	/**
	 * 获取股票指数启动命令
	 */
	@Test
	public void initStock() {
		try {
			log.info("开始初始化股票...");
			stockController.initStock();
			log.info("获取股票完成.");
		} catch (Exception e) {
			e.printStackTrace();
		}
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