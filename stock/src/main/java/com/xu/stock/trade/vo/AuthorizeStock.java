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

public class AuthorizeStock implements Serializable {

    private static final long serialVersionUID = 8784373552456719916L;
    private String     stockCode;
    private String     stockName;
    private String     tradeType;
    private BigDecimal authorizePrice;
    private BigDecimal authorizeCount;
    private BigDecimal tradePrice;
    private BigDecimal tradeCount;
    private String     authorizeStatus;
    private String     authorizeDate;
    private String     authorizeNo;
    private String     authorizeType;
    private String     ownerNo;

    public static List<AuthorizeStock> parse(String html) {
        List<AuthorizeStock> stocks = new ArrayList<AuthorizeStock>();
        try {
            String tempString = html.replaceAll("&nbsp;", "").replaceAll("&", "").replace("width=780", "");
            int begin = tempString.indexOf("<table width=\"100%\" border=\"1\"");
            if (begin > 0) {
                tempString = tempString.substring(begin);
                tempString = tempString.substring(0, tempString.indexOf("</table>") + 8);
                Document doc = DocumentUtil.string2Doc(tempString);

                NodeList stockNodes = (NodeList) XPathUtil.parse(doc, "//table/tr[position()>1]", XPathConstants.NODESET);
                for (int i = 0; i < stockNodes.getLength(); i++) {
                    NodeList stockInfos = stockNodes.item(i).getChildNodes();

                    AuthorizeStock stock = new AuthorizeStock();
                    stock.setStockCode(stockInfos.item(21).getTextContent());
                    stock.setStockName(stockInfos.item(3).getTextContent());
                    stock.setTradeType(stockInfos.item(5).getTextContent());
                    stock.setAuthorizePrice(BigDecimal.valueOf(Double.valueOf(stockInfos.item(7).getTextContent())));
                    stock.setAuthorizeCount(BigDecimal.valueOf(Double.valueOf(stockInfos.item(9).getTextContent())));
                    stock.setTradePrice(BigDecimal.valueOf(Double.valueOf(stockInfos.item(11).getTextContent())));
                    stock.setTradeCount(BigDecimal.valueOf(Double.valueOf(stockInfos.item(13).getTextContent())));
                    stock.setAuthorizeStatus(stockInfos.item(15).getTextContent());
                    stock.setAuthorizeDate(stockInfos.item(17).getTextContent());
                    stock.setAuthorizeNo(stockInfos.item(19).getTextContent());
                    stock.setAuthorizeType(stockInfos.item(25).getTextContent());
                    stock.setOwnerNo(stockInfos.item(23).getTextContent());

                    stocks.add(stock);
                }
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return stocks;
    }

    public static void main(String[] args) {
        String html = "<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" id=\"wt\">\n  <tbody>\n  <tr class=\"top\">\n    <td background=\"img/wrw05.jpg\" height=\"30\" align=\"right\">\n    <span><input type=\"button\" name=\"Submit\" style=\"font-color:#000000;cursor:default\"  value=\"共2条\" /></span>\n <span><input type=\"button\" id=\"chedanBtn\" value=\"批量撤单\" onclick=\"chedans();\"/></span>\n  <span><input type=\"button\" id=\"selectAll\" value=\"全选中\"></span>\n   <span><input type=\"button\" id=\"unselectAll\" value=\"全不选\" /></span>\n   <span><input type=\"button\" name=\"refresh\" value=\"刷新\" onclick=\"trade('100050&kcdbz=1');\"/></span>\n  <span><input type=\"button\" name=\"output\" value=\"输出\" onclick=\"outputTable('撤单查询输出','wtcent',1);\"/></span>\n  <span>&nbsp;&nbsp;&nbsp;&nbsp;</span>\n    </td>\n  </tr>\n   <tr>\n    <td>\n      <table width=\"100%\" border=\"1\" cellspacing=\"0\" cellpadding=\"0\" id=\"wtcent\" bordercolor=\"#C8C8C8\">\n       <tr class=\"tops\">\n         <td align=\"center\">&nbsp;</td>\n          <td align=\"center\">证券名称</td>\n            <td>买卖标志</td>\n         <td>委托价格</td>\n         <td>委托数量</td>\n         <td>成交价格</td>\n         <td>成交数量</td>\n         <td>状态说明</td>\n         <td>委托时间</td>\n         <td>委托编号</td>\n         <td>证券代码</td>\n         <td>股东代码</td>           \n          <td>报价方式</td>\n       </tr>\n   \n          <tr>\n          <td hth='hth=204&gddm=A718770420' hth2='204' xh='0' wtsj='11:01:39'><input type=\"checkbox\" name=\"hth\" value=\"hth=204&gddm=A718770420\" rowno='1'/></td>\n          <td>华海药业</td>\n         <td>卖出</td>\n           <td>25.000</td>\n           <td>1500</td>\n         <td>0.000</td>\n            <td>0</td>\n            <td>未成交</td>\n          <td>20160623</td>\n         <td>204</td>\n          <td>600521</td>\n           <td>A718770420</td>\n           <td>限价委托</td>\n       </tr>\n   \n          <tr>\n          <td hth='hth=202&gddm=A718770420' hth2='202' xh='1' wtsj='10:54:36'><input type=\"checkbox\" name=\"hth\" value=\"hth=202&gddm=A718770420\" rowno='2'/></td>\n          <td>中国化学</td>\n         <td>卖出</td>\n           <td>6.000</td>\n            <td>700</td>\n          <td>0.000</td>\n            <td>0</td>\n            <td>未成交</td>\n          <td>20160623</td>\n         <td>202</td>\n          <td>601117</td>\n           <td>A718770420</td>\n           <td>限价委托</td>\n       </tr>\n   \n  \n      </table>\n  </td>\n   </tr> \n  </tbody>\n</table>\n<script>\n\nchangeTitle(\"撤单\");\n\n\nrowclick = function(tr){\n    //for(var i=0;i<tr.cells.length;i++)\n  //  s += tr.cells.item(i).innerText+\";\";\n    selectRow( parseInt(tr.cells.item(0).getAttribute(\"xh\")) );\n var msg = '操作类别：撤单';\n  msg = msg + '\r买卖方向：' + tr.cells.item(2).innerText;\n   msg = msg + '\r股票代码：' + tr.cells.item(10).innerText;\n  msg = msg + '\r证券名称：' + tr.cells.item(1).innerText;\n   msg = msg + '\r委托价格：' + tr.cells.item(3).innerText + '元';\n msg = msg + '\r委托数量：' + tr.cells.item(4).innerText;\n   msg = msg + '\r委托时间：' + tr.cells.item(0).getAttribute(\"wtsj\");\n  msg = msg + '\r\r您确定要撤单吗？　　　　　　　　';\n   if( confirm(msg) ) {\n      chedan( tr.cells.item(0).getAttribute(\"hth\") );\n } else\n        unselectRow( parseInt(tr.cells.item(0).getAttribute(\"xh\")) );\n}\n\nts_makeSortable(document.getElementById(\"wtcent\"),true);\n\ndocument.getElementById(\"selectAll\").onclick = function(){\n  var c = document.getElementsByName(\"hth\");\n  for(var i=0;i<c.length;i++)\n       c[i].checked = true;\n};\ndocument.getElementById(\"unselectAll\").onclick = function(){\n  var c = document.getElementsByName(\"hth\");\n  for(var i=0;i<c.length;i++)\n       c[i].checked = false;\n};\n\nselectRow=function (xh)\n{\n   var c = document.getElementsByName(\"hth\");\n  c[xh].checked = true;\n}\nunselectRow=function (xh)\n{\n    var c = document.getElementsByName(\"hth\");\n  c[xh].checked = false;\n}\n\n//批量撤单\nchedan_msg=function (tr)\n{\n  var msg = '买卖方向：' + tr.cells.item(2).innerText;\n   msg = msg + '，股票代码：' + tr.cells.item(10).innerText;\n   msg = msg + '，证券名称：' + tr.cells.item(1).innerText;\n    msg = msg + '，委托价格：' + tr.cells.item(3).innerText + '元';\n  msg = msg + '，委托数量：' + tr.cells.item(4).innerText;\n    msg = msg + '，委托时间：' + tr.cells.item(0).getAttribute(\"wtsj\");\n   return msg;\n}\n\nchedans=function ()\n{\n  var tab_rows = document.getElementById(\"wtcent\").rows;\n  var s = '';\n   var c = document.getElementsByName(\"hth\");\n  var msg = '操作类别：撤单\r';\n    for(var i=0;i<c.length;i++) {\n     if( c[i].checked ) {\n          s += c[i].value + \"&\";\n          msg = msg + chedan_msg(tab_rows(parseInt(c[i].getAttribute(\"rowno\")))) + '\r';\n      }\n }\n msg = msg + '\r您确定要撤单吗？';\n if(s=='')\n     alert( '请打勾选择需要撤单的委托' );\n  else if(confirm(msg))\n     chedan(s);\n}\n\n//撤单\nchedan=function (qstring)\n{\n   var url = \"/xtrade?jybm=100013\";\n    Ajax.sendPostRequest2(url,qstring, true, \"evalScript(ME.http_request.responseText)\");\n}\n\n</script>\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n键    值\n请求   POST /xtrade?jybm=100013&random=1466651243019 HTTP/1.1\nhth=202&gddm=A718770420\n<script>alert('股东代码:A718770420  合同号:202  撤单已提交!');trade('100050&kcdbz=1');</script>\n";
        List<AuthorizeStock> stocks = parse(html);
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

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public BigDecimal getAuthorizePrice() {
        return authorizePrice;
    }

    public void setAuthorizePrice(BigDecimal authorizePrice) {
        this.authorizePrice = authorizePrice;
    }

    public BigDecimal getAuthorizeCount() {
        return authorizeCount;
    }

    public void setAuthorizeCount(BigDecimal authorizeCount) {
        this.authorizeCount = authorizeCount;
    }

    public BigDecimal getTradePrice() {
        return tradePrice;
    }

    public void setTradePrice(BigDecimal tradePrice) {
        this.tradePrice = tradePrice;
    }

    public BigDecimal getTradeCount() {
        return tradeCount;
    }

    public void setTradeCount(BigDecimal tradeCount) {
        this.tradeCount = tradeCount;
    }

    public String getAuthorizeStatus() {
        return authorizeStatus;
    }

    public void setAuthorizeStatus(String authorizeStatus) {
        this.authorizeStatus = authorizeStatus;
    }

    public String getAuthorizeDate() {
        return authorizeDate;
    }

    public void setAuthorizeDate(String authorizeDate) {
        this.authorizeDate = authorizeDate;
    }

    public String getAuthorizeNo() {
        return authorizeNo;
    }

    public void setAuthorizeNo(String authorizeNo) {
        this.authorizeNo = authorizeNo;
    }

    public String getAuthorizeType() {
        return authorizeType;
    }

    public void setAuthorizeType(String authorizeType) {
        this.authorizeType = authorizeType;
    }

    public String getOwnerNo() {
        return ownerNo;
    }

    public void setOwnerNo(String ownerNo) {
        this.ownerNo = ownerNo;
    }

    @Override
    public String toString() {
        return "AuthorizeStock [stockCode=" + stockCode + ", stockName=" + stockName + ", tradeType=" + tradeType + ", authorizePrice=" + authorizePrice + ", authorizeCount=" + authorizeCount + ", tradePrice=" + tradePrice + ", tradeCount="
               + tradeCount + ", authorizeStatus=" + authorizeStatus + ", authorizeDate=" + authorizeDate + ", authorizeNo=" + authorizeNo + ", authorizeType=" + authorizeType + ", ownerNo=" + ownerNo + "]";
    }

}
