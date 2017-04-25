package com.github.izerui.yeepay.form;

import com.github.izerui.yeepay.YeepayEngine;
import com.github.izerui.yeepay.YeepayException;
import com.github.izerui.yeepay.utils.DigestUtil;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

/**
 * Created by serv on 2017/4/24.
 */
public class RefundQueryRequest {

    /**
     * 业务类型 (固定值)
     */
    @Getter
    @Setter
    @NonNull
    private String p0_Cmd = "RefundResults";
    /**
     * 商户编号 (商户在易宝支付系统的 唯一身份标识)
     */
    @Getter
    @NonNull
    private String p1_MerId = YeepayEngine.getMerId();
    /**
     * 退款请求号
     */
    @Getter
    @Setter
    @NonNull
    private String p2_Order;
    /**
     * 易宝交易流水号
     */
    @Getter
    @Setter
    @NonNull
    private String pb_TrxId;

    public RefundQueryRequest() throws YeepayException {
    }

    public String getHmac() throws YeepayException {
        return DigestUtil.getHmac(new String[]{p0_Cmd, p1_MerId, p2_Order, pb_TrxId}, YeepayEngine.getMerSecret());
    }
}
