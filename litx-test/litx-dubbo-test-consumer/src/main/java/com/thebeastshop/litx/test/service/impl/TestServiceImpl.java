package com.thebeastshop.litx.test.service.impl;

import javax.annotation.Resource;

import com.thebeastshop.litx.test.provider.service.DemoService;
import com.thebeastshop.litx.test.service.TestService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author gongjun[jun.gong@thebeastshop.com]
 * @since 2017-09-12 16:24
 */
@Service("testService")
public class TestServiceImpl implements TestService {

    private final static Logger log = LoggerFactory.getLogger(TestServiceImpl.class);

    @Resource
    private DemoService demoService;

    @Override
    @Transactional
    public void test1() {
    	demoService.test1();
    	demoService.test2();
    }

    @Override
    @Transactional
    public void test2() {
    	
    }

}
