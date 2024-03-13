package com.practice.orm.advice;

import com.practice.orm.exception.CommentNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class CommentNotFoundAdvice {


    @ResponseBody
    @ExceptionHandler(CommentNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> exceptionHandler(CommentNotFoundException commentNotFoundException){
        Map<String, String> errorMsg = new HashMap<>();
        errorMsg.put("Comment Error : ", commentNotFoundException.getMessage());
        return errorMsg;
    }
}
