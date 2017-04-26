package com.github.izerui.yeepay.form;


import com.github.izerui.yeepay.YeepayEngine;
import com.github.izerui.yeepay.utils.DigestUtil;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

/**
 * Created by serv on 2017/4/24.
 */
public class OrderCancelRequest {


    /**
     * 业务类型 (固定值)
     */
    @Getter
    @NonNull
    private String p0_Cmd = "CancelOrd";
    /**
     * 商户编号 (商户在易宝支付系统的 唯一身份标识)
     */
    @Getter
    @NonNull
    private String p1_MerId = YeepayEngine.getMerId();
    /**
     * 商户订单号 (原订单的商户订单号)
     */
    @Getter
    @Setter
    @NonNull
    private String pb_TrxId;
    /**
     * 版本号 (固定值)
     */
    @Getter
    @Setter
    private String pv_Ver = "1";

    /**
     * 获取签名结果
     *
     * @return
     */
    public String getHmac() {
        String[] strArr = {p0_Cmd, p1_MerId, pb_TrxId, pv_Ver};
        String hmac = DigestUtil.getHmac(strArr, YeepayEngine.getMerSecret());
        return hmac;
    }


}
