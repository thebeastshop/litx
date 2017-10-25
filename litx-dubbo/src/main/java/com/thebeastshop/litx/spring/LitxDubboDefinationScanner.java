package com.thebeastshop.litx.spring;

import com.alibaba.dubbo.config.spring.ReferenceBean;
import com.thebeastshop.litx.annotations.Compensable;
import com.thebeastshop.litx.content.RollbackInvokeHook;
import com.thebeastshop.litx.definition.CompensableMethodDefinition;
import com.thebeastshop.litx.definition.DefinitionManager;
import com.thebeastshop.litx.definition.LitTransactionType;
import com.thebeastshop.litx.definition.MethodDefinition;
import com.thebeastshop.litx.exceptions.NoSuchRollbackMethodException;
import com.thebeastshop.litx.util.MethodUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.Ordered;
import org.springframework.core.PriorityOrdered;

import java.lang.reflect.Method;

/**
 * @author gongjun[jun.gong@thebeastshop.com]
 * @since 2017-09-12 15:27
 */
public class LitxDubboDefinationScanner implements BeanPostProcessor,
        PriorityOrdered, ApplicationContextAware {
    private static final Logger log = LoggerFactory.getLogger(LitxDubboDefinationScanner.class);

    private static ApplicationContext applicationContext = null;

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public void setApplicationContext(ApplicationContext applicationContext) {
        LitxDubboDefinationScanner.applicationContext = applicationContext;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Class clazz = bean.getClass();
        if (ReferenceBean.class.isAssignableFrom(clazz)) {
            ReferenceBean referenceBean = (ReferenceBean) bean;
            referenceBean.setFilter("litxFilter");
            Class targetClass = referenceBean.getObjectType();
            registerDefinationList(targetClass);
        }
        if(RollbackInvokeHook.class.isAssignableFrom(clazz)){
        	try {
        		log.info(" [LITX] 初始化rollbackInvokeHook:"+clazz.getName());
				DefinitionManager.setRollbackInvokeHook((RollbackInvokeHook)clazz.newInstance());
			} catch (InstantiationException | IllegalAccessException e) {
				log.error(" [LITX] 初始化回滚HOOK实现时出错",e);
			}
        }
        return bean;
    }

    private String getRollbackMethodName(Method method) {
        String name = method.getName();
        return DefinitionManager.ROLLBACK_METHOD_PREFIX +
                Character.toUpperCase(name.charAt(0)) +
                name.substring(1);
    }


    private Method getRollbackMethod(Class interfaceClass, Method method) {
        String rollbackMethodName = getRollbackMethodName(method);
        Class returnType = method.getReturnType();
        try {
            return interfaceClass.getMethod(rollbackMethodName, new Class[] {returnType});
        } catch (NoSuchMethodException e) {
            throw new NoSuchRollbackMethodException(
                    method.getName(), rollbackMethodName, returnType);
        }
    }

    private void registerDefinationList(Class targetClass) {
        Method[] methods = targetClass.getDeclaredMethods();
        for (Method method : methods) {
            Compensable compensable = method.getAnnotation(Compensable.class);
            if (compensable != null) {
                Method rollbackMethod = getRollbackMethod(targetClass, method);
                MethodDefinition rollbackDefination = new MethodDefinition(
                        LitTransactionType.ROLLBACK,
                        targetClass,
                        rollbackMethod);
                CompensableMethodDefinition defination = new CompensableMethodDefinition(
                        targetClass,
                        method,
                        rollbackDefination);
                DefinitionManager.registerMethodDefination(targetClass, method, defination);
                log.info(" [LITX] 注册DUBBO的可补偿事务接口 " +
                        MethodUtil.getMethodNameWithParameterTypes(targetClass, method) +
                        ", 和它的补偿方法 " +
                        MethodUtil.getMethodNameWithParameterTypes(targetClass, rollbackMethod));
            }
        }
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }
}
