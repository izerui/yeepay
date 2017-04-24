package com.github.izerui.yeepay;

import com.github.izerui.yeepay.form.*;
import org.junit.Test;

/**
 * Created by serv on 2017/4/24.
 */
public class PayTest {

    private String merId = "10000457067";
    private String merSecret = "U26po59182dV8d7654bo24o5z369408u4sQ3To9j6QuopAbo3gwj4h33mro4";

    @Test
    public void testPayUrl() {
        YeepayEngine engine = new YeepayEngine(merId,merSecret);

        PayRequest request = new PayRequest();
        request.setP5_Pid("的点点滴滴");
        request.setP3_Amt("0.01");
        String url = engine.getPayURL(request);
        System.out.println(url);

    }

    @Test
    public void testQueryOrder() throws YeepayException {
        YeepayEngine engine = new YeepayEngine(merId,merSecret);

        OrderQueryRequest request = new OrderQueryRequest();
        request.setP2_Order("123");
        OrderQueryResponse order = engine.queryOrder(request);
        System.out.println(order.getR3_Amt());

    }

    @Test
    public void testRefund() throws YeepayException {
        YeepayEngine engine = new YeepayEngine(merId,merSecret);

        RefundRequest request = new RefundRequest();
        request.setP2_Order("123");
        request.setP3_Amt("0.01");
        request.setPb_TrxId("868855800142162B");
        RefundResponse refund = engine.refund(request);
        System.out.println(refund.getR3_Amt());
    }
}
