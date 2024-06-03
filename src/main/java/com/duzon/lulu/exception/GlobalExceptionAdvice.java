package com.duzon.lulu.exception;

import com.duzon.common.model.LuluResult;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestControllerAdvice
@EnableWebMvc
public class GlobalExceptionAdvice { // Exception Interceptor

//    @ExceptionHandler(value = { Exception.class })
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public HashMap<String, String> runtimeException(Exception e, HttpServletRequest request, HttpServletResponse response) {
//        System.out.println("Excpetion test");
//        HashMap<String ,String> result = new HashMap<>();
//        result.put("code", "-1");
//        result.put("msg", e.getMessage() );
//
//        return result;
//    }
    @ExceptionHandler(value = {
            MethodArgumentNotValidException.class
            // 예) InspectionNotFoundException.class 등 자체 을 추가할 수 있다.
    })
    @ResponseStatus(HttpStatus.OK)
    public LuluResult invalidParamException(MethodArgumentNotValidException e, HttpServletRequest request, HttpServletResponse response) throws IOException {
//        RequestInfo requestInfo = ExtractUtil.extractHeader(request, response);
//        String fileName = e.getParameter().getExecutable().getDeclaringClass().getName();
//        if (requestInfo != null) {
//            CommonLogger.logException(logger, fileName, -1,
//                    requestInfo.getTransactionId(), requestInfo.getApiCode(),
//                    String.format("Exception [%d] %s", -1, e.getMessage()));
//        }

        LuluResult apiResult = new LuluResult();
        StringBuilder builder = new StringBuilder();
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        for(int i = 0; i < fieldErrors.size(); i ++ ){
            builder.append(fieldErrors.get(i).getDefaultMessage());
            if((i+1) != fieldErrors.size()) {
                builder.append("\n");
            }
        }
        apiResult.setResultCode(-1);
        apiResult.setResultMsg(builder.toString());
        System.out.println("[ResultData] " + apiResult);
        return apiResult;
    }
    @ExceptionHandler(value = {
            HisException.class
            // 예) InspectionNotFoundException.class 등 자체 을 추가할 수 있다.
    })
    @ResponseStatus(HttpStatus.OK)
    public LuluResult invalidParamException(HisException e, HttpServletRequest request, HttpServletResponse response) throws IOException {
//        RequestInfo requestInfo = ExtractUtil.extractHeader(request, response);
//        String fileName = String.valueOf(e.getStackTrace()[0]);
//        if (requestInfo != null) {
//            CommonLogger.logException(logger, fileName, -1,
//                    requestInfo.getTransactionId(), requestInfo.getApiCode(),
//                    String.format("Exception [%d] %s", e.getResultCode(), e.getMessage()));
//        }

        LuluResult apiResult = new LuluResult();
        apiResult.setResultCode(e.getResultCode());
        apiResult.setResultMsg(e.getMessage());
        apiResult.setResultData(e.getException());
        System.out.println("[ResultData] " + apiResult);
        return apiResult;
    }

    @ExceptionHandler(value = {
            Exception.class,
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public LuluResult runtimeException(RuntimeException e, HttpServletRequest request, HttpServletResponse response) throws IOException {
//        RequestInfo requestInfo = ExtractUtil.extractHeader(request, response);
//        String fileName = String.valueOf(e.getStackTrace()[0]);
//        if (requestInfo != null) {
//            CommonLogger.logException(logger, fileName, -1,
//                    requestInfo.getTransactionId(), requestInfo.getApiCode(),
//                    String.format("Exception [%d] %s", -1, e.getMessage()));
//        }

        LuluResult apiResult = new LuluResult();
        apiResult.setResultCode(-1);
        apiResult.setResultMsg(e.getMessage());
        System.out.println("[ResultData] " + apiResult);
        return apiResult;
    }

    @ExceptionHandler(value = {
            NoHandlerFoundException.class,
    })
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public LuluResult noHandlerFoundException(NoHandlerFoundException e, HttpServletRequest request, HttpServletResponse response) throws IOException {
//        RequestInfo requestInfo = ExtractUtil.extractHeader(request, response);
//        String fileName = String.valueOf(e.getStackTrace()[0]);
//        if (requestInfo != null) {
//            CommonLogger.logException(logger, fileName, -1,
//                    requestInfo.getTransactionId(), requestInfo.getApiCode(),
//                    String.format("Exception [%d] %s", -1, e.getMessage()));
//        }

        LuluResult apiResult = new LuluResult();
        apiResult.setResultCode(-1);
        apiResult.setResultMsg(e.getMessage());
        return apiResult;
    }
}