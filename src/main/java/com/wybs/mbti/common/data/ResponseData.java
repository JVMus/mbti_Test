package com.wybs.mbti.common.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * <p>接口响应统一格式</p>
 *
 * <p>Date：2018-04-02</p>
 *
 * @author Mumus
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class ResponseData<T> {
    @JsonProperty("ret_code")
    private int retCode;
    @JsonProperty("msg")
    private String msg;
    @JsonProperty("data")
    private T data;

    public ResponseData() {

    }

    public ResponseData(int retCode) {
        this.retCode = retCode;
    }

    public ResponseData(int retCode, String msg) {
        this.retCode = retCode;
        this.msg = msg;
    }

    public ResponseData(int retCode, String msg, T data) {
        this.retCode = retCode;
        this.msg = msg;
        this.data = data;
    }

    public int getRetCode() {
        return retCode;
    }

    public void setRetCode(int retCode) {
        this.retCode = retCode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ResponseData [retCode=" + retCode + ", msg=" + msg + ", data=" + data + "]";
    }

}
