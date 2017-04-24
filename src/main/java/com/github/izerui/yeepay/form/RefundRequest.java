package com.github.izerui.yeepay.form;

import com.github.izerui.yeepay.SecretContext;
import com.github.izerui.yeepay.utils.DigestUtil;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by serv on 2017/4/24.
 */
public class RefundRequest {
    /**
     * 业务类型
     */
    @Getter
    @Setter
    private String p0_Cmd = "RefundOrd";
    /**
     * 商户编号
     */
    @Getter
    private String p1_MerId = SecretContext.getMerId();
    /**
     * 退款请求编号
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
    /**
     * 退款金额
     */
    @Getter
    @Setter
    private String p3_Amt;
    /**
     * 交易币种
     */
    @Getter
    @Setter
    private String p4_Cur = "CNY";
    /**
     * 退款说明
     */
    @Getter
    @Setter
    private String p5_Desc;

    public String getHmac(){
        String[] strArr 	= {p0_Cmd, p1_MerId, p2_Order, pb_TrxId, p3_Amt, p4_Cur, p5_Desc};
        String hmac			= DigestUtil.getHmac(strArr, SecretContext.getMerSecret());
        return hmac;
    }

}
