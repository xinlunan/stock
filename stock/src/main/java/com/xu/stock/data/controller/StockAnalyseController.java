package com.xu.stock.data.controller;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.xu.stock.data.service.IStockAnalyseService;

/**
 * 股票分析控制层
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
@Service("stockAnalyseController")
public class StockAnalyseController {
	static Logger log = Logger.getLogger(StockAnalyseController.class);

	@Resource
	private IStockAnalyseService highLowAnalyseService;

	public void highLowAnalyse() {
		highLowAnalyseService.analyse();
	}
}
