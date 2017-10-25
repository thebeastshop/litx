
##litx
litx是一个基于补偿的轻量级分布式事务框架。（目前只支持dubbo，未来计划支持http等其他rpc调用的补偿）

* 对代码逻辑无侵入
* 和spring事务隔离级别无缝结合
* 自动回滚补偿接口

##Quick Start
也可以参考litx-test的测试用例，其工程演示了在dubbo环境下的测试情况。

也可参照以下代码进行快速配置

第一步
定义你相关接口的rollback接口。假设你的dubbo方法为submitOrder，回滚接口命名规则为rollbackSubmitOrder，请求参数定义为你submitOrder的返回类型。
在你的接口上加入@Compensable标注
```java
public interface DemoService {
	
	@Compensable
    	String test1();

	@Compensable
	String test2();
	
	void rollbackTest1(String str);
	
	void rollbackTest2(String str);
}
```


第二步
spring配置中加入以下定义
```xml
<bean id="litxDubboDefinationScanner" class="com.thebeastshop.litx.spring.LitxDubboDefinationScanner"/>
```

第三步
替换默认的spring事务管理器
```xml
<bean name="transactionManager" class="com.thebeastshop.litx.transaction.LitTransactionManager">
	<property name="dataSource" ref="dataSource"/>
</bean>
```
