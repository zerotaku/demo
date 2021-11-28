package com.example.demo.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * callback_log
 * @author
 */
@Data
public class CallbackLog {
    private Integer id;

    /**
     * 唯一标识
     */
    private String appkey;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * IP地址
     */
    private String ip;

    /**
     * mac标识
     */
    private String mac;


    public CallbackLog(String appkey, String ip, String mac) {
        this.appkey = appkey;
        this.ip = ip;
        this.mac = mac;
    }
}
