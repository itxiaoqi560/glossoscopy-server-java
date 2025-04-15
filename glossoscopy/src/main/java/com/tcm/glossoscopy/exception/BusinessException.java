package com.tcm.glossoscopy.exception;

import com.tcm.glossoscopy.enums.ExceptionEnum;


public class BusinessException extends RuntimeException{
    public BusinessException(ExceptionEnum exceptionEnum){
        super(exceptionEnum.getMessage());
    }
}
