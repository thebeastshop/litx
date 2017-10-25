/**
 * <p>Title: liteFlow</p>
 * <p>Description: 轻量级的组件式流程框架</p>
 * <p>Copyright: Copyright (c) 2017</p>
 * @author Bryan.Zhang
 * @email weenyc31@163.com
 * @Date 2017-10-25
 * @version 1.0
 */
package com.thebeastshop.litx.content;

public interface RollbackInvokeHook {
	public void hookProcess(Class clazz,String method,Object[] args,Exception rollbackException);
}
