package com.duzon.lulu.service.MSC.common.utils;

import com.duzon.common.model.LuluResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.HashMap;
import java.util.Map;

public class ExceptionUtil {

    public static LuluResult parseMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();

        Map<String, String> errors = new HashMap<>();
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }

        LuluResult luluResult = new LuluResult();
        luluResult.setResultCode(400);
        luluResult.setResultMsg("Invalid parameter");
        luluResult.setResultData(errors);
        return luluResult;
    }
}
