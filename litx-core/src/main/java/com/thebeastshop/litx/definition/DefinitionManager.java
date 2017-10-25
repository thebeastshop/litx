package com.thebeastshop.litx.definition;

import com.thebeastshop.litx.content.RollbackInvokeHook;
import com.thebeastshop.litx.util.MethodUtil;

import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author gongjun[jun.gong@thebeastshop.com]
 * @since 2017-09-12 15:07
 */
public class DefinitionManager {

    public final static String ROLLBACK_METHOD_PREFIX = "rollback";

    private final static ConcurrentHashMap<String, MethodDefinition> definationMap = new ConcurrentHashMap<>();
    
    private static RollbackInvokeHook rollbackInvokeHook = null;

    public static void registerMethodDefination(Class clazz, Method method, MethodDefinition defination) {
        String key = MethodUtil.getMethodKey(clazz, method);
        definationMap.put(key, defination);
    }


    public static MethodDefinition getMethodDefaintion(Class clazz, Method method) {
        String key = MethodUtil.getMethodKey(clazz, method);
        return definationMap.get(key);
    }


    public static MethodDefinition getMethodDefaintion(
            Class clazz, String methodName, Class[] parameterTypes) {
        String key = MethodUtil.getMethodKey(clazz, methodName, parameterTypes);
        return definationMap.get(key);
    }
    
    public static RollbackInvokeHook getRollbackInvokeHook(){
    	return rollbackInvokeHook;
    }
    
    public static void setRollbackInvokeHook(RollbackInvokeHook rollbackInvokeHook){
    	DefinitionManager.rollbackInvokeHook = rollbackInvokeHook;
    }

}
