package com.wrc.tutor.common.exception;

public class BusinessException extends RuntimeException {

    public BusinessException(String msg){
        super(msg);
    }
    public BusinessException(String msg, Throwable t){
        super(msg,t);
    }
}
