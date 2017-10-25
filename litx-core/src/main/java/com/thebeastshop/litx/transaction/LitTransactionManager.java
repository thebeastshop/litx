package com.thebeastshop.litx.transaction;

import com.thebeastshop.litx.constant.LitxConstant;
import com.thebeastshop.litx.context.TransactionContext;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.DefaultTransactionStatus;
import org.springframework.transaction.support.TransactionSynchronizationManager;

/**
 * @author gongjun[jun.gong@thebeastshop.com]
 * @since 2017-09-11 14:37
 */
public class LitTransactionManager extends DataSourceTransactionManager{

    public static TransactionContext getTransactionContext() {
    	return (TransactionContext)TransactionSynchronizationManager.getResource(LitxConstant.TRANSACTION_CONTEXT_KEY);
    }
    
    public static long getTransactionId() {
    	Object o = TransactionSynchronizationManager.getResource(LitxConstant.TRANSACTION_ID_KEY);
    	return (Long)TransactionSynchronizationManager.getResource(LitxConstant.TRANSACTION_ID_KEY);
    }

    @Override
    protected void doBegin(Object transaction, TransactionDefinition definition) {
    	Long txId = System.nanoTime();
        logger.info(" [LITX]开始事务 [TXID: " + txId + "]");
        super.doBegin(transaction, definition);
    	TransactionContext transactionContext = new TransactionContext();
        TransactionSynchronizationManager.bindResource(LitxConstant.TRANSACTION_CONTEXT_KEY, transactionContext);
        TransactionSynchronizationManager.bindResource(LitxConstant.TRANSACTION_ID_KEY, txId);
    }


    @Override
    protected void doRollback(DefaultTransactionStatus status) {
        TransactionContext transactionContext = getTransactionContext();
        long txId = getTransactionId();
        if (transactionContext == null) {
            logger.error(" [LITX] litx transaction has not been started. [TX ID: " + txId + "]");
            throw new RuntimeException("LITX: litx transaction has not been started. [TX ID: " + txId + "]");
        }
        if (transactionContext.isNeedRollback()) {
	        logger.info(" [LITX]开始回滚事务 [TX ID: " + txId + "]");
	        try {
	        	transactionContext.doRollback();
	        }catch(Exception e){
	        	logger.error(" [LITX]回滚异常", e);
	        }finally {
	        	logger.info(" [LITX]回滚事务完成 [TX ID: " + txId + "]");
	        }
        }
        super.doRollback(status);
    }
    
    @Override
    protected void doCleanupAfterCompletion(Object transaction) {
    	long txId = getTransactionId();
        logger.info(" [LITX]结束事务 [TX ID: " + txId + "]");
        super.doCleanupAfterCompletion(transaction);
        TransactionSynchronizationManager.unbindResource(LitxConstant.TRANSACTION_CONTEXT_KEY);
        TransactionSynchronizationManager.unbindResource(LitxConstant.TRANSACTION_ID_KEY);
    }
}
