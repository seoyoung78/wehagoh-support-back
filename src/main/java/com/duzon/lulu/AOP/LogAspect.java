package com.duzon.lulu.AOP;

import com.duzon.common.model.LuluResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogAspect { // Aspect : 부가 기능 구현체들을 포함하고 있는 모듈
    private final Logger logger = LogManager.getLogger(this.getClass());

    // PointCut : 적용할 지점 또는 범위 선택 // PointCut은 경로를 잘 지정해줘야된다.
    @Pointcut("execution(* com.duzon.lulu..controller.*.*(..))")
    private void Target() {}

    // Advice : 실제 부가기능 구현부
    @Around("Target()")
    public Object calcPerformanceAdvice(ProceedingJoinPoint pjp) throws Throwable {
        Object result = pjp.proceed();

        if (!(result instanceof LuluResult)) {
            LuluResult apiResult = new LuluResult();
            apiResult.setResultData(result);
            apiResult.setResultCode(0);
            apiResult.setResultMsg("SUCCESS");
            result = apiResult;

            ObjectWriter ow = new ObjectMapper().writer();
            String json = ow.writeValueAsString(result);
            //System.out.println("[ResultData] " + json);
        }

        return result;
    }
}