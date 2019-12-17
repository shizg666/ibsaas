package com.landleaf.ibsaas.screen.config;

import com.landleaf.ibsaas.screen.model.resp.ResponseResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import reactor.core.publisher.Mono;

/**
 * @author Lokiy
 * @date 2019/12/17 11:31
 * @description:
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public Mono<ResponseResult> handlerMaxUploadSizeExceededException(Exception ex) {
        if (ex != null) {
            return Mono.just(ResponseResult.error(ex));
        }

        return Mono.just(ResponseResult.defaultError());

    }
}
