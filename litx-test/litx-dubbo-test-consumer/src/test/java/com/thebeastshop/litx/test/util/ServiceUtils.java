package com.thebeastshop.litx.test.util;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;

public class ServiceUtils {
	public static <S>  S getService(Class<S> c){
		ApplicationConfig application = new ApplicationConfig();
		application.setName("beast-litx-demo-runner");
		 
		RegistryConfig registry = new RegistryConfig();
		registry.setAddress("zookeeper://114.55.174.189:2181");
		registry.setGroup("dubbo-litx");

		ReferenceConfig<S> reference = new ReferenceConfig<S>(); 
		reference.setApplication(application);
		reference.setRegistry(registry); 
		reference.setInterface(c);
		reference.setTimeout(30000);
		reference.setRetries(0);
		
		S service = reference.get();
		return service;
	}
}
