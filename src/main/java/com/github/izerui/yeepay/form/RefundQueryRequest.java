package com.github.izerui.yeepay.form;

import com.github.izerui.yeepay.SecretContext;
import com.github.izerui.yeepay.utils.DigestUtil;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by serv on 2017/4/24.
 */
public class RefundQueryRequest {

    /**
     * 业务类型
     */
    @Getter
    @Setter
    private String p0_Cmd = "RefundResults";
    /**
     * 商户编号
     */
    @Getter
    private String p1_MerId = SecretContext.getMerId();
    /**
     * 退款请求号
     */
    @Getter
    @Setter
    private String p2_Order;
    /**
     * 易宝交易流水号
     */
    @Getter
    @Setter
    private String pb_TrxId;

    public String getHmac() {
        return DigestUtil.getHmac(new String[]{p0_Cmd, p1_MerId, p2_Order, pb_TrxId}, SecretContext.getMerSecret());
    }
}
