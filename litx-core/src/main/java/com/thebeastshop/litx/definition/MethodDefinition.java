package com.thebeastshop.litx.definition;

import java.lang.reflect.Method;

/**
 * @author gongjun[jun.gong@thebeastshop.com]
 * @since 2017-09-11 18:21
 */
public class MethodDefinition {

    private LitTransactionType transactionType;

    private Class interfaceClass;

    private String methodName;

    private Method method;

    public MethodDefinition(LitTransactionType transactionType, Class interfaceClass, Method method) {
        this.transactionType = transactionType;
        this.interfaceClass = interfaceClass;
        this.methodName = method.getName();
        this.method = method;
    }

    public LitTransactionType getTransactionType() {
        return transactionType;
    }

    public Class getInterfaceClass() {
        return interfaceClass;
    }

    public String getMethodName() {
        return methodName;
    }

    public Method getMethod() {
        return method;
    }
}
