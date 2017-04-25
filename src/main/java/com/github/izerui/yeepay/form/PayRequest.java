package com.github.izerui.yeepay.form;

import com.github.izerui.yeepay.SecretContext;
import com.github.izerui.yeepay.utils.DigestUtil;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

/**
 * Created by serv on 2017/4/22.
 */
public class PayRequest {

    /**
     * 业务类型 (固定值)
     */
    @Getter
    @NonNull
    private String p0_Cmd = "Buy";
    /**
     * 商户编号 (商户在易宝支付系统的 唯一身份标识)
     */
    @Getter
    @NonNull
    private String p1_MerId = SecretContext.getMerId();
    /**
     * 商户订单号 (1、若商户填写，则填写的订单号必须在商户的交易中唯一；2、若商户不填写，易宝支付会自动生成随机的商户订单号；3、已付或撤销的订单号，商户不能重复提交。)
     */
    @Getter
    @Setter
    private String p2_Order;
    /**
     * 支付金额 (单位：元；必须大于等 于0.01)
     */
    @Getter
    @Setter
    @NonNull
    private String p3_Amt;
    /**
     * 交易币种 (固定值)
     */
    @Getter
    @NonNull
    private String p4_Cur = "CNY";
    /**
     * 商品名称 (1、若为中文，请注意转 码：GBK 或GB2312。 2、商品名称如果为空， 默认显示「商品名称」 四个汉字。 3、当支付方式为网银一 键时此参数必传。)
     */
    @Getter
    @Setter
    private String p5_Pid;
    /**
     * 商品种类 (若为中文，请注意转 码：GBK 或GB2312。)
     */
    @Getter
    @Setter
    private String p6_Pcat;
    /**
     * 商品描述 (若为中文，请注意转 码：GBK 或GB2312。)
     */
    @Getter
    @Setter
    private String p7_Pdesc;
    /**
     * 回调地址 (若不填写，则支付成功 后将得不到支付成功的 通知。)
     */
    @Getter
    @Setter
    private String p8_Url;
    /**
     * 送货地址 (1 - 需要用户将送货地址 留在易宝支付系统；0 - 10 否 易宝支付有限公司——网银标准版开发手册第9 / 24页 9 不需要。 默认值为：0)
     */
    @Getter
    @Setter
    private String p9_SAF;
    /**
     * 商户扩展信息 (若为中文，请注意转 码：GBK 或GB2312。)
     */
    @Getter
    @Setter
    private String pa_MP;
    /**
     * 支付通道编号 (1、若不填写，则直接跳 转到易宝支付的默认支 付网关。 2、若填写，则直接跳到 对应的银行支付页面。)
     */
    @Getter
    @Setter
    private String pd_FrpId;
    /**
     * 订单有效期 (默认值:7)
     */
    @Getter
    @Setter
    private String pm_Period;
    /**
     * 订单有效期单位 (订单有效期的单位：year、month、day、hour、minute 、second 否则此参数将无效。默认值：day)
     */
    @Getter
    @Setter
    private String pn_Unit;
    /**
     * 应答机制 (固定值为“1”: 需要应答机制)
     */
    @Getter
    private String pr_NeedResponse = "1";
    /**
     * 考生/用户姓名 (当支付方式为网银一键时指用户姓名)
     */
    @Getter
    @Setter
    private String pt_UserName;
    /**
     * 身份证号 (当支付方式为网银一键时指用户身份证号)
     */
    @Getter
    @Setter
    private String pt_PostalCode;
    /**
     * 地区 (地区，注意中文转码)
     */
    @Getter
    @Setter
    private String pt_Address;
    /**
     * 报考序号/银行卡号 (当支付方式为网银一键时指用户的银行卡号)
     */
    @Getter
    @Setter
    private String pt_TeleNo;
    /**
     * 手机号 (当支付方式为网银一键时指用户预留银行手机号)
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
     * 用户标志 (用户唯一标识ID，绑卡支付时必传)
     */
    @Getter
    @Setter
    private String pt_LeaveMessage;


    public String getHmac() {
        String[] strArr = new String[]{p0_Cmd, p1_MerId, p2_Order, p3_Amt, p4_Cur, p5_Pid, p6_Pcat, p7_Pdesc,
                p8_Url, p9_SAF, pa_MP, pd_FrpId, pm_Period, pn_Unit, pr_NeedResponse,
                pt_UserName, pt_PostalCode, pt_Address, pt_TeleNo, pt_Mobile, pt_Email, pt_LeaveMessage};
        String hmac = DigestUtil.getHmac(strArr, SecretContext.getMerSecret());
        return hmac;
    }

}
