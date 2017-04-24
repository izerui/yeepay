package com.github.izerui.yeepay;

import com.github.izerui.yeepay.form.*;
import org.junit.Test;

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
    public void testRefund() throws YeepayException {
        YeepayEngine engine = new YeepayEngine();

        RefundRequest request = new RefundRequest();
        request.setP2_Order("123");
        request.setP3_Amt("0.01");
        request.setPb_TrxId("868855800142162B");
        RefundResponse refund = engine.refund(request);
        System.out.println(refund.getR3_Amt());
    }
}
