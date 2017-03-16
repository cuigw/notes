
[TOC]

# 2017-03-16

---------

## JSP自定义标签

### 背景
由于项目重构，去除了之前的shiro，导致jsp页面上的shiro标签没法用，之前的按钮都是通过shiro标签的方式鉴定当前用户是否有权限

### 解决方法
使用自定义JSP标签去鉴定权限

### 代码块
#### Java代码
Java自定义类 需要**继承**`TagSupport`类，主要重写`doStartTag()`方法，用于判断是否需要显示按钮
JSP引擎对标签进行实例化对象后，会先调用`setPageContext()`方法，将JSP页面的`pageContext`对象传入这个标签处理器类，只要拥有了`pageContext`对象，那么就可以获取其他八大隐式对象从而操作web中的需求了,也可以通过`(HttpServletRequest)pageContext.getRequest()`获取请求
``` java
  private String name = null; // 需要判断的资源路径url

  //用于加载资源路径
  protected void verifyAttributes() throws JspException {
    String permission = getName();

    if (permission == null || permission.length() == 0) {
      String msg = "资源路径不能为空";
      throw new JspException(msg);
    }
  }

  @Override
  public int doStartTag() throws JspException {

    verifyAttributes();

    return onDoStartTag();
  }

  /**
   * 返回是否显示标签内的内容 
   * EVAL_BODY_INCLUDE：显示； SKIP_BODY：不显示
   * 
   * @return
   * @throws JspException
   */
  public int onDoStartTag() throws JspException {

    String requestUrl = getName();

    boolean show = isPermitted(requestUrl);
    if (show) {
      //显示标签内的内容
      return TagSupport.EVAL_BODY_INCLUDE;
    } else {
      //不显示标签内的内容
      return TagSupport.SKIP_BODY;
    }
  }
  
 /**
   * 判断当前用户权限中是否包含该资源路径权限
   * 
   * @param requestUrl 需要判断的资源路径
   * @return
   */
  protected boolean isPermitted(String requestUrl) {
    //
    HttpServletRequest request = (HttpServletRequest)pageContext.getRequest();
    User user = (User) request.getSession().getAttribute(Constants.AUTH_USER);
    LocalPermissionDTO permissionDTO = new LocalPermissionDTO();
    permissionDTO.setUrl(requestUrl);
    if (user != null && LocalUserCache.hasPrivilege("6" + user.getId(), permissionDTO)) {
      return true;
    }
    return false;
  }
```
#### .tld文件文件
**这里需要注意**的是，如果要通过这个文件中配置的`uri`查找这个文件的话，这个文件需要放在`resources/META-INF`文件夹下面，不然会提示找不到这个文件。
引入方式为`<%@ taglib prefix="shiro" uri="http://www.caocao.com/tags" %>`
使用方式为`<shiro:hasPermission name="/user/addUser">新增</shiro:hasPermission>`
``` xml
<?xml version="1.0" encoding="UTF-8" ?>
<!-- caocao 运营支撑自定义JSP标签 主要用户权限判断-->
<!DOCTYPE taglib PUBLIC "-//Sun Microsystems, Inc.//DTD JSP Tag Library 1.2//EN"
  "http://java.sun.com/dtd/web-jsptaglibrary_1_2.dtd">

<taglib>

  <tlib-version>1.1.2</tlib-version>

  <jsp-version>1.2</jsp-version>

  <short-name>caocaoTag</short-name>

  <uri>http://www.caocao.com/tags</uri>

  <description>caocao JSP Tag Library.</description>

  <tag>
    <!--定义标签名。 --> 
    <name>hasPermission</name>
    <!-- 处理类，需要自定义 -->
    <tag-class>com.caocao.auth.tags.HasPermissionTag</tag-class>
    <body-content>JSP</body-content>
    <description>Displays body content only if the current user
      'has' (implies) the specified permission (i.e the user has the specified ability).
    </description>
    <!-- 自定义属性名称 -->
    <attribute>
      <name>name</name>
      <required>true</required>
      <rtexprvalue>true</rtexprvalue>
    </attribute>
  </tag>

</taglib>
```

