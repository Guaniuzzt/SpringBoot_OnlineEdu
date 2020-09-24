package com.atguigu.servicebase.exceptionhandler;






import com.atguigu.commonutils.R;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {


    //指定出现什么异常会执行这个方法

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public com.atguigu.commonutils.R error(Exception e){
        e.printStackTrace();
        return R.error().message("执行全局异常处理");
    }
}
