package com.github.izerui.yeepay;

import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
import java.util.Properties;

/**
 * Created by serv on 2017/4/24.
 */
@Slf4j
public class SecretContext {
    private static String merId;
    private static String merSecret;
    static {
        Properties prop = new Properties();
        try {
            InputStream resourceAsStream = ClassLoader.getSystemResourceAsStream("yeepay.properties");
            prop.load(resourceAsStream);
            merId = prop.getProperty("merId");
            merSecret = prop.getProperty("merSecret");
        } catch(Exception e) {
            log.error("加载yeepay配置文件出错",e.getMessage());
        }
    }

    public static String getMerId() {
        return merId;
    }

    public static String getMerSecret() {
        return merSecret;
    }
}
