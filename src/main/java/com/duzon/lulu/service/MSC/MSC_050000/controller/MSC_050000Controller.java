package com.duzon.lulu.service.MSC.MSC_050000.controller;

import com.duzon.common.model.LuluResult;
import com.duzon.lulu.annotation.HealthRestController;
import com.duzon.lulu.service.MSC.MSC_050000.model.HistoryQueryKey;
import com.duzon.lulu.service.MSC.MSC_050000.model.SaveRequired;
import com.duzon.lulu.service.MSC.MSC_050000.model.TabDetailRequest;
import com.duzon.lulu.service.MSC.MSC_050000.model.UpdateRequest;
import com.duzon.lulu.service.MSC.common.model.Exam.DetailQueryKey;
import com.duzon.lulu.service.MSC.common.model.Exam.PrscStatParam;
import org.springframework.beans.factory.annotation.Autowired;
import com.duzon.lulu.service.MSC.MSC_050000.service.IMSC_050000Service;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import com.duzon.lulu.service.MSC.common.utils.ExceptionUtil;

import javax.validation.Valid;
import java.util.HashMap;

/*
    진료지원 내시경검사
 */

@HealthRestController("/MSC_050000")
public class MSC_050000Controller {

   @Autowired
   private IMSC_050000Service IMSC_050000Service;

    // 내시경 공통코드 조회
    @PostMapping("/common")
    public LuluResult common() throws Exception {
        return this.IMSC_050000Service.common();
    }

    // 검사 결과 조회
    @PostMapping("/detail")
    public LuluResult detail(@Valid @RequestBody DetailQueryKey param) throws Exception {
        return this.IMSC_050000Service.detail(param);
    }

    // 탭 아이디에 따라 detail 조회 시 사용
    @PostMapping("/detailFetcher")
    public LuluResult detailFetcher(@Valid @RequestBody TabDetailRequest param) throws Exception {
        return this.IMSC_050000Service.detailFetcher(param);
    }

    // 내시경 검사 접수
    @PostMapping("/reception")
    public LuluResult reception(@RequestBody PrscStatParam param) throws Exception {
        return this.IMSC_050000Service.reception(param);
    }

    // 내시경 검사 저장
    @PostMapping("/save")
    public LuluResult save(@Valid @RequestBody SaveRequired param) throws Exception {
        return this.IMSC_050000Service.save(param);
    }

    // 내시경 검사 결과
    @PostMapping("/result")
    public LuluResult result(@Valid @RequestBody UpdateRequest param) throws Exception {
        return this.IMSC_050000Service.result(param);
    }

    // 내시경 검사 리스트 조회
    @PostMapping("/selectHistory")
    public LuluResult selectHistory(@Valid @RequestBody HistoryQueryKey param) throws Exception {
        return this.IMSC_050000Service.selectHistory(param);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public LuluResult handleValidationException(MethodArgumentNotValidException ex) {
        return ExceptionUtil.parseMethodArgumentNotValidException(ex);
    }
}
