package com.xu.stock.analyse.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xu.stock.analyse.service.IStockAnalyseService;
import com.xu.stock.data.controller.StockDailyController;

/**
 * 股票分析控制层 <Change to the actual description of this class>
 * 
 * @version
 * 
 * <pre>
 * Author	Version		Date		Changes
 * lunan.xu 	1.0  		2016年8月5日 	Created
 * </pre>
 * 
 * @since 1.
 */
@SuppressWarnings("restriction")
@Service("volumeRatioAnalyseController")
public class VolumeRatioAnalyseController extends BaseStockAnalyseController {

	@Resource
    private IStockAnalyseService volumeRatioAnalyseService;
    @Resource
    private StockDailyController stockDailyController;

	@Override
	public IStockAnalyseService getStockAnalyseService() {
        return volumeRatioAnalyseService;
    }

    public void execute() {
        download();
        super.analyse(10);
    }

    public void download() {
        stockDailyController.downloadStockDaily(50);
    }

}
