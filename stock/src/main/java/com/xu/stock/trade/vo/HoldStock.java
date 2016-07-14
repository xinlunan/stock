package com.xu.stock.trade.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.xml.xpath.XPathConstants;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import com.xu.util.DocumentUtil;
import com.xu.util.XPathUtil;

public class HoldStock implements Serializable {

    private static final long serialVersionUID = 614981233122060832L;
    private String            stockCode;
    private String            stockName;
    private BigDecimal        stockCount;
    private Integer           sellableCount;
    private String            currency;
    private BigDecimal        costPrice;
    private BigDecimal        costTotal;
    private BigDecimal        currentTotal;
    private BigDecimal        currentPrice;
    private BigDecimal        profit;
    private BigDecimal        profitRate;
    private BigDecimal        exchangeRate;                          // 汇率
    private BigDecimal        buyCount;
    private BigDecimal        sellCount;
    private String            ownerNo;
    private String            remark;

    public static List<HoldStock> parse(String html) {
        List<HoldStock> stocks = new ArrayList<HoldStock>();
        try {
            String tempString = html.replaceAll("&nbsp;", "").replaceAll("&", "").replace("width=780", "").replaceAll(",", "").replaceAll("%", "");
            int begin = tempString.indexOf("<table border=\"1\"");
            if (begin > 0) {
                tempString = tempString.substring(begin);
                tempString = tempString.substring(0, tempString.lastIndexOf("</table>") + 8);
                Document doc = DocumentUtil.string2Doc(tempString);

                NodeList stockNodes = (NodeList) XPathUtil.parse(doc, "//table/tr[position()>1]", XPathConstants.NODESET);
                for (int i = 0; i < stockNodes.getLength(); i++) {
                    NodeList stockInfos = stockNodes.item(i).getChildNodes();

                    HoldStock stock = new HoldStock();
                    stock.setStockCode(stockInfos.item(27).getTextContent());
                    stock.setStockName(stockInfos.item(3).getTextContent());
                    stock.setStockCount(BigDecimal.valueOf(Double.valueOf(stockInfos.item(5).getTextContent())));
                    stock.setSellableCount(Integer.valueOf(stockInfos.item(7).getTextContent()));
                    stock.setCurrency(stockInfos.item(9).getTextContent());
                    stock.setCostPrice(BigDecimal.valueOf(Double.valueOf(stockInfos.item(11).getTextContent())));
                    stock.setCostTotal(BigDecimal.valueOf(Double.valueOf(stockInfos.item(31).getTextContent())));
                    stock.setCurrentPrice(BigDecimal.valueOf(Double.valueOf(stockInfos.item(21).getTextContent())));
                    stock.setCurrentTotal(BigDecimal.valueOf(Double.valueOf(stockInfos.item(19).getTextContent())));
                    stock.setProfit(BigDecimal.valueOf(Double.valueOf(stockInfos.item(13).getTextContent())));
                    stock.setProfitRate(BigDecimal.valueOf(Double.valueOf(stockInfos.item(15).getTextContent())));
                    stock.setBuyCount(BigDecimal.valueOf(Double.valueOf(stockInfos.item(23).getTextContent())));
                    stock.setSellCount(BigDecimal.valueOf(Double.valueOf(stockInfos.item(25).getTextContent())));
                    stock.setOwnerNo(stockInfos.item(29).getTextContent());
                    stock.setRemark(stockInfos.item(33).getTextContent());

                    stocks.add(stock);
                }
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return stocks;
    }

    public static void main(String[] args) {
        String html = "<div class=p_div id=chengben style=\"border: #555555 1px solid; WIDTH: 220px;POSITION: absolute; visibility:hidden;\">\n <table cellSpacing=1 cellPadding=3 width=\"100%\" align=right bgColor=f0f0f0 border=0>\n    <tbody>\n   <TR>\n  <TD height=\"30\" align=\"right\" class=td_left>股票名称：</TD>\n    <TD id=\"gpmc2\" class=td_right>&nbsp;</TD></TR>\n  <TR>\n  <TD height=\"30\" align=\"right\" class=td_left>成本价：</TD>\n <TD  class=td_right><input name=\"cbj2\" id = \"cbj2\" type=\"text\" size=\"12\"></TD></TR>\n   <TR>\n  <TD height=\"40\" colspan=2 align=\"center\">\n <input type=\"button\" name=\"Submit\" value=\" 取 消 \" onclick=\"hideCbDetail(0);\">&nbsp;&nbsp;&nbsp;&nbsp;\n  <input type=\"button\" name=\"Submit2\" value=\" 保 存 \" onclick=\"hideCbDetail(1);\"></td></tr>\n   </tbody>\n  </table>\n</div>\n<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" id=\"wt\" style=\"margin-bottom:0px;padding:0px;padding-top:5px\">\n  <tr class=\"top\">\n    <td background=\"img/wrw05.jpg\" height=\"20\" align=\"right\" valign=\"top\">\n <input type=\"button\" name=\"Submit\" style=\"font-color:#000000;cursor:default\" value=\"共5条\"/>\n    <input type=\"button\" name=\"Submit\" value=\"修改成本\" onclick=\"showCbDetail();\"/>\n   <input type=\"button\" name=\"Submit\" value=\"买入\" onclick=\"buy2();\"/>\n <input type=\"button\" name=\"Submit\" value=\"卖出\" onclick=\"sell2();\"/>\n    <input type=\"button\" name=\"Submit\" value=\"相关资讯\" onclick=\"xgzx();\"/>\n   <input type=\"button\" name=\"Submit\" value=\"刷新\" onclick=\"trade('100040');\"/>\n    <input type=\"button\" name=\"Submit\" value=\"输出\" onclick=\"outputTable('资金股份查询输出','tabbuy',1);\"/>\n <span>&nbsp;&nbsp;&nbsp;&nbsp;</span>\n </td>\n  </tr>\n</table>\n<style>\n#zongzichan td{\n    line-height:14px;\n padding:0px;\n}\n</style>\n<div style=\"border:#333333 solid 0px;\">\n  <table id=\"zongzichan\" width=\"800\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"margin-top:0px;padding:0px\">\n    <tr>\n      <td>人民币：</td>\n      <td align=\"right\">主帐号余额：</td>\n      <td>2862.52</td>\n      <td width=\"50\" align=\"right\">可用：</td>\n      <td>2862.52</td>\n    <td width=\"50\" align=\"right\">可取：</td>\n      <td>2862.52</td>\n      <td width=\"60\" align=\"right\">参考市值：</td>\n      <td>126463.00</td>\n      <td width=\"50\" align=\"right\">资产：</td>\n\n         \n      <td>129325.52</td>\n      <td width=\"50\" align=\"right\">盈亏：</td>\n      <td>-5094.35</td>\n    </tr>\n    \n   \n  \n  </table>\n</div>\n<div style=\"float:left;height:185px;background-color:#ffffff;border:#eeeeee solid 1px;margin-top:2px\">  \n<table border=\"1\" width=780 cellpadding=\"0\" cellspacing=\"0\" bordercolor=\"#909090\" id=\"tabbuy\">\n  <tr class=\"top\">\n    <td>&nbsp;</td>\n <td>证券名称</td>\n <td>证券数量</td>\n <td>可卖数量</td>\n <td>价格币种</td>\n <td>成本价</td>\n  <td>浮动盈亏</td>\n <td>盈亏比例(%)</td>\n  <td>折算汇率</td>\n <td>最新市值</td>\n    <td>当前价</td>\n    <td>今买数量</td>\n    <td>今卖数量</td>\n    <td>证券代码</td>\n    <td>股东代码</td>\n   <td>成本金额</td>\n <td>备注</td>\n  </tr>\n  \n    <tr>\n    <td style=\"color:red\" scdm=\"1\" zqdm=\"600054\" xh='0'><input type=\"radio\" name=\"zqdm\" value=\"600054\" /></td>\n    <td style=\"color:red\">黄山旅游</td>\n <td style=\"color:red\">1,300</td>\n    <td style=\"color:red\">1,300</td>\n    <td style=\"color:red\">人民币</td>\n  <td style=\"color:red\">24.520</td>\n   <td style=\"color:red\">129.61</td>\n    <td style=\"color:red\">0.41%</td>\n    <td style=\"color:red\"></td>\n    <td style=\"color:red\">32,006.00</td>\n    <td style=\"color:red\">24.62</td>\n    <td style=\"color:red\">0</td>\n    <td style=\"color:red\">0</td>\n    <td style=\"color:red\">600054</td>\n    <td style=\"color:red\">A718770420</td>\n  <td style=\"color:red\">31876.39</td>   \n  <td style=\"color:red\">&nbsp;</td>\n    </tr>\n    \n    <tr>\n    <td style=\"color:red\" scdm=\"1\" zqdm=\"600521\" xh='1'><input type=\"radio\" name=\"zqdm\" value=\"600521\" /></td>\n    <td style=\"color:red\">华海药业</td>\n <td style=\"color:red\">1,500</td>\n    <td style=\"color:red\">1,500</td>\n    <td style=\"color:red\">人民币</td>\n  <td style=\"color:red\">23.860</td>\n   <td style=\"color:red\">314.97</td>\n    <td style=\"color:red\">0.88%</td>\n    <td style=\"color:red\"></td>\n    <td style=\"color:red\">36,105.00</td>\n    <td style=\"color:red\">24.07</td>\n    <td style=\"color:red\">0</td>\n    <td style=\"color:red\">0</td>\n    <td style=\"color:red\">600521</td>\n    <td style=\"color:red\">A718770420</td>\n  <td style=\"color:red\">35790.03</td>   \n  <td style=\"color:red\">&nbsp;</td>\n    </tr>\n    \n    <tr>\n    <td style=\"color:blue\" scdm=\"1\" zqdm=\"601117\" xh='2'><input type=\"radio\" name=\"zqdm\" value=\"601117\" /></td>\n    <td style=\"color:blue\">中国化学</td>\n   <td style=\"color:blue\">700</td>\n <td style=\"color:blue\">700</td>\n <td style=\"color:blue\">人民币</td>\n <td style=\"color:blue\">11.458</td>\n  <td style=\"color:blue\">-4,212.59</td>\n    <td style=\"color:blue\">-52.52%</td>\n    <td style=\"color:blue\"></td>\n    <td style=\"color:blue\">3,808.00</td>\n    <td style=\"color:blue\">5.44</td>\n    <td style=\"color:blue\">0</td>\n    <td style=\"color:blue\">0</td>\n    <td style=\"color:blue\">601117</td>\n    <td style=\"color:blue\">A718770420</td>\n  <td style=\"color:blue\">8020.59</td>   \n  <td style=\"color:blue\">&nbsp;</td>\n    </tr>\n   \n    <tr>\n    <td style=\"color:blue\" scdm=\"2\" zqdm=\"002590\" xh='3'><input type=\"radio\" name=\"zqdm\" value=\"002590\" /></td>\n    <td style=\"color:blue\">万安科技</td>\n   <td style=\"color:blue\">800</td>\n <td style=\"color:blue\">800</td>\n <td style=\"color:blue\">人民币</td>\n <td style=\"color:blue\">32.693</td>\n  <td style=\"color:blue\">-594.46</td>\n    <td style=\"color:blue\">-2.27%</td>\n    <td style=\"color:blue\"></td>\n   <td style=\"color:blue\">25,560.00</td>\n   <td style=\"color:blue\">31.93</td>\n    <td style=\"color:blue\">0</td>\n    <td style=\"color:blue\">0</td>\n    <td style=\"color:blue\">002590</td>\n    <td style=\"color:blue\">0128652175</td>\n <td style=\"color:blue\">26154.46</td>  \n  <td style=\"color:blue\">&nbsp;</td>\n    </tr>\n   \n    <tr>\n    <td style=\"color:blue\" scdm=\"2\" zqdm=\"002635\" xh='4'><input type=\"radio\" name=\"zqdm\" value=\"002635\" /></td>\n    <td style=\"color:blue\">安洁科技</td>\n   <td style=\"color:blue\">800</td>\n <td style=\"color:blue\">800</td>\n <td style=\"color:blue\">人民币</td>\n <td style=\"color:blue\">37.145</td>\n  <td style=\"color:blue\">-731.88</td>\n    <td style=\"color:blue\">-2.46%</td>\n    <td style=\"color:blue\"></td>\n   <td style=\"color:blue\">28,984.00</td>\n   <td style=\"color:blue\">36.22</td>\n    <td style=\"color:blue\">0</td>\n    <td style=\"color:blue\">0</td>\n    <td style=\"color:blue\">002635</td>\n    <td style=\"color:blue\">0128652175</td>\n <td style=\"color:blue\">29715.88</td>  \n  <td style=\"color:blue\">&nbsp;</td>\n    </tr>\n   \n  \n  \n  \n</table>\n</div>\n\n<script>\n\nchangeTitle(\"资金股份\");\n\nrowclick = function(tr){\n  if(tr.cells[0].getAttribute(\"xh\")) {\n        selectRow( parseInt(tr.cells[0].getAttribute(\"xh\")) );\n      showHqMinute(tr.cells[0].getAttribute(\"scdm\"),tr.cells[0].getAttribute(\"zqdm\"));\n  }\n}\n\nrowDblclick = function(tr){\n   trade('100020&zqdm=' + tr.cells[0].getAttribute(\"zqdm\"));\n}\n\nts_makeSortable(document.getElementById(\"tabbuy\"),true,true);\n\n//相关资讯\nxgzx=function (){\n    var zqdm = readRadio('zqdm');\n if(zqdm==null){ \n      alert('请选择股票！'); \n } else\n        cjxwJuling(zqdm);\n}\n//资金股份界面买入、卖出\nbuy2=function (){\n    var zqdm = readRadio('zqdm');\n if(zqdm!=null) trade('100010&zqdm=' + zqdm);\n  else trade('100010');\n}\nsell2=function (){\n  var zqdm = readRadio('zqdm');\n if(zqdm!=null) trade('100020&zqdm=' + zqdm);\n  else trade('100020');\n}\n//资金股份界面修改成本价\nshowCbDetail=function ()\n{\n  var tr = null;\n    var c = document.getElementsByName('zqdm');\n   for(var i=0;i<c.length;i++) {\n     if( c[i].checked )\n            tr = c[i];\n    }\n if( tr==null ) {\n      alert( '请选择股票！' );\n        return;\n   }\n tr = tr.parentNode.parentNode;\n    document.getElementById(\"gpmc2\").innerText = tr.cells.item(1).innerText;\n    document.getElementById(\"cbj2\").value = tr.cells.item(4).innerText;\n var p_obj = document.getElementById(\"chengben\");\n\n  if(p_obj.clientHeight > 280)\n  {\n     p_obj.style.height = \"280px\";\n\n     p_obj.style.overflowY = \"auto\";\n }\n placeTo(p_obj,'botrigo','center');\n    p_obj.style.visibility = \"visible\";\n\n}\nhideCbDetail=function (flag)\n{\n\n     if(flag==1) {\n         var tr = null;\n            var c = document.getElementsByName('zqdm');\n           for(var i=0;i<c.length;i++) {\n             if( c[i].checked )\n                    tr = c[i];\n            }\n         if( tr==null ) {\n              alert( '请选择股票！' );\n                return;\n           }\n         if(confirm(\"根据相关规则，成本价修改只能对非当日交易的证券余额部分生效。如需修改成本价，建议您在交易清算后，次日发生交易之前进行修改。如继续本次修改操作请点击“确认”，否则点击“取消”。\")){\n             tr = tr.parentNode.parentNode;\n                var param = 'cbj2=' + document.getElementById(\"cbj2\").value;\n                param = param + '&scdm=' + tr.cells.item(0).getAttribute('scdm');\n             param = param + '&gddm=' + tr.cells.item(14).innerText;\n               param = param + '&zqdm=' + tr.cells.item(13).innerText;\n               //alert( param );\n             var url = \"/xtrade?jybm=100014\";\n                Ajax.sendPostRequest2(url,param, true, \"evalScript(ME.http_request.responseText)\");\n         }\n     }\n     document.getElementById(\"chengben\").style.visibility=\"hidden\";\n}\nselectRow=function (xh)\n{\n var c = document.getElementsByName(\"zqdm\");\n c[xh].checked = true;\n}\n\nzjgfTimeInterval=setTimeout('reloadZjgf()',60000);\nreloadZjgf=function()\n{\n  trade('100040');\n}\n\n</script>\n";
        List<HoldStock> stocks = parse(html);
        System.out.println(stocks);
    }

    public String getStockCode() {
        return stockCode;
    }

    public void setStockCode(String stockCode) {
        this.stockCode = stockCode;
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public BigDecimal getStockCount() {
        return stockCount;
    }

    public void setStockCount(BigDecimal stockCount) {
        this.stockCount = stockCount;
    }

    public Integer getSellableCount() {
        return sellableCount;
    }

    public void setSellableCount(Integer sellableCount) {
        this.sellableCount = sellableCount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public BigDecimal getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(BigDecimal costPrice) {
        this.costPrice = costPrice;
    }

    public BigDecimal getCostTotal() {
        return costTotal;
    }

    public void setCostTotal(BigDecimal costTotal) {
        this.costTotal = costTotal;
    }

    public BigDecimal getCurrentTotal() {
        return currentTotal;
    }

    public void setCurrentTotal(BigDecimal currentTotal) {
        this.currentTotal = currentTotal;
    }

    public BigDecimal getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(BigDecimal currentPrice) {
        this.currentPrice = currentPrice;
    }

    public BigDecimal getProfit() {
        return profit;
    }

    public void setProfit(BigDecimal profit) {
        this.profit = profit;
    }

    public BigDecimal getProfitRate() {
        return profitRate;
    }

    public void setProfitRate(BigDecimal profitRate) {
        this.profitRate = profitRate;
    }

    public BigDecimal getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(BigDecimal exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public BigDecimal getBuyCount() {
        return buyCount;
    }

    public void setBuyCount(BigDecimal buyCount) {
        this.buyCount = buyCount;
    }

    public BigDecimal getSellCount() {
        return sellCount;
    }

    public void setSellCount(BigDecimal sellCount) {
        this.sellCount = sellCount;
    }

    public String getOwnerNo() {
        return ownerNo;
    }

    public void setOwnerNo(String ownerNo) {
        this.ownerNo = ownerNo;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "HoldStock [stockCode=" + stockCode + ", stockName=" + stockName + ", stockCount=" + stockCount + ", sellableCount=" + sellableCount + ", currency=" + currency + ", costPrice=" + costPrice + ", costTotal=" + costTotal
               + ", currentTotal=" + currentTotal + ", currentPrice=" + currentPrice + ", profit=" + profit + ", profitRate=" + profitRate + ", exchangeRate=" + exchangeRate + ", buyCount=" + buyCount + ", sellCount=" + sellCount
               + ", ownerNo=" + ownerNo + ", remark=" + remark + "]";
    }

}
