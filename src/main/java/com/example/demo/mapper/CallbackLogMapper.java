package com.example.demo.mapper;

import com.example.demo.entity.CallbackLog;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CallbackLogMapper  {
    int deleteByPrimaryKey(Integer id);

//    @Insert("insert into callback_log (appkey, create_time, ip,\n" +
//            "      mac)\n" +
//            "    values (#{appkey,jdbcType=VARCHAR}, now(), #{ip,jdbcType=VARCHAR},\n" +
//            "      #{mac,jdbcType=VARCHAR})")
    int insert(@Param("record")CallbackLog record);

    int insertSelective(CallbackLog record);

    CallbackLog selectByPrimaryKey(Integer id);

    List<CallbackLog> selectByAppkeyList(@Param("time")String time);

    int updateByPrimaryKeySelective(CallbackLog record);

    int updateByPrimaryKey(CallbackLog record);
}
