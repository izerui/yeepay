package com.github.izerui.yeepay.form;

import com.github.izerui.yeepay.YeepayException;
import com.github.izerui.yeepay.utils.DigestUtil;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by serv on 2017/4/24.
 */
public class OrderQueryResponse {

    /**
     * 业务类型
     */
    @Setter
    @Getter
    private String r0_Cmd;
    /**
     * 查询结果
     */
    @Setter
    @Getter
    private String r1_Code;
    /**
     * 易宝交易流水号
     */
    @Setter
    @Getter
    private String r2_TrxId;
    /**
     * 支付金额
     */
    @Setter
    @Getter
    private String r3_Amt;
    /**
     * 交易币种
     */
    @Setter
    @Getter
    private String r4_Cur;
    /**
     * 商品名称
     */
    @Setter
    @Getter
    private String r5_Pid;
    /**
     * 商户订单号
     */
    @Setter
    @Getter
    private String r6_Order;
    /**
     * 商户扩展信息
     */
    @Setter
    @Getter
    private String r8_MP;
    /**
     * 退款请求号
     */
    @Setter
    @Getter
    private String rw_RefundRequestID;
    /**
     * 订单创建时间
     */
    @Setter
    @Getter
    private String rx_CreateTime;
    /**
     * 订单成功时间
     */
    @Setter
    @Getter
    private String ry_FinshTime;
    /**
     * 退款请求金额
     */
    @Setter
    @Getter
    private String rz_RefundAmount;
    /**
     * 订单支付状态
     */
    @Setter
    @Getter
    private String rb_PayStatus;
    /**
     * 已退款次数
     */
    @Setter
    @Getter
    private String rc_RefundCount;
    /**
     * 已退款金额
     */
    @Setter
    @Getter
    private String rd_RefundAmt;
    /**
     * 安全签名数据
     */
    @Setter
    @Getter
    private String hmac_safe;
    /**
     * 签名数据
     */
    @Setter
    @Getter
    private String hmac;

    /**
     * 错误信息
     */
    @Setter
    @Getter
    private String errorMsg;


    /**
     * 验证签名
     * @param merSecret
     * @throws YeepayException
     */
    public void validateHmac(String merSecret) throws YeepayException {
        String[] stringArr	= {r0_Cmd, r1_Code, r2_TrxId, r3_Amt, r4_Cur, r5_Pid, r6_Order, r8_MP,
                rw_RefundRequestID, rx_CreateTime, ry_FinshTime, rz_RefundAmount, rb_PayStatus,
                rc_RefundCount, rd_RefundAmt};
        String localHmac	= DigestUtil.getHmac(stringArr,merSecret);
        boolean ishmac_safe = DigestUtil.verifyCallbackHmac_safe(stringArr, hmac_safe,merSecret);

        if(!localHmac.equals(hmac) || !ishmac_safe) {
            throw new YeepayException("HMAC_ERROR","验证签名错误");
        }
    }

}
