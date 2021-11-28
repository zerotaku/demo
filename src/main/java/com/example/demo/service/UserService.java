package com.example.demo.service;


import com.alibaba.fastjson.JSONObject;
import com.example.demo.common.Result;
import com.example.demo.common.contant.RetCode;
import com.example.demo.common.exception.BusinessException;
import com.example.demo.common.util.RetResponse;
import com.example.demo.common.util.SendQQMailUtil;
import com.example.demo.entity.Access;
import com.example.demo.entity.CallbackLog;
import com.example.demo.entity.User;
import com.example.demo.mapper.AccessMapper;
import com.example.demo.mapper.CallbackLogMapper;
import com.example.demo.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Author:wjup
 * @Date: 2018/9/26 0026
 * @Time: 15:23
 */
@Slf4j
@Service
public class UserService {

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private final UserMapper userMapper;
    private final AccessMapper accessMapper;
    private final CallbackLogMapper callbackLogMapper;

    @Autowired
    public UserService(UserMapper userMapper, AccessMapper accessMapper, CallbackLogMapper callbackLogMapper) {
        this.userMapper = userMapper;
        this.accessMapper = accessMapper;
        this.callbackLogMapper = callbackLogMapper;
    }

    public User Sel(int id) {
        return userMapper.Sel(id);
    }

    public ResponseEntity<Result<String>> keepAlive(String key) {
        Access info = null;
        try {
            info = accessMapper.findAccessByKey(key);
        } catch (Exception e) {
            UserService.log.error("保活异常:" + e.getMessage());
            throw new BusinessException(RetCode.FAIL, "保活异常,请联系管理员");
        }
        if (info == null) {
            return new ResponseEntity<>(RetResponse.error("保活失败,未获取到有效的key"), HttpStatus.OK);
        }

        CallbackLog log = new CallbackLog(key, "", "");
        try {
            callbackLogMapper.insert(log);
        } catch (Exception e) {
//            return "插入日志异常"+ e.getMessage();
            UserService.log.error("插入日志异常:" + e.getMessage());
        }
        return new ResponseEntity<>(RetResponse.success("保活成功"), HttpStatus.OK);
    }

    public ResponseEntity<Result<String>> scriptKeepAliveRe() {
        scriptKeepAlive();
        return new ResponseEntity<>(RetResponse.success("保活成功"), HttpStatus.OK);
    }

    public void scriptKeepAlive() {
        List<Access> infos;
        try {
            infos = accessMapper.selectAccessIsEnable();
        } catch (Exception e) {
            UserService.log.error("<scriptKeepAlive>获取所有已启动key异常:" + e.getMessage());
            throw new BusinessException(RetCode.FAIL, "获取所有已启动key异常,请联系管理员" + e.getMessage());
        }
        if (infos.size() == 0) {
            UserService.log.info("<scriptKeepAlive>无已启动key");
            return;
        }
        List<CallbackLog> callbackLogList = new ArrayList<>();
        try {
            String time = sdf.format(new Date(new Date().getTime() - 5 * 60 * 1000));
            callbackLogList = callbackLogMapper.selectByAppkeyList(time);
        } catch (Exception e) {
            UserService.log.error("<scriptKeepAlive>获取日志异常:" + e.getMessage());
            throw new BusinessException(RetCode.FAIL, "获取日志异常,请联系管理员" + e.getMessage());
        }
        if (callbackLogList.size() == 0) {
            UserService.log.info("<scriptKeepAlive>无心跳日志");
            SendQQMailUtil.SendQQMail("农民世界脚本失联", JSONObject.toJSON(infos).toString());
            return;
        }
        Set<String> logAppkeyList = callbackLogList.stream().map(CallbackLog::getAppkey).collect(Collectors.toSet());
        infos.removeAll(logAppkeyList.stream().toList());
        if (infos.size() > 0) {
            SendQQMailUtil.SendQQMail("农民世界脚本失联", JSONObject.toJSON(infos).toString());
        }
        UserService.log.info("<scriptKeepAlive>无死亡脚本想信息");

    }

}