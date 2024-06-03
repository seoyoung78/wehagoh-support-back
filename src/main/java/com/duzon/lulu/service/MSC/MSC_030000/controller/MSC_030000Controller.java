package com.duzon.lulu.service.MSC.MSC_030000.controller;

import com.duzon.common.model.LuluResult;
import com.duzon.lulu.annotation.HealthRestController;
import com.duzon.lulu.common.service.InnerGatewayService;
import com.duzon.lulu.service.MSC.MSC_030000.model.UpdateRequest;
import com.duzon.lulu.service.MSC.MSC_030000.service.IMSC_030000Service;
import com.duzon.lulu.service.MSC.common.model.Exam.DetailQueryKey;
import com.duzon.lulu.service.MSC.common.model.Exam.PrscStatParam;
import com.duzon.lulu.service.MSC.common.utils.ExceptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.validation.Valid;

/*
    진료지원 기능검사
 */
@HealthRestController("/MSC_030000")
public class MSC_030000Controller {

    @Autowired
    private IMSC_030000Service IMSC_030000Service;
    @Autowired
    private InnerGatewayService innerGatewayService;
    @Autowired
    private ObjectMapper objectMapper;

    // 검사 결과 조회
    @PostMapping("/detail")
    public LuluResult detail(@Valid @RequestBody DetailQueryKey param) throws Exception {
        return this.IMSC_030000Service.detail(param);
    }

    // 기능검사 접수
    @PostMapping("/reception")
    public LuluResult reception(@RequestBody PrscStatParam param) throws Exception {
        return this.IMSC_030000Service.reception(param);
    }

    // 기능검사 결과
    @PostMapping("/updateStatusAndDetails")
    public LuluResult statusUpdateController(@RequestBody UpdateRequest param) throws Exception {
        return this.IMSC_030000Service.statusAndDetailUpdateService(param);
    }

    @PostMapping("/selectHistory")
    public Object selectHistory(@Valid @RequestBody DetailQueryKey param) throws Exception { return this.IMSC_030000Service.selectHistory(param); }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public LuluResult handleValidationException(MethodArgumentNotValidException ex) {
        return ExceptionUtil.parseMethodArgumentNotValidException(ex);
    }
}
