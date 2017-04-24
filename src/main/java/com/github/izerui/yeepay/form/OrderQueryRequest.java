package com.github.izerui.yeepay.form;

import com.github.izerui.yeepay.utils.DigestUtil;
import lombok.Getter;
import lombok.Setter;


/**
 * Created by serv on 2017/4/24.
 */
public class OrderQueryRequest {

    /**
     * 业务类型
     */
    @Getter
    private String p0_Cmd = "QueryOrdDetail";
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
     * 商户秘钥
     */
    @Setter
    private String merSecret;
    /**
     * 版本号
     */
    @Getter
    private String pv_Ver = "3.0";
    /**
     * 查询类型
     */
    @Getter
    private String p3_ServiceType = "2";


    /**
     * 获取签名结果
     *
     * @return
     */
    public String getHmac() {
        String[] strArr = {p0_Cmd, p1_MerId, p2_Order, pv_Ver, p3_ServiceType};
        String hmac = DigestUtil.getHmac(strArr, merSecret);
        return hmac;
    }

}
