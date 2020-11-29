package com.zsw.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice//处理controller异常
public class ControllerExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());//获取日志记录


    @ExceptionHandler(Exception.class)//exception级别可以处理
    public ModelAndView exceptionHander(HttpServletRequest request, Exception e) throws Exception {
        logger.error("Requst URL : {}，Exception : {}", request.getRequestURL(),e);//记录异常信息

        if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null) {//根据指定状态返回
            throw e;
        }

        //记录之后返回异常页面
        ModelAndView mv = new ModelAndView();
        mv.addObject("url",request.getRequestURL());//前端页面获取的url放入对象
        mv.addObject("exception", e);//异常信息放入
        mv.setViewName("error/error");//返回到error/error
        return mv;
    }
}
