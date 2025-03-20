# MyBatis 分页插件
使用`pagehelper`插件进行分页查询。<br>
## 相关依赖
相关依赖：[pagehelper最新依赖](https://mvnrepository.com/artifact/com.github.pagehelper/pagehelper-spring-boot-starter)
``` xml
<dependency>
    <groupId>com.github.pagehelper</groupId>
    <artifactId>pagehelper-spring-boot-starter</artifactId>
    <version>1.2.13</version>
</dependency>
```
## 插件使用
eg：使用`pagehelper`插件优化`UserController.getAll`方法
```java
@GetMapping("/users")
@ResponseBody
public List<UserDO> getAll() {
    // 设置当前页数为1，以及每页3条记录
    Page<UserDO> page = PageHelper.startPage(1, 3).doSelectPage(() -> userDAO.findAll());
    return page.getResult();
}
```
- 在`doSelectPage`lambda方法中执行MyBatis查询方法
- PageHelper.startPage(1, 3)
    - startPage(页数，每页记录数)
- 返回类型`Page`对象是MyBatis封装的分页模型
    - getResult() 获取分数数据
    - getPages() 获取总页数
    - getTotal() 获取总记录数
    - getPageNum() 获取当页面数

## 通用分页模型`Paging` 简化开发
> 在开发中，通常会额外封装一个通用的分页模型`Paging`用于处理返回值：<br>
```java
import java.io.Serializable;
import java.util.List;

/**
 * 分页模型
 */
public class Paging<R> implements Serializable {

    private static final long serialVersionUID = 522660448543880825L;
    /**
     * 当前页面数
     */
    private int pageNum;

    /**
     * 每页记录数
     */
    private int pageSize = 15;
    /**
     * 总页面数
     */
    private int totalPage;

    /**
     * 总记录数
     */
    private long totalCount;

    /**
     * 当前页面的集合数据
     */
    private List<R> data;

    public Paging() {}

    public Paging(int pageNum, int pageSize, int totalPage, long totalCount, List<R> data) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.totalPage = totalPage;
        this.totalCount = totalCount;
        this.data = data;
    }

    // 省略 getter、setter
}
```
序列化 & 反序列化 [Serializable原理](https://juejin.cn/post/6844904049997774856)
<br>

## 使用Paging
使用Paging优化后：<br>
```java
@GetMapping("/users")
@ResponseBody
public Paging<UserDO> getAll() {
    // 设置当前页数为1，以及每页3条记录
    Page<UserDO> page = PageHelper.startPage(1, 3).doSelectPage(() -> userDAO.findAll());

    return new Paging<>(page.getPageNum(), page.getPageSize(), page.getPages(), page.getTotal(), page
            .getResult());
}
```
**注意**：返回值要修改！！！<br>

相关代码：<br>
[mybatisPageHelper01](/codes/mybatisPageHelper01/)<br>
[mybatisPageHelper02](/codes/mybatisPageHelper02/)

**注意** 在`mybatisPageHelper02`的`getAll()`方法中采用，**可选参数** `pageNum`、`pageSize` <br>
![mybatis04.jpg](/blogs/image/mybatis04.jpg)

## PageHelper 相关参考
[pagehelper分页原理](https://blog.csdn.net/MarcoAsensio/article/details/104942041)<br>
[官方文档](https://github.com/mybatis-book/book/blob/master/README.md)

