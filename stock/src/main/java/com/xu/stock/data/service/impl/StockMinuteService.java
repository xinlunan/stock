package com.xu.stock.data.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.xu.stock.data.dao.IStockMinuteDao;
import com.xu.stock.data.download.SinaStockMinuteDownloador;
import com.xu.stock.data.model.StockMinute;
import com.xu.stock.data.service.IStockMinuteService;

/**
 * 股票指数service实现
 * 
 * 
 * @version
 * 
 * 			<pre>
 * Author	Version		Date		Changes
 * lunan.xu 	1.0  		2016年3月30日 	Created
 *
 *          </pre>
 * 
 * @since 1.
 */
@SuppressWarnings("restriction")
@Service("stockMinuteService")
public class StockMinuteService implements IStockMinuteService {
	protected Logger log = LoggerFactory.getLogger(this.getClass());

	@Resource
	private IStockMinuteDao stockMinuteDao;

	public List<StockMinute> getStockMinutes(String stockCode, Date date) {

        List<StockMinute> stockMinutes = stockMinuteDao.getStockMinutes(stockCode, date);
        if(stockMinutes==null|| stockMinutes.isEmpty()){
            stockMinutes = SinaStockMinuteDownloador.download(stockCode, date);

            saveStockMinutes(stockMinutes);
        }
        return stockMinutes;
	}

	public void saveStockMinutes(List<StockMinute> stockMinutes) {
		stockMinuteDao.saveStockMinutes(stockMinutes);
	}
}