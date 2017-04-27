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

>备注: 该sdk为原生的易宝接口请求和返回封装,建议在使用时进行二次包装。
>例如(如果非rpc接口注意安全性校验):

```

@Override
public String getPayUrl(
        @RequestParam("order") String order,
        @RequestParam("amount") String amount,
        @RequestParam("productName") String productName,
        @RequestParam(value = "callBackUrl", required = false) String callBackUrl,
        @RequestParam(value = "info", required = false) String info,
        @RequestParam(value = "channel",required = false) FrpIdEnum channel) {

    try {
        PayRequest request = new PayRequest();
        request.setP2_Order(order);
        request.setP3_Amt(amount);
        request.setP5_Pid(productName);
        request.setP8_Url(callBackUrl);
        request.setPa_MP(info);
        if(channel!=null){
            request.setPd_FrpId(channel.getValue());
        }
        recordService.recordRequest(request);
        return engine.getPayURL(request);
    }catch (YeepayException e){
        throw new BusinessException(e.getMessage(),e.getErrCode());
    }
}

@Override
public OrderQueryResponseVo queryOrder(@RequestParam("order") String order) {
    try {
        OrderQueryRequest request = new OrderQueryRequest();
        request.setP2_Order(order);
        OrderQueryResponse response = engine.queryOrder(request);
        switch (response.getR1_Code()) {
            case "1":
                if(response.getErrorMsg()!=null&&!"".equals(response.getErrorMsg())){
                    throw new BusinessException(response.getErrorMsg());
                }
                recordService.updateOrder(response);
                return beanMapper.map(response, OrderQueryResponseVo.class);
            case "50":
                throw new BusinessException("订单不存在","UN_KNOWN_ORDER_ERROR");
            default:
                throw new BusinessException("发生错误,错误码:"+response.getR1_Code()+",请重试","ERROR");
        }
    }catch (YeepayException e){
        throw new BusinessException(e.getMessage(),e.getErrCode());
    }
}


@Override
public RefundResponseVo refund(@RequestParam("amount") String amount,
                               @RequestParam("transactionId") String transactionId) {

    try {
        RefundRequest request = new RefundRequest();
        request.setP3_Amt(amount);
        request.setPb_TrxId(transactionId);
        RefundResponse response = engine.refund(request);

        switch (response.getR1_Code()) {
            case "1":
                return beanMapper.map(response, RefundResponseVo.class);
            case "2":
                throw new BusinessException("账户状态无效","ACCOUNT_STATUS_ERROR");
            case "7":
                throw new BusinessException("该订单不支持退款","UNREFUND_ERROR");
            case "10":
                throw new BusinessException("退款金额超限","TRANSFINITE_ERROR");
            case "18":
                throw new BusinessException("余额不足","LACK_FEE_ERROR");
            case "50":
                throw new BusinessException("订单不存在","UN_KNOWN_ORDER_ERROR");
            case "55":
                throw new BusinessException("历史退款未开通","REFUND_HISTORY_OPENED_ERROR");
            case "6801":
                throw new BusinessException("IP 限制","IP_ERROR");
            case "900":
                throw new BusinessException("保证金金额不足，请充值","MARGIN_LACK_FEE_ERROR");
            case "526":
                throw new BusinessException("订单未支付","UN_PAY_ERROR");
            case "10803":
                throw new BusinessException("出款功能关闭，不允许退款，请联系客户经理","NOT_ALLOW_REFUND_ERROR");
            case "32":
                throw new BusinessException("无此交易，请重新核实流水号","NO_TRAD_ERROR");
            default:
                throw new BusinessException("发生错误,错误码:"+response.getR1_Code()+",请重试","ERROR");
        }
    }catch (YeepayException e){
        throw new BusinessException(e.getMessage(),e.getErrCode());
    }
}


@Override
public RefundQueryResponseVo queryRefund(@RequestParam("refundOrder") String refundOrder,
                                         @RequestParam("transactionId") String transactionId) {

    try {
        RefundQueryRequest request = new RefundQueryRequest();
        request.setP2_Order(refundOrder);
        request.setPb_TrxId(transactionId);
        RefundQueryResponse response = engine.queryRefund(request);
        switch (response.getR1_Code()) {
            case "1":
                    return beanMapper.map(response, RefundQueryResponseVo.class);
            case "-1":
                throw new BusinessException("请求参数不合法：为空,或空字符串","PARAMS_ERROR");
            case "-2":
                throw new BusinessException("商户不存在","MER_ERROR");
            case "-3":
                throw new BusinessException("给定的流水号,没有对应的退款记录","REFUND_NOT_EXSIT_ERROR");
            default:
                throw new BusinessException("发生错误,错误码:"+response.getR1_Code()+",请重试","ERROR");
        }
    }catch (YeepayException e){
        throw new BusinessException(e.getMessage(),e.getErrCode());
    }
}

@Override
public void cancelOrder(@RequestParam("order") String order) {
    try {
        OrderCancelRequest request = new OrderCancelRequest();
        request.setPb_TrxId(order);
        OrderCancelResponse response = engine.cancelOrder(request);
        if(response.getR1_Code()==null){
            throw new BusinessException("发生错误,错误码:"+response.getR1_Code()+",请重试","ERROR");
        }else if(response.getR1_Code().equals("50")){
            throw new BusinessException("订单不存在","UN_KNOWN_ORDER_ERROR");
        }else if(response.getR1_Code().equals("53")){
            throw new BusinessException("订单已经成功，不可撤销","ORDER_TRADED_ERROR");
        }
        if(response.getErrorMsg()!=null&&!"".equals(response.getErrorMsg())){
            throw new BusinessException(response.getErrorMsg());
        }
    }catch (YeepayException e){
        throw new BusinessException(e.getMessage(),e.getErrCode());
    }
}
```