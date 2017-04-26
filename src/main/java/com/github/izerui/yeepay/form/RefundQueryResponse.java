package com.github.izerui.yeepay.form;

import com.github.izerui.yeepay.YeepayEngine;
import com.github.izerui.yeepay.YeepayException;
import com.github.izerui.yeepay.utils.DigestUtil;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by serv on 2017/4/24.
 */
public class RefundQueryResponse implements IVaildHmac {
    /**
     * 业务类型 (固定值)
     */
    @Getter
    @Setter
    private String r0_Cmd;
    /**
     * 查询结果
     * <li></li>1: 查询成功
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
     * 退款请求号
     */
    @Getter
    @Setter
    private String r4_Order;
    /**
     * 退款申请结果
     * 1: 退款申请成功
     * -1: 请求参数不合法：为空,或空字符串
     * -2: 商户不存在
     * -3: 给定的易宝流水号,没有对应的退款记录
     */
    @Getter
    @Setter
    private String refundStatus;
    /**
     * 退至银行状态
     * <li></li>1：退款处理中；
     * <li></li>2：处理成功
     * <li></li>3：处理失败
     * <li></li>4：已退回
     */
    @Getter
    @Setter
    private String refundFrpStatus;
    /**
     * 安全签名数据
     */
    @Getter
    @Setter
    private String hmac_safe;
    /**
     * 签名数据
     */
    @Getter
    @Setter
    private String hmac;

    @Override
    public void validateHmac() {
        String[] stringArr = {r0_Cmd, r1_Code, r2_TrxId, r4_Order, refundStatus, refundFrpStatus};
        String localHmac = DigestUtil.getHmac(stringArr, YeepayEngine.getMerSecret());
        boolean ishmac_safe = DigestUtil.verifyCallbackHmac_safe(stringArr, hmac_safe, YeepayEngine.getMerSecret());

        if (!localHmac.equals(hmac) || !ishmac_safe) {
            throw new YeepayException("验证签名错误", "HMAC_ERROR");
        }
    }
}
