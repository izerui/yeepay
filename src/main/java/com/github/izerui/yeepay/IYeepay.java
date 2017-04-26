package com.github.izerui.yeepay;

import com.github.izerui.yeepay.form.*;

import java.util.Map;

/**
 * Created by serv on 2017/4/22.
 */
public interface IYeepay {

    /**
     * 生成支付链接
     */
    String getPayURL(PayRequest request);

    /**
     * 查询订单
     *
     * @param request
     * @return
     */
    OrderQueryResponse queryOrder(OrderQueryRequest request);

    /**
     * 退款
     *
     * @param request
     * @return
     * @
     */
    RefundResponse refund(RefundRequest request);


    /**
     * 退款查询
     *
     * @param request
     * @return
     * @
     */
    RefundQueryResponse queryRefund(RefundQueryRequest request);

    /**
     * 撤销订单
     *
     * @param request
     * @return
     * @
     */
    OrderCancelResponse cancelOrder(OrderCancelRequest request);


    /**
     * 验证通知的签名
     *
     * @param request
     * @
     */
    void validateCallback(Map<String, String> request);

}
