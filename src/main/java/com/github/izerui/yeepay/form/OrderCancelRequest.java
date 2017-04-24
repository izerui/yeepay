package com.github.izerui.yeepay.form;


import com.github.izerui.yeepay.SecretContext;
import com.github.izerui.yeepay.utils.DigestUtil;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by serv on 2017/4/24.
 */
public class OrderCancelRequest {


    /**
     * 业务类型
     */
    @Getter
    private String p0_Cmd = "CancelOrd";
    /**
     * 商户编号
     */
    @Getter
    private String p1_MerId = SecretContext.getMerId();
    /**
     * 商户订单号
     */
    @Getter
    @Setter
    private String pb_TrxId;
    /**
     * 版本号
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
        String[] strArr = {p0_Cmd, p1_MerId, pb_TrxId,pv_Ver};
        String hmac = DigestUtil.getHmac(strArr, SecretContext.getMerSecret());
        return hmac;
    }


}
