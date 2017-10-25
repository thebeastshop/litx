package com.thebeastshop.litx.test.provider.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thebeastshop.litx.test.provider.service.DemoService;

import java.util.Date;

import javax.annotation.Resource;

/**
 * @author gongjun[jun.gong@thebeastshop.com]
 * @since 2017-09-12 16:24
 */
@Service("demoService")
public class DemoServiceImpl implements DemoService {

    private final static Logger log = LoggerFactory.getLogger(DemoServiceImpl.class);

    @Resource
    private JdbcTemplate jdbcTemplate;
    
	@Override
	@Transactional
	public String test1() {
		log.info("method test1 invoked");
		jdbcTemplate.update("insert into tmp_a(name,create_time) values(?,?)", "nelson",new Date());
		return "test1 result";
	}

	@Override
	@Transactional
	public String test2() {
		log.info("method test2 invoked");
		jdbcTemplate.update("insert into tmp_a(name,create_time) values(?,?)", "steven",new Date());
		throw new RuntimeException("test1 throws exception");
//		return "test2 result";
	}
	
	@Override
	public void rollbackTest1(String str) {
		log.info("***************do rollbackTest1****************");
		throw new RuntimeException("rollback cause exception");
		
	}
	
	@Override
	public void rollbackTest2(String str) {
		log.info("***************do rollbackTest2****************");
	}
}
