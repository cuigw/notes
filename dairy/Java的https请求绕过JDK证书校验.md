# java的https请求绕过JDK证书校验
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
