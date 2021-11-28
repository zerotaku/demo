package com.example.demo.mapper;

import com.example.demo.entity.Access;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccessMapper  {

    int deleteByPrimaryKey(Integer id);

    int insert(Access record);

    int insertSelective(Access record);

    Access selectByPrimaryKey(Integer id);

    @Select("select * from access where appkey = #{key} and is_enable = 1 order by create_time desc LIMIT 1")
    Access findAccessByKey(@Param("key")String key);

    @Select("select * from access where is_enable = 1 ")
    List<Access> selectAccessIsEnable();

    int updateByPrimaryKeySelective(Access record);

    int updateByPrimaryKey(Access record);
}
