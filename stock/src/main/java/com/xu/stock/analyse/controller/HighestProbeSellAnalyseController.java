package com.xu.stock.analyse.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xu.stock.analyse.service.IStockAnalyseService;
import com.xu.stock.data.model.Stock;
import com.xu.stock.data.service.IStockService;

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
@Service("highestProbeSellAnalyseController")
public class HighestProbeSellAnalyseController extends BaseStockAnalyseController {

    @Resource
    private IStockService        stockService;
	@Resource
    private IStockAnalyseService highestProbeAnalyseService;

	@Override
	public IStockAnalyseService getStockAnalyseService() {
        return highestProbeAnalyseService;
	}

    public void execute() {
        super.analyse(10);
    }

    @Override
    public List<Stock> getAnalyseStocks() {
        return stockService.getAnalyseSellStocks();
    }
}
