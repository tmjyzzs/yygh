package com.requestSwitch;

import com.helper.HttpRequestHelper;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author 便利店狗砸ha
 * 封装request对象装换成为dao对象
 */
public class RequestSwitch {
    public static Map<String,Object> switchMap(HttpServletRequest request){
        Map<String, String[]> parameterMap = request.getParameterMap();

        return HttpRequestHelper.switchMap(parameterMap);
    }
}
