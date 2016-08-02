package com.xu.stock.data.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.xu.stock.data.dao.IStockDailyDao;
import com.xu.stock.data.dao.IStockDao;
import com.xu.stock.data.download.StockDownloadHelper;
import com.xu.stock.data.model.Stock;
import com.xu.stock.data.model.StockDaily;
import com.xu.stock.data.service.IStockService;

/**
 * 股票service实现
 * 
 * @version Revision History
 * 
 *          <pre>
 * Author     Version       Date        Changes
 * lunan.xu    1.0           2015-5-23     Created
 * 
 *          </pre>
 * 
 * @since 1.
 */
@SuppressWarnings("restriction")
@Service("stockService")
public class StockService implements IStockService {
	protected Logger log = LoggerFactory.getLogger(this.getClass());

	@Resource
	private IStockDao stockDao;

	@Resource
	private IStockDailyDao stockDailyDao;

	@Override
    public Stock getStock(String stockCode) {
		return stockDao.getStock(stockCode);
	}

    @Override
    public Stock getStockForUpdate(String stockCode) {
        return stockDao.getStockForUpdate(stockCode);
    }

	@Override
    public List<Stock> getAllStocks() {
		return stockDao.getAllStocks();
	}

    @Override
    public List<Stock> getAnalyseBuyStocks() {
        return stockDao.getAnalyseBuyStocks();
    }

    @Override
    public List<Stock> getAnalyseSellStocks() {
        return stockDao.getAnalyseSellStocks();
    }

	@Override
    public List<Stock> getUnupatedStocks() {
		return stockDao.getUnupatedStocks();
	}

	@Override
    public Integer insertStocks(List<Stock> stocks) {
		Integer result = 0;
		for (Stock stock : stocks) {
			Stock exsitStock = stockDao.getStock(stock.getStockCode());
			if (exsitStock == null) {
				result = result + stockDao.insertStock(stock);
            } else if (!exsitStock.getStockName().equals(stock.getStockName())) {
                exsitStock.setStockName(stock.getStockName());
                stockDao.updateStock(exsitStock);
			}
		}

		return result;
	}

	@Override
    public void saveStockData(Stock stock) {
        log.info("更新数据:" + stock.getStockCode());
		filterInvalid(stock);

        List<StockDaily> dailys = stock.getStockDailys();
        stockDailyDao.saveStockDailys(dailys);

        dailys = stockDailyDao.getNoMaStockDailys(stock);
        for (StockDaily daily : dailys) {
            stockDailyDao.countDailyVolumeRatio(daily);
            stockDailyDao.countDailyMa(daily, 5);
            stockDailyDao.countDailyMa(daily, 10);
            stockDailyDao.countDailyMa(daily, 20);
            stockDailyDao.countDailyMa(daily, 30);
            stockDailyDao.countDailyMa(daily, 40);
            stockDailyDao.countDailyMa(daily, 50);
            stockDailyDao.countDailyMa(daily, 60);
        }
		stockDao.updateStock(stock);
	}

    protected void countDailyVolumeRatio(List<StockDaily> dailys, Integer noMaSize) {
        for (int i = noMaSize - 1; i >= 0; i--) {
            StockDaily thisDaily = dailys.get(i);
            Integer endIndex = dailys.size() - i > 5 ? i + 5 : dailys.size() - 1;
            BigDecimal totalAmount = BigDecimal.ZERO;
            for (int j = i + 1; j < endIndex; j++) {
                totalAmount = totalAmount.add(thisDaily.getAmount());
            }
            BigDecimal thisAmount = dailys.get(i).getAmount().multiply(BigDecimal.valueOf(Integer.valueOf(i - endIndex)));
            BigDecimal volumeRatio = thisAmount.divide(totalAmount, 4, BigDecimal.ROUND_HALF_UP);

            thisDaily.setVolumeRatio(volumeRatio);
        }
    }

    /**
     * 过滤无效数据
     * 
     * @param stock
     */
	private void filterInvalid(Stock stock) {
		if (stock.getLastDate() == null) {
			return;
		}
		List<StockDaily> dailys = stock.getStockDailys();
		List<StockDaily> invalidIndexs = new ArrayList<StockDaily>();
		if (dailys != null) {
			for (int i = 0; i < dailys.size(); i++) {
				StockDaily daily = dailys.get(i);
				if (daily.getDate().compareTo(stock.getLastDate()) <= 0) {// 日期小于最后日期
					invalidIndexs.add(daily);
				}
				if (daily.getDate().compareTo(StockDownloadHelper.getLastDate()) > 0) {// 日期大于最后有效日期
					invalidIndexs.add(daily);
				}
			}
			dailys.removeAll(invalidIndexs);
		}
        if (stock.getHasException()) {
            if (dailys != null && !dailys.isEmpty()) {
                stock.setLastDate(dailys.get(dailys.size() - 1).getDate());
                stock.setLastClose(dailys.get(dailys.size() - 1).getLastClose());
            } else {
                stock = getStock(stock.getStockCode());
            }
        } else {
            stock.setLastDate(StockDownloadHelper.getLastDate());
        }
	}

    @Override
    public StockDaily getNextDaily(String stockCode, Date date) {
        return stockDailyDao.getNextStockDaily(stockCode, date);
    }

}