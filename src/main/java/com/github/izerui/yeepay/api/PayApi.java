package com.github.izerui.yeepay.api;

import com.github.izerui.yeepay.form.OrderQueryRequest;
import com.github.izerui.yeepay.form.OrderQueryResponse;
import com.github.izerui.yeepay.form.RefundRequest;
import com.github.izerui.yeepay.form.RefundResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

import java.util.Map;

/**
 * Created by serv on 2017/4/24.
 */
public interface PayApi {

    @GET("https://www.yeepay.com/app-merchant-proxy/node")
    Call<Void> payUrl(@QueryMap(encoded = true) Map<String,String> params);

    @POST("https://cha.yeepay.com/app-merchant-proxy/command")
    Call<OrderQueryResponse> queryOrder(@Body OrderQueryRequest request);

    @POST("https://cha.yeepay.com/app-merchant-proxy/command")
    Call<RefundResponse> refund(@Body RefundRequest request);
}
