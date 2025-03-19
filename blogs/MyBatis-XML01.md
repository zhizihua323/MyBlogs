# 复盘Mybatis XML
## 配置xml文件
注意：配置mybatis xml文件时，需要放在resources文件夹中，<br/>
并且要将dao文件路径保持一致<br/>
![mybatis01.png](/blogs/image/mybatis01.png)
<br/>
同时还要在.properties文件中完成mybatis.mapper-locations配置
![mybatis02.png](/blogs/image/mybatis02.png)
<br/>
详见 [mybatis01](/codes/mybatis01/)
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
> <resultMap id="userResultMap" type="com.youkeda.comment.dataobject。UserDO">
- id 对应DO对象，命名规则`xxxResultMap` 
- type 对应DO类路径

`id` & `result`
> <id column="id" property="id"/>
> <result column="user_name" property="userName"/>
- id设置数据库主键 column对应表，property对应DO属性名称
- result 设置数据库其他字段名称

有了resultMap 就不用使用别名 user_name as userName 了
对应代码 [mybatis02](/codes/mybatis02/)

---

