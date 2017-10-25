package com.thebeastshop.litx.test.provider.tools;

import org.apache.commons.lang3.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Runner {
	private static final Logger log = LoggerFactory.getLogger(Runner.class);
	
	private static ClassPathXmlApplicationContext context;
	
    public static void main(String[] args) {
    	StopWatch sw = new StopWatch();
    	sw.start();
        context = new ClassPathXmlApplicationContext(new String[] { "/applicationContext.xml" });
        context.start();
        sw.stop();
        log.info("litx-demo-provider has started in: {}", sw.getTime());
        while (true) {
            try {
                Thread.sleep(60000);
            } catch (Throwable e) {
            	e.printStackTrace();
            }
        }
    }
}
