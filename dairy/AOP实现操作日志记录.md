# 操作日志记录功能实现
--------
[toc]
## 基于Spring Aop的日志记录程序

### 背景
日志记录模块重构，之前的操作记录很多是直接`写在代码里面`，有些是写在`filter`里面的，觉得设计的也很有问题。
故想要通过Aop的方式实现新的操作日志记录体系。

### 几个注意点
1.  获得当前正在执行的方法。环绕通知
    MethodSignature signature = (MethodSignature) joinPoint.getSignature();
    Method method = signature.getMethod();
    这里之后可以用注解判断是否是RequestMapping注解的方法，`以避免很多工具方法被记录`
2. **环绕通知必须返回`joinPoint.proceed();`** 不然请求无法正常获取返回结果

### 直接上代码了（雏形）

``` xml
    <!-- 操作日志记录 -->
    <aop:config>
        <aop:aspect id="operateLogAspect" ref="operateLogAspect">
            <aop:pointcut id="logPointcut" expression="execution(* com.caouucao.authcenter.web.controller.*.*(..)))"/>
            <aop:before pointcut-ref="logPointcut" method="before"/>
            <aop:after  pointcut-ref="logPointcut" method="after"/>
            <aop:around pointcut-ref="logPointcut" method="around"/>
        </aop:aspect>
    </aop:config>
```


``` java
package com.caouucao.authcenter.web.aop;


import com.caouucao.authcenter.util.utils.RequestUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * TODO
 *
 * @author Cui.GaoWei
 * @date 2017/3/17
 */
@Component
public class OperateLogAspect {

  Logger logger = LoggerFactory.getLogger(OperateLogAspect.class);

  private long startTimeMillis = 0; // 开始时间

  /**
   * 前置通知，用于记录开始时间
   * 
   * @param joinPoint
   */
  public void before(JoinPoint joinPoint) {
    startTimeMillis = System.currentTimeMillis(); // 记录方法开始执行的时间
    System.out.println("startTimeMillis" + startTimeMillis);
  }

  /**
   * 后置通知，用于记录操作时间
   * 
   * @param joinPoint
   */
  public void after(JoinPoint joinPoint) {
    long requestTime = System.currentTimeMillis() - startTimeMillis;
    System.out.println("requestTime" + requestTime);
  }

  /**
   * 环绕通知，用于具体操作及结果的记录
   * 
   * @param joinPoint
   * @return
   * @throws Throwable
   */
  public Object around(ProceedingJoinPoint joinPoint) throws Throwable {

    // 获得当前正在执行的方法。若果不是请求过来的则直接放过
    MethodSignature signature = (MethodSignature) joinPoint.getSignature();
    Method method = signature.getMethod();
    if (!method.isAnnotationPresent(RequestMapping.class)) {
      return joinPoint.proceed();
    }

    // 获取request
    HttpServletRequest request = RequestUtil.getRequest();
    Map<String, String> requestParams = RequestUtil.request2Map(request);
    String requestUri = request.getRequestURI();
    System.out.println(String.format("HttpRequest[%s:%s:%s]:%s:%s", request.getRemoteHost(),
        request.getRemotePort(), request.getRequestedSessionId(), requestUri, requestParams));

    // 执行完方法的返回值：调用proceed()方法，就会触发切入点方法执行
    Map<String, Object> responseParams = new HashMap<String, Object>();
    // result的值就是被拦截方法的返回值 最后必须返回这个result
    Object result = joinPoint.proceed();
    responseParams.put("result", result);
    System.out.println("responseParams : " + responseParams);
    return result;
  }
}
```
