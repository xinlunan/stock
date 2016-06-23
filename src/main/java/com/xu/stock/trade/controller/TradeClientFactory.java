package com.xu.stock.trade.controller;

import org.apache.http.impl.client.DefaultHttpClient;

import com.xu.stock.trade.controller.util.HttpsClientHandle;

@SuppressWarnings("deprecation")
public class TradeClientFactory {

    private static DefaultHttpClient httpClient = null;

    public static DefaultHttpClient getHttpClient() {
        if (httpClient == null) {

            httpClient = HttpsClientHandle.getDefaultHttpClient();
        }
        return httpClient;
    }
}
