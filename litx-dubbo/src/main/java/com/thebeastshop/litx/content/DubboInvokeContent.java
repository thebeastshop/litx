package com.thebeastshop.litx.content;

import com.thebeastshop.litx.definition.CompensableMethodDefinition;
import com.thebeastshop.litx.definition.DefinitionManager;
import com.thebeastshop.litx.definition.LitTransactionType;
import com.thebeastshop.litx.definition.MethodDefinition;
import com.thebeastshop.litx.spring.LitxDubboDefinationScanner;
import com.thebeastshop.litx.util.MethodUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author gongjun[jun.gong@thebeastshop.com]
 * @since 2017-09-12 17:54
 */
public class DubboInvokeContent implements InvokeContent {

    private final static Logger log = LoggerFactory.getLogger(DubboInvokeContent.class);

    private MethodDefinition defination;


    private Object[] arguments;

    private Object result;

    public DubboInvokeContent(MethodDefinition defination, Object[] arguments, Object result) {
        this.defination = defination;
        this.arguments = arguments;
        this.result = result;
    }

    @Override
    public MethodDefinition getDefination() {
        return defination;
    }

    @Override
    public Object[] getArguments() {
        return arguments;
    }

    @Override
    public Object getResult() {
        return result;
    }


    @Override
    public void rollback() {
        tryRollback(0, null);
    }


    private Object tryRollback(int tryCount, Throwable th) {
        if (tryCount > 1) {
            log.error("超过回滚重试上限, {}", th);
            return null;
        }
        Object rollbackResult = null;
        Object bean = LitxDubboDefinationScanner.getApplicationContext().getBean(defination.getInterfaceClass());
        if (LitTransactionType.COMPENSABLE == defination.getTransactionType()) {
            CompensableMethodDefinition compensableMethodDefination =
                    (CompensableMethodDefinition) defination;
            MethodDefinition rollbackDefintation =
                    compensableMethodDefination.getRollbackMethodDefinition();
            Method rollbackMethod = rollbackDefintation.getMethod();
            try {
                rollbackResult = rollbackMethod.invoke(bean, result);
                
                log.info(" [LITX]成功回滚RPC方法 " + MethodUtil.getMethodNameWithArguments(
                        defination.getInterfaceClass(),
                        defination.getMethod().getName(),
                        arguments
                ));
            } catch (Exception e) {
            	log.info(" [LITX]回滚RPC方法出错 " + MethodUtil.getMethodNameWithArguments(
                        defination.getInterfaceClass(),
                        defination.getMethod().getName(),
                        arguments
                ));
                tryRollback(tryCount + 1, e);
                if(tryCount>=1){
                	RollbackInvokeHook rollbackInvokeHook = DefinitionManager.getRollbackInvokeHook();
                    if(rollbackInvokeHook != null){
                    	log.info(" [LITX]进行rollbackHook调用");
                    	rollbackInvokeHook.hookProcess(defination.getInterfaceClass(), defination.getMethod().getName(), arguments, e);
                    }
                }
            }
        }
        return rollbackResult;
    }
}
