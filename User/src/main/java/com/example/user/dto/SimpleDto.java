package com.example.user.dto;

public class SimpleDto {

    private boolean isSuccess;
    private String msg;
    private Object object;

    public SimpleDto(){
        isSuccess = false;
        msg = "";
        object = null;
    }

    public SimpleDto(boolean isSuccess, String msg, Object object) {
        this.isSuccess = isSuccess;
        this.msg = msg;
        this.object = object;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}
