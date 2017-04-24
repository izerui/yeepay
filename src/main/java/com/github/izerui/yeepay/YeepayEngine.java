package com.github.izerui.yeepay;

import com.github.izerui.yeepay.api.PayApi;
import com.github.izerui.yeepay.converter.ConverterFactory;
import com.github.izerui.yeepay.form.*;
import com.github.izerui.yeepay.utils.QueryFormUtils;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.io.IOException;

/**
 * Created by serv on 2017/4/22.
 */
@Slf4j
public class YeepayEngine implements IYeepay {

    private PayApi api;

    public YeepayEngine() {
        this(new OkHttpClient());
    }

    public YeepayEngine(OkHttpClient client) {
        OkHttpClient newClient = new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor())
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.yeepay.com/")
                .addConverterFactory(new ConverterFactory())
                .client(newClient).build();
        this.api = retrofit.create(PayApi.class);
    }


    public String getPayURL(PayRequest request){
        Call<Void> call = api.payUrl(QueryFormUtils.getEncodedQueryParams(request));
        return call.request().url().toString();
    }


    @Override
    public OrderQueryResponse queryOrder(OrderQueryRequest request) throws YeepayException {
        Call<OrderQueryResponse> post = api.queryOrder(request);
        try {
            Response<OrderQueryResponse> execute = post.execute();
            OrderQueryResponse body = execute.body();

            return body;
        } catch (IOException e) {
            throw new YeepayException("-999","请求失败");
        }
    }

    @Override
    public RefundResponse refund(RefundRequest request) throws YeepayException {
        Call<RefundResponse> post = api.refund(request);
        try {
            Response<RefundResponse> execute = post.execute();
            RefundResponse body = execute.body();

            return body;
        } catch (Exception e) {
            throw new YeepayException("-999","请求失败");
        }
    }

    @Override
    public RefundQueryResponse queryRefund(RefundQueryRequest request) throws YeepayException {
        Call<RefundQueryResponse> post = api.queryRefund(request);
        try {
            Response<RefundQueryResponse> execute = post.execute();
            RefundQueryResponse body = execute.body();

            return body;
        } catch (Exception e) {
            throw new YeepayException("-999","请求失败");
        }
    }
}
