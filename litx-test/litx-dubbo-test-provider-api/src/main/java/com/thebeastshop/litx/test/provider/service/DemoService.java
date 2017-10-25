package com.thebeastshop.litx.test.provider.service;

import com.thebeastshop.litx.annotations.Compensable;

/**
 * @author gongjun[jun.gong@thebeastshop.com]
 * @since 2017-09-12 16:05
 */
public interface DemoService {
	
	@Compensable
    String test1();

	@Compensable
	String test2();
	
	void rollbackTest1(String str);
	
	void rollbackTest2(String str);
}
