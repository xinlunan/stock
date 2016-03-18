/**
 * Copyright © 2014 Winit Corp. All rights reserved.
 * This software is proprietary to and embodies the confidential
 * technology of Winit Corp.  Possession, use, or copying
 * of this software and media is authorized only pursuant to a
 * valid written license from Winit Corp or an authorized sublicensor.
 */
package com.xu.stock.command;

import javax.annotation.Resource;

import org.junit.Test;

import com.xu.stock.data.controller.StockIndexController;
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
public class StockIndexInitializtion extends BaseTestCase {

	public static final int NUM_THREADS = 1;

	@Resource
	private StockIndexController stockIndexController;

	/**
	 * 获取股票指数启动命令
	 */
	@Test
	public void downloadIndex() {
		log.info("开始初始化股票指数...");
		stockIndexController.downloadStockIndex(NUM_THREADS);
		log.info("获取股票指数完成.");
	}

}