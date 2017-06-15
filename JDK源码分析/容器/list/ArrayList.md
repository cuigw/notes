#JDK  源码分析 -- ArrayList
---------------------------------

## 注意点
> * 允许空
> * 允许重复数据
> * 有序
> * 非线程安全


###  删除元素

    1. 检查是否有IndexOutOfBoundsException
    2. 进行一次`复制`操作，其实就是将`该元素的后面的元素往前移一格`
    
    ``` java
            public E remove(int index) {
                rangeCheck(index);
        
                modCount++;
                E oldValue = elementData(index);
        
                int numMoved = size - index - 1;
                if (numMoved > 0)
                    System.arraycopy(elementData, index+1, elementData, index,
                                     numMoved);
                elementData[--size] = null; // clear to let GC do its work
        
                return oldValue;
            }
    ```
    
    

## 添加元素
1. 判断需不需要扩容，如果需要扩容则扩容，`同时会进行一次复制操作，会消耗性能`
2. 在末尾添加元素
   
```   java
    private void grow(int minCapacity) {
              // overflow-conscious code
              int oldCapacity = elementData.length;
              int newCapacity = oldCapacity + (oldCapacity >> 1);
              if (newCapacity - minCapacity < 0)
                  newCapacity = minCapacity;
              if (newCapacity - MAX_ARRAY_SIZE > 0)
                  newCapacity = hugeCapacity(minCapacity);
              // 复制操作会消耗性能
              elementData = Arrays.copyOf(elementData, newCapacity);
          }

```

##  删除元素
1. 检查是否有IndexOutOfBoundsException
2. 进行一次`复制`操作，其实就是将`该元素的后面的元素往前移一格`
    
``` java
            public E remove(int index) {
                rangeCheck(index);
        
                modCount++;
                E oldValue = elementData(index);
        
                int numMoved = size - index - 1;
                if (numMoved > 0)
                    System.arraycopy(elementData, index+1, elementData, index,
                                     numMoved);
                elementData[--size] = null; // clear to let GC do its work
        
                return oldValue;
            }
 ```
 
 ## 为什么ArrayList中的elementData是用`transient`
 为了防止过度序列化， 不需要序列化elementData未使用的indexß