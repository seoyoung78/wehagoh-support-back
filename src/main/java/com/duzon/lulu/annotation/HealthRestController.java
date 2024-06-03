package com.duzon.lulu.annotation;

import org.springframework.core.annotation.AliasFor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@RestController
@RequestMapping(method = {RequestMethod.GET, RequestMethod.POST})
@CrossOrigin(allowCredentials = "true", originPatterns = "*")
public @interface HealthRestController {
    @AliasFor(annotation = RequestMapping.class, attribute = "path")
    String[] value() default {};
}