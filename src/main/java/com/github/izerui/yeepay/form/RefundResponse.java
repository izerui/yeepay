package com.github.izerui.yeepay.form;

import com.github.izerui.yeepay.YeepayException;
import com.github.izerui.yeepay.utils.DigestUtil;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by serv on 2017/4/24.
 */
public class RefundResponse {


    /**
     * 业务类型
     */
    @Getter
    @Setter
    private String r0_Cmd;
    /**
     * 退款结果
     */
    @Getter
    @Setter
    private String r1_Code;
    /**
     * 易宝交易流水号
     */
    @Getter
    @Setter
    private String r2_TrxId;
    /**
     * 退款金额
     */
    @Getter
    @Setter
    private String r3_Amt;
    /**
     * 交易币种
     */
    @Getter
    @Setter
    private String r4_Cur;
    /**
     * 退款请求号
     */
    @Getter
    @Setter
    private String r4_Order;
    /**
     * 已退手续费
     */
    @Getter
    @Setter
    private String rf_fee;
    /**
     * 签名数据
     */
    @Getter
    @Setter
    private String hmac;
    /**
     * 安全签名数据
     */
    @Getter
    @Setter
    private String hmac_safe;


    /**
     * 验证签名
     *
     * @param merSecret
     * @throws YeepayException
     */
    public void validateHmac(String merSecret) throws YeepayException {
        String[] stringArr	= {r0_Cmd, r1_Code, r2_TrxId, r3_Amt, r4_Cur};
        String localHmac 	= DigestUtil.getHmac(stringArr, merSecret);
        boolean ishmac_safe = DigestUtil.verifyCallbackHmac_safe(stringArr, hmac_safe,merSecret);

        if(!localHmac.equals(hmac) || !ishmac_safe) {
            throw new YeepayException("HMAC_ERROR", "验证签名错误");
        }

    }
}
