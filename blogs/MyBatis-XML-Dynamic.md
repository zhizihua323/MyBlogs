# MyBatis 动态SQL
此部分是对XML的增强部分，用于解决SQL语句复杂情况；解释为什么选用XML
## 条件语句
### if 语句
```java
<update id="update" parameterType="com.youkeda.comment.dataobject.UserDO">
  update user set
   <if test="nickName != null">
    nick_name=#{nickName},gmt_modified=now()
   </if>
   where id=#{id}
</update>
```
避免了nick_name为null的情况造成的错误<br/>
通过这个`if`语句，可以提前对数据进行判断，确保数据不会因为错误而丢失
### set 语句
```java
<update id="update" parameterType="com.youkeda.comment.dataobject.UserDO">
  update user
  <set>
    <if test="nickName != null">
      nick_name=#{nickName},
    </if>
    <if test="avatar != null">
      avatar=#{avatar},
    </if>
    gmt_modified=now()
  </set>
   where id=#{id}
</update>
```
在上面的`if`中解决了，字段为空的错误情况<br/>
那么如果所有列值都为`null`,可能会余留`,`导致SQL错误。<br/>
此时使用`set`，系统就可以自动去除最后一个逗号，而不用担心到底哪一个列才是最后一个。
### if+select
除了上面这种案例，`if`也可以用于`select`查询语句等<br>
如查询某时间之后的注册用户：
```java
<select id="search" resultMap="userResultMap">
  select * from user where
    <if test="keyWord != null">
      user_name like CONCAT('%',#{keyWord},'%')
        or nick_name like CONCAT('%',#{keyWord},'%')
    </if>
    <if test="time != null">
      and  gmt_created <![CDATA[ >= ]]> #{time}
    </if>
</select>
```
**注意**： `>=、<、<=、>、>=、&` 这类的表达式会导致MyBatis解析失败，<br>所以建议使用`<![CDATA[ ]]>` 包围住

```java
@GetMapping("/user/search")
    @ResponseBody
    public List<UserDO> search(@RequestParam("keyWord") String keyWord,
    @RequestParam("time")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime time) {
        return userDAO.search(keyWord, time);
    }
```
**注意**：这里时间类型，我们要用`Spring`提供的`@DateTimeFormat`注解<br>
用于将字符串参数转为日期类型。（使用时前后端必须遵守统一数据格式）

### where
上面例子中的SQL查询语句在实际运行中会发生例外：<br>
1. keyWord 为 null，SQL变成 
```java
select * from user where and  gmt_created >= ?
```
错误的SQL（条件缺失）

2. keyWord、time都为null，SQL会变成
```java
select * from user where
```
也是错误的（条件缺失）<br>
因此，我们可以使用`WHERE`解决
```java
<select id="search" resultMap="userResultMap">
  select * from user
   <where>
      <if test="keyWord != null">
          user_name like CONCAT('%',#{keyWord},'%')
            or nick_name like CONCAT('%',#{keyWord},'%')
      </if>
      <if test="time != null">
        and  gmt_created <![CDATA[ >= ]]> #{time}
      </if>
   </where>
</select>
```
当条件缺失，where会自动去掉不该有的and，或者去除where条件等；<br>

对应代码[mybatisDynamicSQL01](/codes/mybatisDynamicSQL01/) [mybatisDynamicSQL02](/codes/mybatisDynamicSQL02/)

---

## 循环部分
### foreach
```java
//insert into user (user_name, pwd, nick_name,avatar,gmt_created,gmt_modified) 
// VALUS (?, ?, ?,?,now(),now()),
//      (?, ?, ?,?,now(),now()),
//      (?, ?, ?,?,now(),now())

<insert id="batchAdd" parameterType="java.util.List" useGeneratedKeys="true" keyProperty="id">
    INSERT INTO user (user_name, pwd, nick_name,avatar,gmt_created,gmt_modified)
    VALUES
    <foreach collection="list" item="it" index="index" separator =",">
        (#{it.userName}, #{it.pwd}, #{it.nickName}, #{it.avatar},now(),now())
    </foreach >
</insert>

//select * from user where id in (1,2,3)
<select id="findByIds" resultMap="userResultMap">
    select * from user
    <where>
        id in
        <foreach item="item" index="index" collection="ids"
                    open="(" separator="," close=")">
            #{item}
        </foreach>
    </where>
</select>
```
**属性**：
- collection：指定集合上下文参数名称，这里的list对应DAO类中方法的参数注解`@Param("list")`
- item：指定遍历每一个数据的变量，获取值时eg:it、it.xxx
- index：索引值，从0开始
- open：节点开始自定义分隔符
- close：节点结束自定义分隔符
- separator：遍历每条记录的分隔符

本部分对应代码：
[mybatisDynamicSQL03](/codes/mybatisDynamicSQL03/) <br>
[mybatisDynamicSQL04](/codes/mybatisDynamicSQL04/)

---

详情内容链接 [动态SQL](https://mybatis.net.cn/dynamic-sql.html)