#java的https请求绕过JDK证书校验+自生成证书
------------------------
[toc]

## 如何绕过JDK证书校验

### 背景
由于需要类似cas的单点登录功能需要java发送请求`（非浏览器）`**https**请求给服务端，而JDK会自动**https**请求的证书进行校验。如果是`自建证书或者非JDK认证的证书(公司的WoSign沃通证书也是没有被JDK认证)`，则需要往JDK中`导入证书`或者`绕过JDK证书校验`
`如果每台机器上的JDK都导入证书，显然比较麻烦不可行`
`这里介绍绕过JDK校验的方式`

### 代码块
``` java
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;


/**
 * TODO 无视Https证书是否正确的
 *
 * @author Cui.GaoWei
 * @date 2017/3/17
 */
public class HttpsIgnoreCertUtil {
    private static final Logger logger = LoggerFactory.getLogger(HttpsIgnoreCertUtil.class);

    /**
     * 忽视证书HostName
     */
    public static HostnameVerifier ignoreHostnameVerifier = new HostnameVerifier() {
        @Override public boolean verify(String s, SSLSession sslSession) {
            logger.info("Warning:Hostname is not matched for cert.");
            return true;
        }
    };

    /**
     * 忽视证书
     */
    public static TrustManager ignoreCertificationTrustManger = new X509TrustManager() {

        private X509Certificate[] certificates;

        @Override public void checkClientTrusted(X509Certificate certificates[], String authType)
            throws CertificateException {
            if (this.certificates == null) {
                this.certificates = certificates;
                logger.info("init at checkClientTrusted");
            }
        }

        @Override public void checkServerTrusted(X509Certificate[] ax509certificate, String s)
            throws CertificateException {
            if (this.certificates == null) {
                this.certificates = ax509certificate;
                logger.info("init at checkServerTrusted");
            }

        }

        @Override public X509Certificate[] getAcceptedIssuers() {
            // TODO Auto-generated method stub
            return null;
        }
    };
}
```

###  使用

``` java
 final SSLContext sslContext = SSLContext.getInstance(this.sslConfiguration.getProperty("protocol", "SSL"));
            TrustManager[] tm = { HttpsIgnoreCertUtil.ignoreCertificationTrustManger };
            if(isIgnoreCert) {
                sslContext.init(null, tm,  new java.security.SecureRandom());
                return sslContext.getSocketFactory();
            }
```

### 资料
[java请求https地址如何绕过证书验证](http://www.cnblogs.com/javaee6/p/3714769.html)


## 自生成证书
**1.** [生成证书并配置tomcat](ttp://jingyan.baidu.com/article/a948d6515d3e850a2dcd2ee6.htm)
**2.tomcat中server.xml中http配置成443端口。这样就不需要配置端口号了**
``` xml
    <Connector port="443" protocol="org.apache.coyote.http11.Http11Protocol"
               maxThreads="150" SSLEnabled="true" scheme="https" secure="true"
               clientAuth="false" sslProtocol="TLS"
               keystoreFile="D:\\keys\\tomcat.keystore" keystorePass="caouucao123"
							 truststoreFile="D:\\keys\\tomcat.keystore" truststorePass="caouucao123"/>
```
**3. JVM中导入受信任证书（正规）**
	keytool -import -keystore D:\jdk1.8.0_121\jre\lib\security\cacerts -file D:/keys/tomcat.cer -alias tomcat
        不导入的话会报 unable to find valid certification path to requested target
	通常这种情况下是因为服务器的部分或者全部证书 不是 由 证书颁发机构颁发的
	而是自生成或者有其他私人机构颁发的，所以我们本地的truststore对服务器证书进行校验的时候会报错。
**4. 删除证书**
keytool -delete -alias tomcat -keystore cacerts 
