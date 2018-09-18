package com.thebeastshop.litx.content;

public interface RollbackInvokeHook {
	public void hookProcess(Class clazz,String method,Object[] args,Exception rollbackException);
}
