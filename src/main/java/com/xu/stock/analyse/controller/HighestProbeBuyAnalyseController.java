package com.xu.stock.analyse.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xu.stock.analyse.service.IStockAnalyseService;

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
@Service("highestProbeBuyAnalyseController")
public class HighestProbeBuyAnalyseController extends BaseStockAnalyseController {

	@Resource
	private IStockAnalyseService highestProbeBuyAnalyseService;

	@Override
	public IStockAnalyseService getStockAnalyseService() {
		return highestProbeBuyAnalyseService;
	}

}
