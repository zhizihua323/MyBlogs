# XML Insert

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
mapper节点下添加了 Insert 以下两个属性：
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
## 一些属性
- id：DAO类中对应方法名
- parameterType：接受参数类型
- resultType：返回类型
<br/>

详细内容链接[XML映射器](https://mybatis.net.cn/sqlmap-xml.html)