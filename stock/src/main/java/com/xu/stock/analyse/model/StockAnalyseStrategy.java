package com.xu.stock.analyse.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 股票分时指数
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
public class StockAnalyseStrategy implements Serializable {
	private static final long serialVersionUID = -1687096421228533000L;
	// 策略ID
	private Long strategyId;
	// 策略类型
	private String strategyType;
	// 版本
	private String version;
	// 参数
	private String parameters;
	// 创建日期
	private Date created;
	// 更新日期
	private Date updated;

	public Long getStrategyId() {
		return strategyId;
	}

	public void setStrategyId(Long strategyId) {
		this.strategyId = strategyId;
	}

	public String getStrategyType() {
		return strategyType;
	}

	public void setStrategyType(String strategyType) {
		this.strategyType = strategyType;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getParameters() {
		return parameters;
	}

	public void setPrameters(String parameters) {
		this.parameters = parameters;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}

	@Override
	public String toString() {
		return "StockAnanlyseStrategy [strategyId=" + strategyId + ", strategyType=" + strategyType + ", version="
				+ version + ", parameters=" + parameters + ", created=" + created + ", updated=" + updated + "]";
	}

	public String getValue(String key) {
		return parseJSON2Map(parameters).get(key) + "";
	}

	public Integer getIntValue(String key) {
		return Integer.valueOf(parseJSON2Map(parameters).get(key) + "");
	}

	public static Map<String, Object> parseJSON2Map(String jsonStr) {
		Map<String, Object> map = new HashMap<String, Object>();
		// 最外层解析
		JSONObject json = JSONObject.fromObject(jsonStr);
		for (Object k : json.keySet()) {
			Object v = json.get(k);
			// 如果内层还是数组的话，继续解析
			if (v instanceof JSONArray) {
				ArrayList<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
				Iterator<JSONObject> it = ((JSONArray) v).iterator();
				while (it.hasNext()) {
					JSONObject json2 = it.next();
					list.add(parseJSON2Map(json2.toString()));
				}
				map.put(k.toString(), list);
			} else {
				map.put(k.toString(), v);
			}
		}
		return map;
	}
}
