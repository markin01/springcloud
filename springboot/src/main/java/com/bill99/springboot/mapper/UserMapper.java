package com.bill99.springboot.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.bill99.springboot.model.User;

@Mapper
public interface UserMapper {
	
	// 获取主键
    @Insert("INSERT INTO user(name,password) VALUES (#{name}, #{password}) ")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(User user);

    @Delete("DELETE FROM user WHERE id　= #{id}")
    int delete(@Param("id") Integer id);

    @Update("UPDATE user SET name = #{name}, password = #{password} WHERE id = #{id}")
    int update(User user);

    @Select("SELECT id, name, password FROM user WHERE id = #{id}")
    @Results(id = "userMap", value = { @Result(column = "id", property = "id", javaType = Integer.class),
            @Result(column = "name", property = "name", javaType = String.class),
            @Result(column = "password", property = "password", javaType = String.class) })
    User findById(Integer id);

    @Select("SELECT * FROM user")
    @ResultMap("userMap")
    List<User> findAll();

}
