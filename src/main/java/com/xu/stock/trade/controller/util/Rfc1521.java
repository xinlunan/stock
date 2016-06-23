package com.xu.stock.trade.controller.util;

import java.io.FileReader;

import javax.script.ScriptEngineManager;

public class Rfc1521 {

    @SuppressWarnings("restriction")
    public static String encode(String src) {
        ScriptEngineManager manager = new ScriptEngineManager();
        javax.script.ScriptEngine engine = manager.getEngineByName("javascript");
        try {
            engine.eval(new FileReader("D:/workspace/lunan.xu/myproject/xulunan.stock/stock/src/main/java/com/xu/stock/trade/controller/util/rfc1521.js"));
            return (String) engine.eval("R1521.encode('" + src + "')");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
