package com.github.izerui.yeepay.form;

import com.github.izerui.yeepay.SecretContext;
import com.github.izerui.yeepay.YeepayException;
import com.github.izerui.yeepay.utils.DigestUtil;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by serv on 2017/4/24.
 */
public class RefundResponse implements IVaildHmac{


    /**
     * 业务类型 (固定值)
     */
    @Getter
    @Setter
    private String r0_Cmd;
    /**
     * 退款结果
     * <li></li>1 退款成功
     * <li></li>2 账户状态无效
     * <li></li>7 该订单不支持退款
     * <li></li>10 退款金额超限
     * <li></li>18 余额不足
     * <li></li>50 订单不存在
     * <li></li>55 历史退款未开通
     * <li></li>6801 IP 限制
     * <li></li>900 保证金金额不足，请充值
     * <li></li>526 订单未支付
     * <li></li>10803 出款功能关闭，不允许退款，请联系客户经理
     * <li></li>32 无此交易，请重新核实易宝流水号
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
     * 交易币种 (固定值,单位人民币)
     */
    @Getter
    @Setter
    private String r4_Cur;
    /**
     * 退款请求号 (同一笔订单，每次退款都会返回一个唯一的退款请求号)
     */
    @Getter
    @Setter
    private String r4_Order;
    /**
     * 已退手续费 (单位:元)
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
     * @throws YeepayException
     */
    @Override
    public void validateHmac() throws YeepayException {
        String[] stringArr	= {r0_Cmd, r1_Code, r2_TrxId, r3_Amt, r4_Cur};
        String localHmac 	= DigestUtil.getHmac(stringArr, SecretContext.getMerSecret());
        boolean ishmac_safe = DigestUtil.verifyCallbackHmac_safe(stringArr, hmac_safe,SecretContext.getMerSecret());

        if(!localHmac.equals(hmac) || !ishmac_safe) {
            throw new YeepayException("HMAC_ERROR", "验证签名错误");
        }

    }
}
