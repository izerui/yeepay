package com.github.izerui.yeepay.utils;

import lombok.extern.slf4j.Slf4j;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by serv on 2017/4/24.
 */
@Slf4j
public class QueryFormUtils {

    public static Map<String,String> getEncodedQueryParams(Object obj){
        Map<String,String> params = new HashMap<>();
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass(), Object.class);
            PropertyDescriptor[] descriptors = beanInfo.getPropertyDescriptors();

            for (PropertyDescriptor descriptor : descriptors) {
                Method readMethod = descriptor.getReadMethod();
                if(readMethod!=null){
                    String value = (String) readMethod.invoke(obj);
                    if(value!=null&&!"".equals(value)){
                        params.put(descriptor.getName(), URLEncoder.encode(value,"GBK"));
                    }
                }
            }

        } catch (Exception e) {
            log.error(e.getMessage(),e);
        }
        return params;
    }
}
