package com.thebeastshop.litx.util;

import com.alibaba.fastjson.JSON;

import java.lang.reflect.Method;

/**
 * @author gongjun[jun.gong@thebeastshop.com]
 * @since 2017-09-12 17:05
 */
public class MethodUtil {


    public static String getMethodKey(Class clazz, Method method) {
        String name = getMethodNameWithParameterTypes(clazz, method);
        return MD5Util.makeMD5(name);
    }

    public static String getMethodKey(Class clazz,
                                      String methodName, Class[] paramTypes) {
        String name = getMethodNameWithParameterTypes(clazz, methodName, paramTypes);
        return MD5Util.makeMD5(name);
    }

    public static String getMethodNameWithParameterTypes(Class clazz, Method method) {
        return getMethodNameWithParameterTypes(clazz, method.getName(), method.getParameterTypes());
    }


    public static String getMethodNameWithParameterTypes(Class clazz,
                                 String methodName, Class[] paramTypes) {
        String name = clazz.getName() + "." +
                methodName + "(";
        StringBuilder builder = new StringBuilder(name);
        for (int i = 0, len = paramTypes.length; i < len; i++) {
            Class pType = paramTypes[i];
            builder.append(pType.getName());
            if (i < len - 1) {
                builder.append(',');
            }
        }
        builder.append(")");
        return builder.toString();
    }

    public static String getMethodNameWithArguments(
            Class clazz, String methodName, Object[] arguments) {
        String name = clazz.getName() + "." +
                methodName + "(";
        StringBuilder builder = new StringBuilder(name);
        for (int i = 0, len = arguments.length; i < len; i++) {
            Object arg = arguments[i];
            builder.append(JSON.toJSONString(arg));
            if (i < len - 1) {
                builder.append(',');
            }
        }
        builder.append(")");
        return builder.toString();
    }

}
