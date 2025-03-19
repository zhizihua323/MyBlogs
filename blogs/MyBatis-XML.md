# 复盘Mybatis XML
## 配置xml文件
注意：配置mybatis xml文件时，需要放在resources文件夹中，<br/>
并且要将dao文件路径保持一致<br/>
![mybatis01.png](/blogs/image/mybatis01.png)
<br/>
同时还要在.properties文件中完成mybatis.mapper-locations配置
![mybatis02.png](/blogs/image/mybatis02.png) <br/>

**对应代码**  [mybatis01](/codes/mybatisXML01/)

---

## XML Mapper

1. xml文件的头信息：
```java
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
```

2. 有了头信息，我们还要准备mapper
```java
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.youkeda.comment.dao.UserDAO">


</mapper>
```

3. resultMap用来处理表和DO对象的属性映射关系，确保每个字段都有属性可以匹配
```java
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.youkeda.comment.dao.UserDAO">

  <resultMap id="userResultMap" type="com.youkeda.comment.dataobject.UserDO">
    <id column="id" property="id"/>
    <result column="user_name" property="userName"/>
  </resultMap>

</mapper>
```
`id` & `type`
```java
<resultMap id="userResultMap" type="com.youkeda.comment.dataobject。UserDO">
```
- id 对应DO对象，命名规则`xxxResultMap` 
- type 对应DO类路径

`id` & `result`
```java
<id column="id" property="id"/>
<result column="user_name" property="userName"/>
```
- id设置数据库主键 column对应表，property对应DO属性名称
- result 设置数据库其他字段名称

有了resultMap 就不用使用别名 user_name as userName 了<br/>
**对应代码** [mybatisXML02](/codes/mybatisXML02/)

---

## XML Insert

示例代码：
```java
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.youkeda.comment.dao.UserDAO">
 <resultMap id="userResultMap" type="com.youkeda.comment.dataobject.UserDO">
    <id column="id" property="id"/>
    <result column="user_name" property="userName"/>
    <result column="pwd" property="pwd"/>
    <result column="nick_name" property="nickName"/>
    <result column="avatar" property="avatar"/>
    <result column="gmt_created" property="gmtCreated"/>
    <result column="gmt_modified" property="gmtModified"/>
  </resultMap>

  <insert id="add" parameterType="com.youkeda.comment.dataobject.UserDO" >
    INSERT INTO user (user_name, pwd, nick_name,avatar,gmt_created,gmt_modified)
    VALUES(#{userName}, #{pwd}, #{nickName}, #{avatar},now(),now())
  </insert>

</mapper>
```
mapper节点下添加了 Insert 以下两个属性：<br/>

`id` && `parameterType`
- id：DAO类中对应的方法名
- parameterType：传递参数类型
<br/>

**常用于insert、select、insert、update等标签中**

---

用于插入的是一组数据（对象）采用`@RequestBody`
```java
@PostMapping("/user")
@ResponseBody
public UserDO save(@RequestBody UserDO userDO) {
    userDAO.add(userDO);
    return userDO;
}
```

> 如果想要获得插入的主键id值，需要配置`useGeneratedKeys`、`keyProperty`

```java
<insert id="add" parameterType="com.youkeda.comment.dataobject.UserDO" useGeneratedKeys="true" keyProperty="id">
    INSERT INTO user (user_name, pwd, nick_name,avatar,gmt_created,gmt_modified)
    VALUES(#{userName}, #{pwd}, #{nickName}, #{avatar},now(),now())
</insert>
```

本部分对应代码[mybatisXML03](/codes/mybatisXML03/)

---

## XML update

```java
<update id="update" parameterType="com.youkeda.comment.dataobject.UserDO">
    update user set nick_name=#{nickName},gmt_modified=now() where id=#{id}
</update>
```
[mybatisXML04](/codes/mybatisXML04/)

---

## XML delete
```java
<delete id="delete">
    delete from user where id=#{id}
</delete>
```
**注意**：这里的`delete`没有配置 `parameterType` 属性，这因为：<br/>
```java
int delete(@Param("id") long id);
```
delete方法参数是由`@Param`注解组成，<br/>Mybatis会把这类数据当成`Map`数据来传递，<br/>`parameterType`默认就是`Map`，可以不写<br/>

[mybatisXML05](/codes/mybatisXML05/)

---

## XML Select
```java
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.youkeda.comment.dao.UserDAO">
    <resultMap id="userResultMap" type="com.youkeda.comment.dataobject.UserDO">
        <id column="id" property="id"/>
        <result column="user_name" property="userName"/>
        <result column="pwd" property="pwd"/>
        <result column="nick_name" property="nickName"/>
        <result column="avatar" property="avatar"/>
        <result column="gmt_created" property="gmtCreated"/>
        <result column="gmt_modified" property="gmtModified"/>
    </resultMap>

    <select id="findByUserName" resultMap="userResultMap">
        select * from user where user_name=#{userName} limit 1
    </select>

</mapper>
```
**注意**：我们这里使用了一个属性`resultMap`，这个值我们一般配置为XML文件下的`resultMap`节点的`id`值<br/>
这样的好处是，我们不用做额外的数据映射了<br/>

这部分的代码：
[mybatis06](/codes/mybatisXML06/)
[mybatis07](/codes/mybatisXML07/)

---

这里要注意SQL拼接问题：<br/>

![mybatis03.jpg](/blogs/image/mybatis03.jpg)

## 简单总结XML CRUD
基于前几次开发，总结一下基于XML模式的开发顺序：<br/>
1. 创建 DO 对象
2. 创建 DAO 接口，配置`@Mapper`注解
3. 创建 XML 文件，并完成`resultMap`配置
4. 创建 DAO 接口方法
5. 创建对应的 XML 语句

完整代码：[mybatis07](/codes/mybatisXML07/)

## 一些属性
- id：DAO类中对应方法名
- parameterType：接受参数类型
- resultType：返回类型
<br/>

详细内容链接[XML映射器](https://mybatis.net.cn/sqlmap-xml.html)