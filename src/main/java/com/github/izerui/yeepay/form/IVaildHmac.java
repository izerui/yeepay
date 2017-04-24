package com.github.izerui.yeepay.form;

import com.github.izerui.yeepay.YeepayException;

/**
 * Created by serv on 2017/4/24.
 */
public interface IVaildHmac {
    void validateHmac() throws YeepayException;
}
