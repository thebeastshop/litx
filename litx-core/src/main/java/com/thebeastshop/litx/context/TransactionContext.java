package com.thebeastshop.litx.context;

import com.thebeastshop.litx.content.InvokeContent;
import com.thebeastshop.litx.definition.CompensableMethodDefinition;
import com.thebeastshop.litx.definition.LitTransactionType;
import com.thebeastshop.litx.definition.MethodDefinition;
import com.thebeastshop.litx.util.MethodUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author gongjun[jun.gong@thebeastshop.com]
 * @since 2017-09-11 17:38
 */
public class TransactionContext {
    private final static Logger log = LoggerFactory.getLogger(TransactionContext.class);

    private final ConcurrentLinkedDeque<InvokeContent> deque = new ConcurrentLinkedDeque<>();

    public void doRollback() {
        while (!deque.isEmpty()) {
            final InvokeContent invokeContent = deque.pollLast();
            invokeContent.rollback();
        }
    }

    public void logInvokeContent(InvokeContent invokeContent) {
        MethodDefinition defination = invokeContent.getDefination();
        if (defination.getTransactionType() == LitTransactionType.COMPENSABLE) {
            CompensableMethodDefinition cpsDefination = (CompensableMethodDefinition) defination;
            log.info(" [LITX]记录RPC方法调用 " +
                    MethodUtil.getMethodNameWithArguments(
                            cpsDefination.getInterfaceClass(),
                            cpsDefination.getMethodName(),
                            invokeContent.getArguments()) +
                    ", 它的回滚方法是：" +
                            cpsDefination.getRollbackMethodDefinition().getInterfaceClass().getName() +
                            "." + cpsDefination.getRollbackMethodDefinition().getMethodName()
                    );
        }
        deque.add(invokeContent);
    }
    
    public boolean isNeedRollback(){
    	return !deque.isEmpty();
    }

}
