package com.xu.stock.data.service.analyse;

import java.util.ArrayList;
import java.util.List;

import com.xu.stock.data.model.StockIndex;
import com.xu.stock.data.service.IStockAnalyst;

/**
 * 最高点探测分析师
 * 
 * @version
 * 
 * 			<pre>
 * Author	Version		Date		Changes
 * lunan.xu 	1.0  		2016年3月19日 	Created
 *
 *          </pre>
 * 
 * @since 1.
 */
public class HighestProbeAnalyst implements IStockAnalyst {
	/** 探测配置信息 */
	HighestProbeConfig config = new HighestProbeConfig();
	/** 分析后的指标 */
	HighestProbeValue value = new HighestProbeValue();
	/** 分析样本指数 */
	List<StockIndex> indexs = new ArrayList<StockIndex>();
	List<StockIndex> buyPoints = new ArrayList<StockIndex>();

	public static HighestProbeAnalyst newInstance() {
		return new HighestProbeAnalyst();
	}

	public void putStockIndex(StockIndex index) {
		indexs.add(index);

		// TODO

	}

	public List<StockIndex> getBuyPoints() {
		return buyPoints;
	}

	private HighestProbeAnalyst() {
	}

}
