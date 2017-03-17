# shiro笔记
--------------------
[toc]

## shiro介绍

### 几个基本概念
> * Subject ： 主体，代表当前的用户
> * SecurityManager ： 完全管理器，负责交互。主要注入Realm
> * Realm ： 域。安全数据源。一般从这里获取数据库的正确数据给shiro做校验。
> * AuthenticationInfo ： 身份认证器
> * AuthorizationInfo ： 权限认证器
> * SessionDAO ： 会话存放的地方
> * CacheManager ： 缓存存放的地方
> * Cryptography ： 密码模块

### 身份认证
#### 验证流程
1. 用户输入用户名密码
2. 调用 subject.login（UsernamePasswordToken token）
3. realm中**`doGetAuthenticationInfo`**根据token中的用户名从数据源中查到正确的数据信息
4. 生成一个包含正确信息AuthenticationInfo （user,password,getName()）**(这一步就有了表单和数据源中的用户信息，就可以去比较了)**
5. AuthenticationInfo调用assertCredentialsMatch去生成一个CredentialsMatcher，在CredentialsMatcher做表单和数据源的校验从而得出结果。**（如果不想用shiro自带的校验可以重写realm里面的`assertCredentialsMatch`，进行自定义校验）**
#### 一篇好文
[Shiro框架的登录认证流程](https://www.zifangsky.cn/893.html)

### 权限认证
类似于身份认证
在reanlm中的**`doGetAuthorizationInfo`**方法中返回**`AuthorizationInfo`**，这个类中包含了用户的全部权限信息
