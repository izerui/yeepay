package com.github.izerui.yeepay;

import com.github.izerui.yeepay.form.*;
import org.apache.commons.beanutils.PropertyUtils;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by serv on 2017/4/24.
 */
public class PayTest {

    @Test
    public void testPayUrl() {
        YeepayEngine engine = new YeepayEngine();

        PayRequest request = new PayRequest();
        request.setP5_Pid("的点点滴滴");
        request.setP3_Amt("0.01");
        String url = engine.getPayURL(request);
        System.out.println(url);

    }

    @Test
    public void testQueryOrder() throws YeepayException {
        YeepayEngine engine = new YeepayEngine();

        OrderQueryRequest request = new OrderQueryRequest();
        request.setP2_Order("123");
        OrderQueryResponse order = engine.queryOrder(request);
        System.out.println(order.getR3_Amt());

    }

    @Test
    public void testRefund() throws YeepayException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        YeepayEngine engine = new YeepayEngine();

        RefundRequest request = new RefundRequest();
        request.setP2_Order("1222223");
        request.setP3_Amt("0.01");
        request.setPb_TrxId("868855800142162222B");
        RefundResponse refund = engine.refund(request);
        System.out.println(PropertyUtils.describe(refund).toString());
    }

    @Test
    public void testRefundQuery() throws YeepayException {
        YeepayEngine engine = new YeepayEngine();

        RefundQueryRequest request = new RefundQueryRequest();
        request.setP2_Order("123");
        request.setPb_TrxId("868855800142162B");
        RefundQueryResponse refund = engine.queryRefund(request);
        System.out.println(refund.getRefundFrpStatus());
    }

    @Test
    public void testCancelOrder() throws YeepayException {
        YeepayEngine engine = new YeepayEngine();

        OrderCancelRequest request = new OrderCancelRequest();
        request.setPb_TrxId("868855800142162B");
        OrderCancelResponse refund = engine.cancelOrder(request);
        System.out.println(refund.getR1_Code());
    }
}
