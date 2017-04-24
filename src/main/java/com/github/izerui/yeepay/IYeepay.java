package com.github.izerui.yeepay;

import com.github.izerui.yeepay.form.*;

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
     * @param request
     * @return
     */
    OrderQueryResponse queryOrder(OrderQueryRequest request) throws YeepayException;

    /**
     * 退款
     * @param request
     * @return
     * @throws YeepayException
     */
    RefundResponse refund(RefundRequest request) throws YeepayException;


    /**
     * 退款查询
     * @param request
     * @return
     * @throws YeepayException
     */
    RefundQueryResponse queryRefund(RefundQueryRequest request) throws YeepayException;

    /**
     * 撤销订单
     * @param request
     * @return
     * @throws YeepayException
     */
    OrderCancelResponse cancelOrder(OrderCancelRequest request) throws YeepayException;

}
