package com.zhou.coin.util;

import org.knowm.xchange.gateio.GateioExchange;

/**
 * 交易市场
 */
public enum ExchangeType {

    GateIo(GateioExchange.class.getName());
//    Binance(BinanceEx);

    private String className;
    private String accessKeyName;
    private String sercetKeyName;

    ExchangeType(String className) {
        this.className = className;
        this.accessKeyName = className + "_access_key";
        this.sercetKeyName = className + "_secret_key";
    }

    public String getClassName() {
        return className;
    }

    public String getAccessKeyName() {
        return accessKeyName;
    }

    public String getSercetKeyName() {
        return sercetKeyName;
    }
}
