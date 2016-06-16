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

import com.xu.stock.analyse.controller.StockAnalyseResultMailController;
import com.xu.test.BaseTestCase;

/**
 * 股票初始化，将A股所有股票初始化到系统中。
 * 
 * @version Revision History
 * 
 * <pre>
 * Author     Version       Date        Changes
 * lunan.xu    1.0           2015-5-24     Created
 * </pre>
 * 
 * @since 1.
 */
@SuppressWarnings("restriction")
public class StockAnalyseMail extends BaseTestCase {

    @Resource
    private StockAnalyseResultMailController stockAnalyseResultMailController;

    @Test
    public void highestProbeBuyAnalyse() {
        log.info("发送分析结果...");
        stockAnalyseResultMailController.execute();
        log.info("发送分析结果完成.");
    }

}
