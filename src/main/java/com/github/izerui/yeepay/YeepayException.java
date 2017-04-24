package com.github.izerui.yeepay;

/**
 * Created by serv on 2017/4/24.
 */
public class YeepayException extends Exception{

    private String errCode;

    public YeepayException(String errCode) {
        this.errCode = errCode;
    }

    public YeepayException(String message, String errCode) {
        super(message);
        this.errCode = errCode;
    }

    public YeepayException(String message, Throwable cause, String errCode) {
        super(message, cause);
        this.errCode = errCode;
    }

    public YeepayException(Throwable cause, String errCode) {
        super(cause);
        this.errCode = errCode;
    }

    public YeepayException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, String errCode) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.errCode = errCode;
    }
}
