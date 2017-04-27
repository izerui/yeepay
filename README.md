# yeepay
易宝标准版支付 JAVA SDK （订单支付接口、单笔订单查询接口、单笔退款接口、单笔退款查询接口、单笔订单撤销接口）

[![](https://jitpack.io/v/izerui/yeepay.svg)](https://jitpack.io/#izerui/yeepay)

仓库:

```
<repository>
    <id>jitpack.io</id>
    <url>https://jitpack.io</url>
</repository>
```


依赖:

```
<dependency>
    <groupId>com.github.izerui</groupId>
    <artifactId>yeepay</artifactId>
    <version>最新版本</version>
</dependency>
```

# 如何使用:

一、设置商户编号和商户秘钥:
>通过静态方法调用、或者程序初始化方法中调用

```
        YeepayEngine.setMerId("10000457067");
        YeepayEngine.setMerSecret("U26po59182dV8d7654bo24o5z369408u4sQ3To9j6QuopAbo3gwj4h33mro4");
```

二、设置请求参数、并完成engine对象建立
>可以将 engine 对象作为单例bean放入spring上下文中

```
YeepayEngine engine = new YeepayEngine();
```
或:
```
OkHttpClient client = new OkHttpClient.Builder()
        .connectTimeout(10, TimeUnit.SECONDS)
        .connectionPool(new ConnectionPool(5,5,TimeUnit.MINUTES))
        .pingInterval(30,TimeUnit.SECONDS)
        .readTimeout(15,TimeUnit.SECONDS)
        .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .build();

YeepayEngine engine = new YeepayEngine(client);
```

三、调用接口示例

* 请求支付url
```
PayRequest request = new PayRequest();
request.setP5_Pid("35242111");
request.setP3_Amt("0.01");
request.setPd_FrpId("CCB-NET-B2C");
String url = engine.getPayURL(request);
```

* 订单查询
```
OrderQueryRequest request = new OrderQueryRequest();
request.setP2_Order("123");
OrderQueryResponse order = engine.queryOrder(request);
```

* 退款
```
RefundRequest request = new RefundRequest();
request.setP2_Order("222");
request.setP3_Amt("0.01");
request.setPb_TrxId("868855800142162B");
RefundResponse response = engine.refund(request);
```

* 退款查询
```
RefundQueryRequest request = new RefundQueryRequest();
request.setP2_Order("ddd");
request.setPb_TrxId("868855800142162B");
RefundQueryResponse response = engine.queryRefund(request);
```

* 取消订单
```
OrderCancelRequest request = new OrderCancelRequest();
request.setPb_TrxId("123");
OrderCancelResponse response = engine.cancelOrder(request);
```
