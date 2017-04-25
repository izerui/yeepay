package com.github.izerui.yeepay;

import com.github.izerui.yeepay.api.PayApi;
import com.github.izerui.yeepay.converter.ConverterFactory;
import com.github.izerui.yeepay.form.*;
import com.github.izerui.yeepay.utils.DigestUtil;
import com.github.izerui.yeepay.utils.QueryFormUtils;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.io.IOException;
import java.util.Map;

/**
 * Created by serv on 2017/4/22.
 */
public class YeepayEngine implements IYeepay {

    /** 商户编号 */
    private static String merId;
    /** 商户秘钥 */
    private static String merSecret;

    private PayApi api;

    public YeepayEngine() {
        this(new OkHttpClient());
    }

    public YeepayEngine(OkHttpClient client) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.yeepay.com/")
                .addConverterFactory(new ConverterFactory())
                .client(client).build();
        this.api = retrofit.create(PayApi.class);
    }



    public String getPayURL(PayRequest request) {
        Call<Void> call = api.payUrl(QueryFormUtils.getEncodedQueryParams(request));
        return call.request().url().toString();
    }


    @Override
    public OrderQueryResponse queryOrder(OrderQueryRequest request) throws YeepayException {
        Call<OrderQueryResponse> post = api.queryOrder(request);
        return execute(post);
    }

    @Override
    public RefundResponse refund(RefundRequest request) throws YeepayException {
        Call<RefundResponse> post = api.refund(request);
        return execute(post);
    }

    @Override
    public RefundQueryResponse queryRefund(RefundQueryRequest request) throws YeepayException {
        Call<RefundQueryResponse> post = api.queryRefund(request);
        return execute(post);
    }


    @Override
    public OrderCancelResponse cancelOrder(OrderCancelRequest request) throws YeepayException {
        Call<OrderCancelResponse> post = api.cancelOrder(request);
        return execute(post);
    }


    public void validateCallback(Map<String,String> request) throws YeepayException{
        String p1_MerId = request.get("p1_MerId");
        String r0_Cmd = request.get("r0_Cmd");
        String r1_Code = request.get("r1_Code");
        String r2_TrxId = request.get("r2_TrxId");
        String r3_Amt = request.get("r3_Amt");
        String r4_Cur = request.get("r4_Cur");
        String r5_Pid = request.get("r5_Pid");
        String r6_Order = request.get("r6_Order");
        String r7_Uid = request.get("r7_Uid");
        String r8_MP = request.get("r8_MP");
        String r9_BType = request.get("r9_BType");
        String hmac = request.get("hmac");
        String hmac_safe = request.get("hmac_safe");

        String[] strArr = {p1_MerId, r0_Cmd, r1_Code, r2_TrxId, r3_Amt, r4_Cur, r5_Pid, r6_Order, r7_Uid, r8_MP, r9_BType};

        boolean hmacIsCorrect = hmac.equals(DigestUtil.getHmac(strArr, YeepayEngine.getMerSecret()));
        boolean hmacsafeIsCorrect = DigestUtil.verifyCallbackHmac_safe(strArr, hmac_safe,YeepayEngine.getMerSecret());

        if (!hmacIsCorrect || !hmacsafeIsCorrect) {
            throw new YeepayException("HMAC_ERROR","验证签名错误");
        }
    }



    private <T> T execute(Call<T> post) throws YeepayException {
        try {
            Response<T> execute = post.execute();
            return execute.body();
        } catch (IOException e) {
            throw new YeepayException("-999", "请求失败");
        }
    }

    public static String getMerId() throws YeepayException {
        if(merId==null){
            throw new YeepayException("商户编号未设置","-990");
        }
        return merId;
    }

    public static void setMerId(String merId) {
        YeepayEngine.merId = merId;
    }

    public static String getMerSecret() throws YeepayException {
        if(merSecret==null){
            throw new YeepayException("商户秘钥未设置","-991");
        }
        return merSecret;
    }

    public static void setMerSecret(String merSecret) {
        YeepayEngine.merSecret = merSecret;
    }
}
