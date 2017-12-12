package com.zhou.coin;

import com.zhou.coin.util.Utils;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.gateio.GateioExchange;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParams;

import java.io.IOException;
import java.util.List;

public class A {
    public static  void main(String[] args){
//        System.out.println(getSecretKey());
        getOrders();
    }

    /**
     * 获取支持的交易对
     */
    public void getSupportPairs(){
        String exchangeName = GateioExchange.class.getName() ;
        Exchange exchange = ExchangeFactory.INSTANCE.createExchange(exchangeName);
        List<CurrencyPair> parirs = exchange.getExchangeSymbols();
        for (CurrencyPair pair : parirs) {
            System.out.println(pair.toString());
        }
    }

    /**
     * 获取配置参数
     */
    public static String getSecretKey(){
        return Utils.getProperty("secret_key");
    }

    public static void getOrders(){
        Exchange exchange = getPrivateGateIoExchange();
        exchange.getExchangeMetaData().toJSONString();
       OpenOrdersParams openOrdersParams = exchange.getTradeService().createOpenOrdersParams();
       try {
          OpenOrders openOrders = exchange.getTradeService().getOpenOrders(openOrdersParams);
          List<LimitOrder> limitOrders = openOrders.getOpenOrders();
           for (LimitOrder order:limitOrders) {
                System.out.println(order.toString());
           }
       }catch (IOException e){
            e.printStackTrace();
       }

    }

    /**
     * 获取具备账户权限的Exchange对象
     * @return
     */
    public static Exchange getPrivateGateIoExchange(){
        return ExchangeFactory.INSTANCE.createExchangeWithApiKeys(
                GateioExchange.class.getName()
                ,Utils.getProperty("access_key")
                ,Utils.getProperty("secret_key")
        );
    }

}
