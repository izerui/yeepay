package com.github.izerui.yeepay.form;

import com.github.izerui.yeepay.utils.DigestUtil;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by serv on 2017/4/22.
 */
public class PayRequest {

    /**
     * 业务类型
     */
    @Getter
    private String p0_Cmd = "Buy";
    /**
     * 商户编号
     */
    @Getter
    @Setter
    private String p1_MerId;
    /**
     * 商户订单号
     */
    @Getter
    @Setter
    private String p2_Order;
    /**
     * 支付金额
     */
    @Getter
    @Setter
    private String p3_Amt;
    /**
     * 交易币种
     */
    @Getter
    private String p4_Cur = "CNY";
    /**
     * 商品名称
     */
    @Getter
    @Setter
    private String p5_Pid;
    /**
     * 商品种类
     */
    @Getter
    @Setter
    private String p6_Pcat;
    /**
     * 商品描述
     */
    @Getter
    @Setter
    private String p7_Pdesc;
    /**
     * 回调地址
     */
    @Getter
    @Setter
    private String p8_Url;
    /**
     * 送货地址
     */
    @Getter
    @Setter
    private String p9_SAF;
    /**
     * 商户扩展信息
     */
    @Getter
    @Setter
    private String pa_MP;
    /**
     * 支付通道编号
     */
    @Getter
    @Setter
    private String pd_FrpId;
    /**
     * 订单有效期
     */
    @Getter
    @Setter
    private String pm_Period;
    /**
     * 订单有效期单位(year、month、day、hour、minute、second)
     */
    @Getter
    @Setter
    private String pn_Unit;
    /**
     * 应答机制
     */
    @Getter
    private String pr_NeedResponse = "1";
    /**
     * 考生/用户姓名
     */
    @Getter
    @Setter
    private String pt_UserName;
    /**
     * 身份证号
     */
    @Getter
    @Setter
    private String pt_PostalCode;
    /**
     * 地区
     */
    @Getter
    @Setter
    private String pt_Address;
    /**
     * 报考序号/银行卡号
     */
    @Getter
    @Setter
    private String pt_TeleNo;
    /**
     * 手机号
     */
    @Getter
    @Setter
    private String pt_Mobile;
    /**
     * 邮件地址
     */
    @Getter
    @Setter
    private String pt_Email;
    /**
     * 用户标志
     */
    @Getter
    @Setter
    private String pt_LeaveMessage;
    /**
     * 商户秘钥
     */
    @Setter
    private String merSecret;

    /**
     * 获取签名结果
     *
     * @return
     */
    public String getHmac() {
        String[] strArr = new String[]{p0_Cmd, p1_MerId, p2_Order, p3_Amt, p4_Cur, p5_Pid, p6_Pcat, p7_Pdesc,
                p8_Url, p9_SAF, pa_MP, pd_FrpId, pm_Period, pn_Unit, pr_NeedResponse,
                pt_UserName, pt_PostalCode, pt_Address, pt_TeleNo, pt_Mobile, pt_Email, pt_LeaveMessage};
        String hmac = DigestUtil.getHmac(strArr, merSecret);
        return hmac;
    }

}
