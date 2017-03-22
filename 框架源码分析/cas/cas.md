# CAS源码分析
--------------------
[toc]

## 客户端
### SingleSignOutHttpSessionListener
> 用于监听会话销毁，如果会话销毁，则在`sessionMappingStorage(存储会话的容器)`

### SingleSignOutFilter
> 单点的登出请求拦截
> 1. 如果是token请求 ，记录会话 `（这里有点问题）`
> 2. 如果是登出请求，销毁会话，`sessionMappingStorage中移除该会话，会话是根据token保存的`

### AuthenticationFilter
> 身份认证的过滤器
>  * 入参
>    1. casServerLoginUrl   统一登录校验的地址
>    2. serverName 当前web应用所在的web服务器域名url
>  * 流程
>    1. 从当前会话中获取`Assertion`如果有的话直接放过
>    2. 如果没有`Assertion`，检查是否有`ticket`如果有的话放过
>    3. 如果`Assertion`和`ticket`都没有，那就重定向到统一登录地址

### AbstractTicketValidationFilter
> 用于ticket校验
>  1. 生成一个Validator**`（这里可以自定义Validator）`**
>  2. 用这个Validator去校验ticket，通过Http的方式，检验之后会组装成一个`Assertion`，如果校验不通过直接抛出异常


## 服务端
### AuthenticationHandler
> 继承这个类就可以实现自定义登录校验。

[数据库查询用户名密码自定义校验](http://blog.csdn.net/hejingyuan6/article/details/45111703)
