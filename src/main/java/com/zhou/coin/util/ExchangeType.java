package com.zhou.coin.util;

import org.knowm.xchange.gateio.GateioExchange;

/**
 * 交易市场
 */
public enum ExchangeType {

    GateIo(GateioExchange.class);
//    Binance(BinanceEx);

    private Class exchangeClass;
    private String accessKeyName;
    private String sercetKeyName;

    ExchangeType(Class exchangeClass) {
        this.exchangeClass = exchangeClass;
        this.accessKeyName = exchangeClass.getSimpleName() + "_access_key";
        this.sercetKeyName = exchangeClass.getSimpleName() + "_secret_key";
    }

    public String getClassName() {
        return exchangeClass.getName();
    }

    public String getAccessKeyName() {
        System.out.println("accessKeyName:"+accessKeyName);
        return accessKeyName;
    }

    public String getSercetKeyName() {
        System.out.println("sercetKeyName:"+sercetKeyName);
        return sercetKeyName;
    }
}
