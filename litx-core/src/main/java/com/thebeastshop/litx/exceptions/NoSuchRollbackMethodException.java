package com.thebeastshop.litx.exceptions;

/**
 * @author gongjun[jun.gong@thebeastshop.com]
 * @since 2017-09-12 15:51
 */
public class NoSuchRollbackMethodException extends RuntimeException {

    private String methodName;

    private String rollbackMethodName;

    public NoSuchRollbackMethodException(String methodName, String rollbackMethodName, Class paramType) {
        super("未找到" + methodName + "的补偿方法: " + rollbackMethodName + "(" + paramType.getName() + ")");
    }

    public String getRollbackMethodName() {
        return rollbackMethodName;
    }

    public String getMethodName() {
        return methodName;
    }
}
