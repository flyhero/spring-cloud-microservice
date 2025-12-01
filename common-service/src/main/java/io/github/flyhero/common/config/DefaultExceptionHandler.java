package io.github.flyhero.common.config;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import io.github.flyhero.common.enums.ErrorCode;
import io.github.flyhero.common.exception.ArgumentError;
import io.github.flyhero.common.exception.BizException;
import io.github.flyhero.common.model.JsonResult;
import jakarta.servlet.http.HttpServletRequest;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@RestControllerAdvice
public class DefaultExceptionHandler {

    /**
    * 业务异常
    * */
    @ExceptionHandler(value = BizException.class)
    public JsonResult<String> toolkitExceptionHandler(HttpServletRequest request, BizException bizException) {
        log.error("业务异常 method : {} url : {} query : {} ", request.getMethod(), getRequestUrl(request), getRequestQuery(request), bizException);
        return JsonResult.error(500, bizException.getMessage());
    }
    /**
     * 参数验证异常
     */
    @SneakyThrows
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public JsonResult<List<ArgumentError>> validExceptionHandler(HttpServletRequest request, MethodArgumentNotValidException ex) {
        log.error("参数验证异常 method : {} url : {} query : {} ", request.getMethod(), getRequestUrl(request), getRequestQuery(request), ex);
        BindingResult bindingResult = ex.getBindingResult();
        List<ArgumentError> argumentErrorList = 
                bindingResult.getFieldErrors()
                        .stream()
                        .map(fieldError -> {
                            ArgumentError argumentError = new ArgumentError();
                            argumentError.setArgumentName(fieldError.getField());
                            argumentError.setMessage(fieldError.getDefaultMessage());
                            return argumentError;
                        }).collect(Collectors.toList());
        return JsonResult.errorData(ErrorCode.PARAMETER_ERROR.getCode(), argumentErrorList);
    }

    /**
     * 拦截未捕获异常
     */
    @ExceptionHandler(value = Throwable.class)
    public JsonResult<String> defaultErrorHandler(HttpServletRequest request, Throwable throwable) {
        log.error("全局异常 method : {} url : {} query : {} ", request.getMethod(), getRequestUrl(request), getRequestQuery(request), throwable);
        return JsonResult.error();
    }

    private String getRequestUrl(HttpServletRequest request) {
        return request.getRequestURL().toString();
    }

    private String getRequestQuery(HttpServletRequest request){
        return request.getQueryString();
    }
}
