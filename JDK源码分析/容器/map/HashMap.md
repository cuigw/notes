#JDK 源码分析 — Map
----------------------------------------

[toc]
## HashMap

###  主要属性
>  * threshold    扩容的临界值（默认12）
>  * loadFactor   加载因子（默认0.75）
>  * TREEIFY_THRESHOLD  链表大于这个值变成红黑树
>  * Node<K,V> 实现 Map.Entry<K,V> 哈希桶数组

### 相关方法
>  * putVal()    放入键值对
>  * resize()     重新hash
>  * treeifyBin()  生成红黑树

### 注意点
> * 扩容后是之前的**2**倍
> * 哈希桶数组table的长度length大小必须为2的n次方(一定是合数)，为了在取模和扩容时做优化
>  * 当链表长度超过8的时候变成红黑树

### put代码
``` java
final V putVal(int hash, K key, V value, boolean onlyIfAbsent,boolean evict) {
        Node<K,V>[] tab;
        Node<K,V> p; 
        int n, i;
        //1.判断tab是否为空，是否还有剩余空间，是否需要扩容操作
        if ((tab = table) == null || (n = tab.length) == 0)
            n = (tab = resize()).length;
        //给p赋值，为一个节点的位置，若为空，创建一个新节点,调用的LinkedHashMap的newNode方法
        if ((p = tab[i = (n - 1) & hash]) == null)
            tab[i] = newNode(hash, key, value, null);
        else {
            Node<K,V> e; 
            K k;
            //1.比较哈希值是否相同；2.比较key实例是否相同；3.比较key的值
            if (p.hash == hash &&((k = p.key) == key || (key != null && key.equals(k))))
            //若值已经存在，直接覆盖
                e = p;
            //若p是一个TreeNode对象
            else if (p instanceof TreeNode)
            //在红黑树中直接插入键值对
                e = ((TreeNode<K,V>)p).putTreeVal(this, tab, hash, key, value);
            else {
                //开始遍历链表，准备插入
                for (int binCount = 0; ; ++binCount) {
                    if ((e = p.next) == null) {
                        //创建要进行插入的节点
                        p.next = newNode(hash, key, value, null);
                        //如果链表的长度大于8了，进行链表->红黑树的转换操作
                        if (binCount >= TREEIFY_THRESHOLD - 1) // -1 for 1st
                            treeifyBin(tab, hash);
                        break;
                    }
                    //如果存在KEY值,直接赋值覆盖value
                    if (e.hash == hash && ((k = e.key) == key || (key != null && key.equals(k))))
                        break;
                    p = e;
                }
            }
            //上面过程为给e这个Node<K,V>对象赋值
            if (e != null) { // existing mapping for key
                V oldValue = e.value;
                if (!onlyIfAbsent || oldValue == null)
                    e.value = value;
                afterNodeAccess(e);
                return oldValue;
            }
        }
        ++modCount;
        //判断是否超过最大容量，需要扩容
        if (++size > threshold)
            resize();
        afterNodeInsertion(evict);
        return null;
    }
```

### 一篇好文
[Java8系列之重新认识HashMap](http://www.importnew.com/20386.html)
