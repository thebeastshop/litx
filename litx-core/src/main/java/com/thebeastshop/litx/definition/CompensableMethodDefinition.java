package com.thebeastshop.litx.definition;

import java.lang.reflect.Method;

/**
 * @author gongjun[jun.gong@thebeastshop.com]
 * @since 2017-09-11 18:28
 */
public class CompensableMethodDefinition extends MethodDefinition {

    private MethodDefinition rollbackMethodDefinition;

    public CompensableMethodDefinition(Class interfaceClass, Method method,
                                       MethodDefinition rollbackMethodDefinition) {
        super(LitTransactionType.COMPENSABLE, interfaceClass, method);
        this.rollbackMethodDefinition = rollbackMethodDefinition;
    }

    public MethodDefinition getRollbackMethodDefinition() {
        return rollbackMethodDefinition;
    }
}
