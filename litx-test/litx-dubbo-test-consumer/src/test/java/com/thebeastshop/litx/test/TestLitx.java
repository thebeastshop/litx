package com.thebeastshop.litx.test;

import java.io.IOException;

import com.thebeastshop.litx.test.service.TestService;
import com.thebeastshop.litx.test.util.ServiceUtils;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author gongjun[jun.gong@thebeastshop.com]
 * @since 2017-09-12 16:44
 */
public class TestLitx {

    private final static Logger log = LoggerFactory.getLogger(TestLitx.class);

    private TestService testService;

    @Before
    public void prepare() {
        testService = ServiceUtils.getService(TestService.class);
    }

    @Test
    public void test1() throws IOException {
    	testService.test1();
    }
}
