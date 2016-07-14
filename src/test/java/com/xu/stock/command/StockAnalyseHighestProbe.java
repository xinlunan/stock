/**
 * Copyright © 2014 Winit Corp. All rights reserved.
 * This software is proprietary to and embodies the confidential
 * technology of Winit Corp.  Possession, use, or copying
 * of this software and media is authorized only pursuant to a
 * valid written license from Winit Corp or an authorized sublicensor.
 */
package com.xu.stock.command;

import java.util.Date;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;

import com.xu.stock.analyse.controller.HighestProbeAnalyseController;
import com.xu.stock.data.controller.StockDailyController;
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
public class StockAnalyseHighestProbe extends BaseTestCase {

    public static final int               NUM_THREADS = 10;
    @Resource
    private StockDailyController             stockDailyController;
    @Resource
    private HighestProbeAnalyseController highestProbeAnalyseController;

    @Test
    public void highestProbeBuyAnalyse() {
        log.info("最高点试探分析购买开始...");
        highestProbeAnalyseController.analyse(NUM_THREADS);
        log.info("最高点试探分析购买完成.");
    }

    @SuppressWarnings("deprecation")
    @Before
    public void download() {
        if (new Date().getHours() >= 20) {
            stockDailyController.downloadStockDaily(10);
        }
    }


}
