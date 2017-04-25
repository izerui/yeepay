package com.github.izerui.yeepay.form;

import com.github.izerui.yeepay.SecretContext;
import com.github.izerui.yeepay.YeepayException;
import com.github.izerui.yeepay.utils.DigestUtil;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by serv on 2017/4/24.
 */
public class OrderQueryResponse implements IVaildHmac{

    /**
     * 业务类型 (固定值)
     */
    @Setter
    @Getter
    private String r0_Cmd;
    /**
     * 查询结果 (1：查询正常；50：订单不存在)
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
     * 支付金额 (单位：元；必须大于等 于0.01)
     */
    @Setter
    @Getter
    private String r3_Amt;
    /**
     * 交易币种 (固定值)
     */
    @Setter
    @Getter
    private String r4_Cur;
    /**
     * 商品名称 (1、若为中文，请注意转 码：GBK 或GB2312。 2、商品名称如果为空， 默认显示「商品名称」 四个汉字。 3、当支付方式为网银一 键时此参数必传。)
     */
    @Setter
    @Getter
    private String r5_Pid;
    /**
     * 商户订单号 (1、若商户填写，则填写的订单号必须在商户的交易中唯一；2、若商户不填写，易宝支付会自动生成随机的商户订单号；3、已付或撤销的订单号，商户不能重复提交。)
     */
    @Setter
    @Getter
    private String r6_Order;
    /**
     * 商户扩展信息 (若为中文，请注意转 码：GBK 或GB2312。)
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
     * <ul>
     *     <li>INIT：未支付
     *     <li>CANCELED：已取消
     *     <li>SUCCESS：已支付
     * </ul>
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
     * 已退款金额 (单位:元)
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
     * @throws YeepayException
     */
    @Override
    public void validateHmac() throws YeepayException {
        String[] stringArr	= {r0_Cmd, r1_Code, r2_TrxId, r3_Amt, r4_Cur, r5_Pid, r6_Order, r8_MP,
                rw_RefundRequestID, rx_CreateTime, ry_FinshTime, rz_RefundAmount, rb_PayStatus,
                rc_RefundCount, rd_RefundAmt};
        String localHmac	= DigestUtil.getHmac(stringArr, SecretContext.getMerSecret());
        boolean ishmac_safe = DigestUtil.verifyCallbackHmac_safe(stringArr, hmac_safe,SecretContext.getMerSecret());

        if(!localHmac.equals(hmac) || !ishmac_safe) {
            throw new YeepayException("HMAC_ERROR","验证签名错误");
        }
    }

}
