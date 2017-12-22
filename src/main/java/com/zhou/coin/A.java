package com.zhou.coin;

import com.zhou.coin.manager.ExchangeManager;
import com.zhou.coin.manager.MarketDataManager;
import com.zhou.coin.util.ExchangeType;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.gateio.Gateio;
import org.knowm.xchange.gateio.GateioExchange;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class A {
    public static void main(String[] args){

//        System.out.println(GateioExchange.class.getSimpleName());
//        USDT -- EOS -- ETH -- USDT
        Currency[] currencies = {
                Currency.USDT
                ,Currency.EOS
                ,Currency.ETH
                ,Currency.USDT
        };

        try {
            handle(ExchangeType.GateIo,currencies);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void handle(ExchangeType type,Currency[] currencies) throws IOException{
        Currency last ;
        Currency current ;

        for (int i = 1; i < currencies.length; i++) {
            last = currencies[i - 1];
            current = currencies[i];
            CurrencyPair currencyPair = getCurrencyPair(last,current);
            OrderBook orderBook = MarketDataManager.getDepthOrderBook(type,currencyPair);
            if (currencyPair.counter.equals(last)){
                // 需要买入，所以获取卖出的数据
                List<LimitOrder> limitOrders = orderBook.getAsks().subList(0,3);
            }else if(currencyPair.counter.equals(current)){
                // 需要卖出，所以获取买入的数据
                List<LimitOrder> limitOrders = orderBook.getBids().subList(0,3);
            }
        }
    }

    // USDT BTC ETH
    private static CurrencyPair getCurrencyPair(Currency last , Currency current){
        CurrencyPair currencyPair = null;
        if (last.equals(Currency.USDT)){
            currencyPair = new CurrencyPair(current,last);
        }else if (current.equals(Currency.USDT)){
            currencyPair = new CurrencyPair(last,current);
        }
        else if (last.equals(Currency.BTC)){
            currencyPair = new CurrencyPair(current,last);
        }else if (current.equals(Currency.BTC)){
            currencyPair = new CurrencyPair(last,current);
        }

        else if (last.equals(Currency.ETH)){
            currencyPair = new CurrencyPair(current,last);
        }else if(current.equals(Currency.ETH)){
            currencyPair = new CurrencyPair(last,current);
        }

        return currencyPair;
    }

    private static Boolean isCounterCurrency(Currency currency){
        return currency.equals(Currency.USDT);
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

    public void aa(){
        try {
            CurrencyPair EOS_USDT = new CurrencyPair(Currency.EOS,Currency.USDT);
            OrderBook orderBookEOS_USDT = MarketDataManager.getDepthOrderBook(ExchangeType.GateIo,EOS_USDT);
            //知道市场上，谁在卖EOS -- 我就可以买入EOS 价格越来越高 AEC
            List<LimitOrder> orders_EOS_USDT = orderBookEOS_USDT.getAsks().subList(0,1);
            for (LimitOrder limitOrder:orders_EOS_USDT) {
                System.out.println(limitOrder);
            }

            //知道市场上，谁在买EOS -- 我就可以卖掉EOS得到ETH 价格越来越低 DESC
            OrderBook orderBookEOS_ETH = MarketDataManager.getDepthOrderBook(ExchangeType.GateIo,CurrencyPair.EOS_ETH);
            List<LimitOrder> ordersEOS_ETH = orderBookEOS_ETH.getBids().subList(0,1);
            for (LimitOrder limitOrder:ordersEOS_ETH) {
                System.out.println(limitOrder);
            }

            //知道市场上谁在买ETH
            CurrencyPair ETH_USDT = new CurrencyPair(Currency.ETH,Currency.USDT);
            OrderBook orderBookETH_USDT = MarketDataManager.getDepthOrderBook(ExchangeType.GateIo,ETH_USDT);
            List<LimitOrder> ordersETH_USDT = orderBookETH_USDT.getBids().subList(0,1);
            for (LimitOrder limitOrder:ordersETH_USDT) {
                System.out.println(limitOrder);
            }

            BigDecimal result = ordersEOS_ETH.get(0).getLimitPrice().multiply(
                    ordersETH_USDT.get(0).getLimitPrice()
            ).setScale(3, RoundingMode.DOWN).divide(
                    orders_EOS_USDT.get(0).getLimitPrice()
                    ,RoundingMode.DOWN
            );



            System.out.println(result);

        }catch (IOException e){
            e.printStackTrace();
        }
    }



}
