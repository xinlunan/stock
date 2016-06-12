package com.xu.stock.analyse.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xu.stock.analyse.service.IStockAnalyseService;
import com.xu.stock.data.controller.StockDailyController;

/**
 * 股票分析控制层
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
@Service("highestProbeAnalyseController")
public class HighestProbeAnalyseController extends BaseStockAnalyseController {

	@Resource
    private IStockAnalyseService highestProbeBuyAnalyseService;
    @Resource
    private StockDailyController stockDailyController;

	@Override
	public IStockAnalyseService getStockAnalyseService() {
        return highestProbeBuyAnalyseService;
    }

    public void execute() {
        download();
        super.analyse(10);
    }

    public void download() {
        stockDailyController.downloadStockDaily(50);
    }

}
