
## litx
litx是一个基于补偿的轻量级分布式事务框架。（目前只支持dubbo，未来计划支持http等其他rpc调用的补偿）

* 对代码逻辑无侵入
* 和spring事务无缝结合（内部实现是集成spring的事务管理器，靠@transactional标注开启）
* 自动回滚补偿接口
* 如回滚异常提供hook接口可供扩展，当回滚失败时，可以拿到数据自行可作处理。

## Quick Start
也可以参考litx-test的测试用例，其工程演示了在dubbo环境下的测试情况。

也可参照以下代码进行快速配置

第一步
定义你相关接口的rollback接口。假设你的dubbo方法为submitOrder，回滚接口命名规则为rollbackSubmitOrder，请求参数定义为你submitOrder的返回类型。加上spring的事务标注@Transactional
并且再你的接口上加入@Compensable标注

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

如果你想处理回滚异常的数据
请定义自己的类，实现RollbackInvokeHook接口。并把自己的类注册到spring容器内就可以。litx启动的时候会自动扫描到。

## 测试用例说明
测试工程提供了一个dubbo-provider，首先得启动他，用Runner的main启动（数据源配置请重新配置），然后启动dubbo-consumer
consumer的test用例演示了一个用例：consumer先调用provider的test1，再调用test2。test2代码里主动抛错。test1回滚，test1回滚的时候主动抛错，然后被consumer自定义的hook捕获到。
