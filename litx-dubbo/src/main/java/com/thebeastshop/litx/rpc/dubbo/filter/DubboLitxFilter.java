package com.thebeastshop.litx.rpc.dubbo.filter;


import com.alibaba.dubbo.rpc.*;
import com.thebeastshop.litx.content.DubboInvokeContent;
import com.thebeastshop.litx.context.TransactionContext;
import com.thebeastshop.litx.definition.DefinitionManager;
import com.thebeastshop.litx.definition.LitTransactionType;
import com.thebeastshop.litx.definition.MethodDefinition;
import com.thebeastshop.litx.transaction.LitTransactionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Dubbo分布式事务拦截器
 * @author gongjun[jun.gong@thebeastshop.com]
 * @since 2017-09-11 11:37
 */
public class DubboLitxFilter implements Filter {
    private final static Logger log = LoggerFactory.getLogger(DubboLitxFilter.class);

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        Result result = invoker.invoke(invocation);
        if (result == null || result.hasException()) {
            return result;
        }

        TransactionContext transactionContext = LitTransactionManager.getTransactionContext();
        if (transactionContext == null) {
            return result;
        }

        Class interfaceClass = invoker.getInterface();
        String methodName = invocation.getMethodName();
        Class[] paramTypes = invocation.getParameterTypes();
        MethodDefinition defination = DefinitionManager.getMethodDefaintion(interfaceClass, methodName, paramTypes);
        if (defination == null) {
            return result;
        }

        if (LitTransactionType.COMPENSABLE == defination.getTransactionType()) {
            Object[] args = invocation.getArguments();
            Object ret = result.getValue();
            DubboInvokeContent invokeContent = new DubboInvokeContent(
                    defination, args, ret);
            transactionContext.logInvokeContent(invokeContent);
        }

        return result;
    }

}
