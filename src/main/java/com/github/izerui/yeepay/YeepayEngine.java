package com.github.izerui.yeepay;

import com.github.izerui.yeepay.api.PayApi;
import com.github.izerui.yeepay.converter.ConverterFactory;
import com.github.izerui.yeepay.form.*;
import com.github.izerui.yeepay.utils.QueryFormUtils;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.io.IOException;

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
