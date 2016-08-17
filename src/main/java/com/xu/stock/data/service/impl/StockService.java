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

        stockDailyDao.saveStockDailys(stock.getStockDailys());

        updateStockDailysMa(stock);

        stockDao.updateStock(stock);
    }

    /**
     * 更新均线、量比信息
     * 
     * @param dailys
     */
    private void updateStockDailysMa(Stock stock) {

        List<StockDaily> dailys = stockDailyDao.getNoMaStockDailys(stock);
        for (int i = 0; i < dailys.size(); i++) {
            StockDaily daily = dailys.get(i);
            if (daily.getMa5().compareTo(BigDecimal.ZERO) == -1) {
                BigDecimal sumVolume = BigDecimal.ZERO;
                BigDecimal sum5 = BigDecimal.ZERO;
                BigDecimal sum10 = BigDecimal.ZERO;
                BigDecimal sum20 = BigDecimal.ZERO;
                BigDecimal sum30 = BigDecimal.ZERO;
                BigDecimal sum40 = BigDecimal.ZERO;
                BigDecimal sum50 = BigDecimal.ZERO;
                BigDecimal sum60 = BigDecimal.ZERO;

                for (int j = 0; j < 60; j++) {
                    int index = i + j;
                    if (index < dailys.size()) {
                        StockDaily beforeDaily = dailys.get(index);
                        if (0 < j && j < 6) {// 量比，前5天汇总，不含当天
                            sumVolume = sumVolume.add(beforeDaily.getVolume().multiply(beforeDaily.getExrights()));
                        }
                        if (j < 5) {// 5日均线，5天内汇总，含当天
                            sum5 = sum5.add(beforeDaily.getClose().multiply(beforeDaily.getExrights()));
                        }
                        if (j < 10) {// 10日均线，10天内汇总，含当天
                            sum10 = sum10.add(beforeDaily.getClose().multiply(beforeDaily.getExrights()));
                        }
                        if (j < 20) {// 20均线，20天内汇总，含当天
                            sum20 = sum20.add(beforeDaily.getClose().multiply(beforeDaily.getExrights()));
                        }
                        if (j < 30) {// 30日均线，30天内汇总，含当天
                            sum30 = sum30.add(beforeDaily.getClose().multiply(beforeDaily.getExrights()));
                        }
                        if (j < 40) {// 40日均线，40天内汇总，含当天
                            sum40 = sum40.add(beforeDaily.getClose().multiply(beforeDaily.getExrights()));
                        }
                        if (j < 50) {// 50日均线，50天内汇总，含当天
                            sum50 = sum50.add(beforeDaily.getClose().multiply(beforeDaily.getExrights()));
                        }
                        if (j < 60) {// 60日均线，60天内汇总，含当天
                            sum60 = sum60.add(beforeDaily.getClose().multiply(beforeDaily.getExrights()));
                        }
                    }
                }

                int beforeSize = dailys.size() - i - 1;
                if (sumVolume.intValue() == 0) {
                    daily.setVolumeRatio(BigDecimal.ONE);
                } else {
                    daily.setVolumeRatio(daily.getVolume().multiply(daily.getExrights()).divide((sumVolume.divide(BigDecimal.valueOf(beforeSize >= 6 ? 5 : beforeSize), 4, BigDecimal.ROUND_HALF_UP)), 4, BigDecimal.ROUND_HALF_UP));
                }
                daily.setMa5(sum5.divide(BigDecimal.valueOf(beforeSize >= 5 ? 5 : beforeSize + 1), 4, BigDecimal.ROUND_HALF_UP).divide(daily.getExrights(), 4, BigDecimal.ROUND_HALF_UP));
                daily.setMa10(sum10.divide(BigDecimal.valueOf(beforeSize >= 10 ? 10 : beforeSize + 1), 4, BigDecimal.ROUND_HALF_UP).divide(daily.getExrights(), 4, BigDecimal.ROUND_HALF_UP));
                daily.setMa20(sum20.divide(BigDecimal.valueOf(beforeSize >= 20 ? 20 : beforeSize + 1), 4, BigDecimal.ROUND_HALF_UP).divide(daily.getExrights(), 4, BigDecimal.ROUND_HALF_UP));
                daily.setMa30(sum30.divide(BigDecimal.valueOf(beforeSize >= 30 ? 30 : beforeSize + 1), 4, BigDecimal.ROUND_HALF_UP).divide(daily.getExrights(), 4, BigDecimal.ROUND_HALF_UP));
                daily.setMa40(sum40.divide(BigDecimal.valueOf(beforeSize >= 40 ? 40 : beforeSize + 1), 4, BigDecimal.ROUND_HALF_UP).divide(daily.getExrights(), 4, BigDecimal.ROUND_HALF_UP));
                daily.setMa50(sum50.divide(BigDecimal.valueOf(beforeSize >= 50 ? 50 : beforeSize + 1), 4, BigDecimal.ROUND_HALF_UP).divide(daily.getExrights(), 4, BigDecimal.ROUND_HALF_UP));
                daily.setMa60(sum60.divide(BigDecimal.valueOf(beforeSize >= 60 ? 60 : beforeSize + 1), 4, BigDecimal.ROUND_HALF_UP).divide(daily.getExrights(), 4, BigDecimal.ROUND_HALF_UP));

            }
        }
        stockDailyDao.updateStockDaily(dailys);
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
                stock.setStockName(daily.getStockName());
                stock.setMarketValue(daily.getMarketValue());
                stock.setCirculationMarketValue(daily.getCirculationMarketValue());
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