package com.zhou.coin.manager;

import com.zhou.coin.util.ExchangeType;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParams;

import java.io.IOException;
import java.util.List;

/**
 * 订单管理
 */
public class OrderManager {

    private static TradeService getTradeService(ExchangeType type){
        return ExchangeManager.getExchange(type).getTradeService();
    }
    /**
     * 获取当前账户所有的订单
     *
     * @param type
     * @return
     * @throws IOException
     */
    public static List<LimitOrder> getOrders(ExchangeType type) throws IOException {
        TradeService tradeService = getTradeService(type);
        OpenOrdersParams openOrdersParams = tradeService.createOpenOrdersParams();
        OpenOrders openOrders = tradeService.getOpenOrders(openOrdersParams);
        return openOrders.getOpenOrders();
    }

    /**
     * 创建订单
     * @return orderId
     */
    public static String createLimitOrder(ExchangeType type, LimitOrder limitOrder) throws IOException {
        TradeService tradeService = getTradeService(type);
        return tradeService.placeLimitOrder(limitOrder);
    }

    /**
     * 取消订单
     * @return 成功与否
     */
    public static boolean cancelLimitOrder(ExchangeType type, String orderId) throws IOException {
        TradeService tradeService = getTradeService(type);
        return tradeService.cancelOrder(orderId);
    }



}
