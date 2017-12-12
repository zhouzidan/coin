package com.zhou.coin.manager;

import com.zhou.coin.util.ExchangeType;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;

import java.util.HashMap;
import java.util.Map;

public class ExchangeManager {
    private static Map<ExchangeType,Exchange> exchangeMap = new HashMap<ExchangeType, Exchange>();

    /**
     * 获取交易市场的对象
     * @param type @ExchangeType
     * @return
     */
    public static Exchange getExchange(ExchangeType type){
        if (exchangeMap.containsKey(type) == false) {
            Exchange exchange = ExchangeFactory.INSTANCE.createExchangeWithApiKeys(
                    type.getClassName()
                    ,type.getAccessKeyName()
                    ,type.getSercetKeyName()
            );
            exchangeMap.put(type,exchange);
        }

        return exchangeMap.get(type);
    }
}
