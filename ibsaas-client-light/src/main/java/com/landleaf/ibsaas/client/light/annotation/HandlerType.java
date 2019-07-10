package com.landleaf.ibsaas.client.light.annotation;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface HandlerType {
    String type() default "";
    String desc() default "";
}
