package com.example.demo.common.util;

import com.example.demo.common.Result;
import com.example.demo.common.contant.RetCode;

import static com.example.demo.common.contant.RetCode.SUCCESS;

public class RetResponse {

    public static <T> Result<T> success() {
        return new Result<T>().setSuccess(true).setCode(RetCode.SUCCESS.getCode()).setMsg(RetCode.SUCCESS.getText());
    }

    public static <T> Result<T> success(String message) {
        return new Result<T>().setSuccess(true).setCode(RetCode.SUCCESS.getCode()).setMsg(message);
    }

    public static <T> Result<T> success(String message, T data) {
        return new Result<T>().setSuccess(true).setCode(RetCode.SUCCESS.getCode()).setMsg(message).setResult(data);
    }

    public static <T> Result<T> success(T data) {
        return new Result<T>().setSuccess(true).setCode(RetCode.SUCCESS.getCode()).setMsg(RetCode.SUCCESS.getText()).setResult(data);
    }

    public static <T> Result<T> error(String message) {
        return new Result<T>().setSuccess(false).setCode(RetCode.FAIL.getCode()).setMsg(message);
    }

    public static <T> Result<T> error(String message, T data) {
        return new Result<T>().setSuccess(false).setCode(RetCode.FAIL.getCode()).setMsg(message).setResult(data);
    }

    public static <T> Result<T> error() {
        return new Result<T>().setSuccess(false).setCode(RetCode.FAIL.getCode()).setMsg(RetCode.FAIL.getText());
    }
    public static <T> Result<T> error(RetCode code) {
        return new Result<T>().setSuccess(false).setCode(code.getCode()).setMsg(code.getText());
    }
    public static <T> Result<T> make(boolean isSuccess) {
        return isSuccess ? success() : error();
    }

    public static <T> Result<T> make(RetCode code) {
        return new Result<T>().setSuccess(code == RetCode.SUCCESS).setCode(code.getCode()).setMsg(code.getText());
    }

    public static <T> Result<T> make(int code, String msg) {
        return new Result<T>().setSuccess(code == RetCode.SUCCESS.getCode()).setCode(code).setMsg(msg);
    }

    public static <T> Result<T> make(int code, String msg, T data) {
        return new Result<T>().setSuccess(code == RetCode.SUCCESS.getCode()).setCode(code).setMsg(msg).setResult(data);
    }


}
