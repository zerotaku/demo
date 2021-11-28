package com.example.demo.ctrl;

import com.example.demo.common.Result;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author:wjup
 * @Date: 2018/9/26 0026
 * @Time: 14:42
 */

@RestController
@RequestMapping("/common")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("getUser/{id}")
    public String GetUser(@PathVariable int id){
        return userService.Sel(id).toString();
    }



    @PostMapping("keepAlive/{key}")
    public ResponseEntity keepAlive(@PathVariable String key) {
        return userService.keepAlive(key);
    }


    @PostMapping("keepAlive")
    public ResponseEntity keepAlive() {
        return userService.scriptKeepAliveRe();
    }


}