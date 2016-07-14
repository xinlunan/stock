package com.xu.stock.data.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.winit.framework.dao.impl.BaseDao;
import com.xu.stock.data.dao.IStockDao;
import com.xu.stock.data.model.Stock;
import com.xu.util.DateUtil;

/**
 * 股票Dao实现
 * 
 * @version Revision History
 * 
 *          <pre>
 * Author     Version       Date        Changes
 * lunan.xu    1.0           2015-5-23     Created
 * 
 * </pre>
 * @since 1.
 */
@Repository("stockDao")
public class StockDao extends BaseDao<Stock> implements IStockDao {

    public final String SQL_GET_STOCK              = getNameSpace() + "getStock";
    public final String SQL_GET_STOCK_FOR_UPDATE   = getNameSpace() + "getStockForUpdate";
    public final String SQL_GET_ALL_STOCK          = getNameSpace() + "getAllStock";
    public final String SQL_GET_ANALYSE_BUY_STOCK  = getNameSpace() + "getAnalyseBuyStock";
    public final String SQL_GET_ANALYSE_SELL_STOCK = getNameSpace() + "getAnalyseSellStock";
    public final String SQL_GET_OLD_STOCK          = getNameSpace() + "getOldStock";
    public final String SQL_INSERT_STOCK           = getNameSpace() + "insertStock";
    public final String SQL_UPDATE_STOCK           = getNameSpace() + "updateStock";

	@Override
    public Stock getStock(String stockCode) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("stockCode", stockCode);
		return getSqlSession().selectOne(SQL_GET_STOCK, params);
    }

    @Override
    public Stock getStockForUpdate(String stockCode) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("stockCode", stockCode);
        return getSqlSession().selectOne(SQL_GET_STOCK_FOR_UPDATE, params);
    }

	@Override
    public List<Stock> getAllStocks() {
		return getSqlSession().selectList(SQL_GET_ALL_STOCK);
	}

    @Override
    public List<Stock> getAnalyseBuyStocks() {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("date", DateUtil.addDay(new Date(), -10));
        return getSqlSession().selectList(SQL_GET_ANALYSE_BUY_STOCK, params);
    }

    @Override
    public List<Stock> getAnalyseSellStocks() {
        return getSqlSession().selectList(SQL_GET_ANALYSE_SELL_STOCK);
    }

	@Override
    public List<Stock> getUnupatedStocks() {
		return getSqlSession().selectList(SQL_GET_OLD_STOCK);
	}

	@Override
    public Integer insertStock(Stock stock) {
		return getSqlSession().insert(SQL_INSERT_STOCK, stock);
	}

	@Override
    public Integer updateStock(Stock stock) {
		return getSqlSession().update(SQL_UPDATE_STOCK, stock);
	}

}
