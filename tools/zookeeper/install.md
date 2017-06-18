#  安装
----------------------------------------

[toc]
## 下载

> wget http://mirror.bit.edu.cn/apache/zookeeper/zookeeper-3.4.10/zookeeper-3.4.10.tar.gz

## 解压
> tar zxvf zookeeper-3.4.10.tar.gz

## 配置Zookeeper
zookeeper支持两种运行模式：**独立模式（standalone）** 和 **复制模式（replicated）** <br>
由于zookeeper集群是通过多数选举的方式产生leader的，因此，集群需要 **奇数个** zookeeper实例组成，也就是至少需要3台

## 配置zoo.conf文件
复制成3个实例
> mv zookeeper-3.4.10 zookeeper-3.4.10-01
> cp -r  zookeeper-3.4.10-01 zookeeper-3.4.10-02
> cp -r  zookeeper-3.4.10-01 zookeeper-3.4.10-03

分别创建zoo.conf配置文件，放在对应实例下的conf/目录下，文件内容如下：

``` xml

# The number of milliseconds of each tick
tickTime=2000
# The number of ticks that the initial
# synchronization phase can take
initLimit=10
# The number of ticks that can pass between
# sending a request and getting an acknowledgement
syncLimit=5
# the directory where the snapshot is stored.
# do not use /tmp for storage, /tmp here is just
# example sakes.
dataDir=/usr/cgw/tools/zookeeper/data/zookeeper-03
# the port at which the clients will connect
clientPort=2183

# servers
server.1=127.0.0.1:2888:3888
server.2=127.0.0.1:2889:3889
server.3=127.0.0.1:2890:3890
# the maximum number of client connections.
# increase this if you need to handle more clients
#maxClientCnxns=60
#
# Be sure to read the maintenance section of the
# administrator guide before turning on autopurge.
#
# http://zookeeper.apache.org/doc/current/zookeeperAdmin.html#sc_maintenance
#
# The number of snapshots to retain in dataDir
#autopurge.snapRetainCount=3
# Purge task interval in hours
# Set to "0" to disable auto purge feature
#autopurge.purgeInterval=1

```
* `dataDir这个是指定实例数据的存放路径，不同的实例要区分开。`
* `clientPort这个是供客户端连接本实例的端口号，不同的实例也要区分。`
* `server.{X}这个X可以取数字，用来标识集群中唯一的一个实例。配置了多少个server.{X}就表示集群中有多少个实例。后面值的格式为：{host}:{port1}:{port2}
   其中，{host}为实例所在的主机IP，这里由于都在一台机器上，所以都指定为本机地址；{port1}是集群中实例之间用于数据通信的端口；{port2}是集群中实例进行leader选举时使用的通信端口。对于同一实例{port1}和{port2}是不可相同的。对于同一台机器部署多个实例的情况，不同实例的同一种端口也是需要区分的。`
  
## 配置myid文件
前面提到的server.{X}配置项中的{X}就是一个实例的myid，它需要被写在对应实例的{dataDir}/myid文件中。

下面需要在每一个实例指定的{dateDir}目录下创建一个名为myid的文件，文件的内容就是一个数字，对应server.{X}中的X。

比如，这里是这样配置的：
> 在/usr/cgw/tools/zookeeper/data/zookeeper-01/myid文件中写入1；
> 在/usr/cgw/tools/zookeeper/data/zookeeper-02/myid文件中写入2；
> 在/usr/cgw/tools/zookeeper/data/zookeeper-03/myid文件中写入3。

## 创建启动和停止的脚本
启动脚本
``` xml
    
    echo `/usr/cgw/tools/zookeeper/zookeeper-3.4.10-01/bin/zkServer.sh start`
    echo `/usr/cgw/tools/zookeeper/zookeeper-3.4.10-02/bin/zkServer.sh start`
    echo `/usr/cgw/tools/zookeeper/zookeeper-3.4.10-03/bin/zkServer.sh start`
    
```

停止脚本
``` xml
    
    echo `/usr/cgw/tools/zookeeper/zookeeper-3.4.10-01/bin/zkServer.sh stop`
    echo `/usr/cgw/tools/zookeeper/zookeeper-3.4.10-02/bin/zkServer.sh stop`
    echo `/usr/cgw/tools/zookeeper/zookeeper-3.4.10-03/bin/zkServer.sh stop`
    
```