package com.okay.testcenter.exception;

import com.okay.testcenter.common.constant.ReturnCode;
import com.okay.testcenter.domain.RetResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.validation.ValidationException;
import java.net.UnknownHostException;
import java.util.concurrent.TimeoutException;


/**
 * 全局异常处理，处理请求参数校验不通过
 */


@Slf4j
@RestControllerAdvice
public class BindExceptionHanlder {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(Exception.class)
    public RetResult handleBindException(Exception ex) {

        logger.error("处理异常==", ex);
        RetResult errorResult = new RetResult();

        if (ex instanceof BindException) {
            String errMsg = ((BindException) ex).getFieldError().getDefaultMessage();
            errorResult.setCode(ReturnCode.PARAM_ERROR.getCode());
            errorResult.setMsg(ReturnCode.PARAM_ERROR.getMsg() + ":" + errMsg);
        } else if (ex instanceof MethodArgumentNotValidException) {
            String errMsg = ((MethodArgumentNotValidException) ex).getBindingResult().getFieldError().getDefaultMessage();
            errorResult.setCode(ReturnCode.PARAM_ERROR.getCode());
            errorResult.setMsg(ReturnCode.PARAM_ERROR.getMsg() + ":" + errMsg);
        } else if(ex instanceof ValidationException){
            String errMsg = ((ValidationException) ex).getCause().getMessage();
            errorResult.setCode(ReturnCode.PARAM_ERROR.getCode());
            errorResult.setMsg(ReturnCode.PARAM_ERROR.getMsg() + ":" + errMsg);

        }else if(ex instanceof MissingServletRequestParameterException){
            String errMsg = ((MissingServletRequestParameterException) ex).getMessage();
            errorResult.setCode(ReturnCode.PARAM_ERROR.getCode());
            errorResult.setMsg(ReturnCode.PARAM_ERROR.getMsg() + ":" + errMsg);

        }else if(ex instanceof HttpRequestMethodNotSupportedException){
            String errMsg = ((HttpRequestMethodNotSupportedException) ex).getMessage();
            errorResult.setCode(ReturnCode.METHOD_NOT_ALLOWED_ERROR.getCode());
            errorResult.setMsg(ReturnCode.METHOD_NOT_ALLOWED_ERROR.getMsg() + ":" + errMsg);

        } else if (ex instanceof HttpMediaTypeNotSupportedException) {
            String errMsg = ((HttpMediaTypeNotSupportedException) ex).getMessage();
            errorResult.setCode(ReturnCode.HTTP_MEDIA_TYPE_NOTSSUPPERT_ERROR.getCode());
            errorResult.setMsg(ReturnCode.HTTP_MEDIA_TYPE_NOTSSUPPERT_ERROR.getMsg() + ":" + errMsg);

        } else if (ex instanceof HttpMediaTypeNotAcceptableException) {
            String errMsg = ((HttpMediaTypeNotAcceptableException) ex).getMessage();
            errorResult.setCode(ReturnCode.HTTP_MEDIA_TYPE_NOTACCEPTABLE_ERROR.getCode());
            errorResult.setMsg(ReturnCode.HTTP_MEDIA_TYPE_NOTACCEPTABLE_ERROR.getMsg() + ":" + errMsg);

        } else if (ex instanceof MissingPathVariableException) {
            String errMsg = ((MissingPathVariableException) ex).getMessage();
            errorResult.setCode(ReturnCode.MISSLING_PATH_VARIABLE_ERROR.getCode());
            errorResult.setMsg(ReturnCode.MISSLING_PATH_VARIABLE_ERROR.getMsg() + ":" + errMsg);

        } else if (ex instanceof ServletRequestBindingException) {
            String errMsg = ((ServletRequestBindingException) ex).getMessage();
            errorResult.setCode(ReturnCode.SERVLET_REQUEST_BINDING_ERROR.getCode());
            errorResult.setMsg(ReturnCode.SERVLET_REQUEST_BINDING_ERROR.getMsg() + ":" + errMsg);

        } else if (ex instanceof ConversionNotSupportedException) {
            String errMsg = ((ConversionNotSupportedException) ex).getMessage();
            errorResult.setCode(ReturnCode.CONVERSION_NOT_SUPPORTED_ERROR.getCode());
            errorResult.setMsg(ReturnCode.CONVERSION_NOT_SUPPORTED_ERROR.getMsg() + ":" + errMsg);

        } else if (ex instanceof TypeMismatchException) {
            String result = ((TypeMismatchException) ex).getPropertyName();
            String except = ((TypeMismatchException) ex).getRequiredType().getTypeName();
            errorResult.setCode(ReturnCode.TYPE_MISMATCH_ERROR.getCode());
            errorResult.setMsg(ReturnCode.TYPE_MISMATCH_ERROR.getMsg() + ":参数=" + result + ",应该为=" + except);

        } else if (ex instanceof HttpMessageNotReadableException) {
            String errMsg = ((HttpMessageNotReadableException) ex).getMessage();
            errorResult.setCode(ReturnCode.HTTP_MESSAGE_NOT_READABLE_ERROR.getCode());
            errorResult.setMsg(ReturnCode.HTTP_MESSAGE_NOT_READABLE_ERROR.getMsg() + ":" + errMsg);

        } else if (ex instanceof HttpMessageNotWritableException) {
            String errMsg = ((HttpMessageNotWritableException) ex).getMessage();
            errorResult.setCode(ReturnCode.HTTP_MESSAGE_NOT_WRITABLE_ERROR.getCode());
            errorResult.setMsg(ReturnCode.HTTP_MESSAGE_NOT_WRITABLE_ERROR.getMsg() + ":" + errMsg);
        } else if (ex instanceof MissingServletRequestPartException) {
            String errMsg = ((MissingServletRequestPartException) ex).getMessage();
            errorResult.setCode(ReturnCode.MISSING_SERVLET_REQUEST_PART_ERROR.getCode());
            errorResult.setMsg(ReturnCode.MISSING_SERVLET_REQUEST_PART_ERROR.getMsg() + ":" + errMsg);
        } else if (ex instanceof NoHandlerFoundException) {
            String errMsg = ((NoHandlerFoundException) ex).getMessage();
            errorResult.setCode(ReturnCode.NO_HANDLER_FOUND_ERROR.getCode());
            errorResult.setMsg(ReturnCode.NO_HANDLER_FOUND_ERROR.getMsg() + ":" + errMsg);
        } else if (ex instanceof AsyncRequestTimeoutException) {
            String errMsg = ((AsyncRequestTimeoutException) ex).getMessage();
            errorResult.setCode(ReturnCode.ASYNC_REQUEST_TIMEOUT_ERROR.getCode());
            errorResult.setMsg(ReturnCode.ASYNC_REQUEST_TIMEOUT_ERROR.getMsg() + ":" + errMsg);
        }  else if (ex instanceof ClassNotFoundException) {
            String errMsg = ((ClassNotFoundException) ex).getMessage();
            errorResult.setCode(ReturnCode.CLASS_NOT_FOUND_ERROR.getCode());
            errorResult.setMsg(ReturnCode.CLASS_NOT_FOUND_ERROR.getMsg() + ":" + errMsg);
        }else if (ex instanceof NullPointerException) {
            String errMsg = ((NullPointerException) ex).getMessage();
            errorResult.setCode(ReturnCode.NULL_ERROR.getCode());
            errorResult.setMsg(ReturnCode.NULL_ERROR.getMsg() + ":" + errMsg);
        }else if (ex instanceof UnknownHostException) {
            String errMsg = ((UnknownHostException) ex).getMessage();
            errorResult.setCode(ReturnCode.UNKNOWN_ERROR.getCode());
            errorResult.setMsg(ReturnCode.UNKNOWN_ERROR.getMsg() + ":" + errMsg);
        }else if (ex instanceof TimeoutException) {
            String errMsg = ((TimeoutException) ex).getMessage();
            errorResult.setCode(ReturnCode.TIME_OUT_ERROR.getCode());
            errorResult.setMsg(ReturnCode.TIME_OUT_ERROR.getMsg() + ":" + errMsg);
        }else if (ex instanceof AuthenticationException) {
            String errMsg = ex.getMessage();
            errorResult.setCode(400);
            errorResult.setMsg(errMsg);
        }
        else {
            errorResult.setCode(ReturnCode.UNKNOWN_ERROR.getCode());
            errorResult.setMsg(ReturnCode.UNKNOWN_ERROR.getMsg());
        }

        return errorResult;
    }



}
