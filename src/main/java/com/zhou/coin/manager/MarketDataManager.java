package com.zhou.coin.manager;

import com.zhou.coin.util.ExchangeType;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.service.marketdata.MarketDataService;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

/**
 * 市场数据
 */
public class MarketDataManager {
    private static MarketDataService get(ExchangeType type){
        return ExchangeManager.getExchange(type).getMarketDataService();
    }

    /**
     * 返回最新，最高，最低 交易行情和交易量
     * GateIo 每10秒钟更新;
     * @param type
     * @param currencyPair
     * @return
     * @throws IOException
     */
    public static Ticker getTicker(ExchangeType type ,CurrencyPair currencyPair) throws IOException{
        MarketDataService service = get(type);
        return service.getTicker(currencyPair);
    }

    /**
     * 获取市场深度
     * @param type
     * @param currencyPair
     * @return
     * @throws IOException
     */
    public static OrderBook getDepthOrderBook(ExchangeType type , CurrencyPair currencyPair) throws IOException{
        MarketDataService service = get(type);
        return service.getOrderBook(currencyPair);
    }



    /**
     * 根据最大值和最小值相加除以2 得到合适的价格依据
     * @param ticker
     * @return
     */
    private static BigDecimal getSuitable(Ticker ticker){
        return ticker.getHigh().add(ticker.getLow()).divide(BigDecimal.valueOf(2));
    }


}
