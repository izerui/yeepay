package com.github.izerui.yeepay.form;


import com.github.izerui.yeepay.SecretContext;
import com.github.izerui.yeepay.YeepayException;
import com.github.izerui.yeepay.utils.DigestUtil;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by serv on 2017/4/24.
 */
public class OrderCancelResponse implements IVaildHmac{


    /**
     * 业务类型 (固定值)
     */
    @Getter
    @Setter
    private String r0_Cmd;
    /**
     * 撤销结果
     */
    @Getter
    @Setter
    private String r1_Code;
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
    /**
     * 错误信息
     */
    @Getter
    @Setter
    private String errorMsg;


    @Override
    public void validateHmac() throws YeepayException {
        String[] stringArr	= {r0_Cmd, r1_Code};
        String localHmac	= DigestUtil.getHmac(stringArr, SecretContext.getMerSecret());
        boolean ishmac_safe = DigestUtil.verifyCallbackHmac_safe(stringArr, hmac_safe,SecretContext.getMerSecret());

        if(!localHmac.equals(hmac) || !ishmac_safe) {
            throw new YeepayException("HMAC_ERROR","验证签名错误");
        }
    }
}
