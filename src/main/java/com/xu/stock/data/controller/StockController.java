package com.xu.stock.data.controller;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.xu.stock.StockApiConstant;
import com.xu.stock.data.model.Stock;
import com.xu.stock.data.service.IStockService;
import com.xu.stock.data.service.impl.StockServiceHelper;
import com.xu.util.HttpClientHandle;

/**
 * 股票控制层
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
@SuppressWarnings("restriction")
@Service("stockController")
public class StockController {
	static Logger log = LoggerFactory.getLogger(StockController.class);

	@Resource
	private IStockService stockService;

	public void initStock() {
		//通过API获取股票信息
		String jsonStr = HttpClientHandle.get(StockApiConstant.EqbQuant.API_URL_GET_ALL_STOCKS);

		//封装股票对象
		List<Stock> stocks = StockServiceHelper.converStocks(jsonStr);

		//插入到数据库
		Integer result = stockService.insertStocks(stocks);
		log.info("初始化股票，数量：" + result);
	}
}
