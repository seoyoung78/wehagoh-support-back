package com.duzon.lulu.service.MSC.MSC_060000.controller;

import com.duzon.common.model.LuluResult;
import com.duzon.lulu.annotation.HealthRestController;
import com.duzon.lulu.service.MSC.MSC_060000.model.SaveRequired;
import com.duzon.lulu.service.MSC.MSC_060000.model.TrtrDtUpdate;
import com.duzon.lulu.service.MSC.MSC_060000.service.IMSC_060000Service;
import com.duzon.lulu.service.MSC.common.model.Exam.DetailQueryKey;
import com.duzon.lulu.service.MSC.common.model.Exam.PrscStatParam;
import com.duzon.lulu.service.MSC.common.utils.ExceptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;

/*
    진료지원 재활물리치료
 */

@HealthRestController("/MSC_060000")
public class MSC_060000Controller {

    @Autowired
    private IMSC_060000Service IMSC_060000Service;

    // 재활 default 값 조회(공통코드)
    @PostMapping("/selectDefault")
    public LuluResult selectDefault() throws Exception {
        return this.IMSC_060000Service.selectDefault();
    }


    // 접수 목록 조회
    @PostMapping("/selectReceptionList")
    public LuluResult selectReceptionList(@RequestBody HashMap<String, Object>  param) throws Exception {
        return this.IMSC_060000Service.selectReceptionList(param);
    }

    // 환자정보 조회
    @PostMapping("/selectPatientInfo")
    public LuluResult selectPatientInfo(@RequestBody HashMap<String, Object> param) throws Exception {
        return this.IMSC_060000Service.selectPatientInfo(param);
    }

    // 처방 목록 조회
    @PostMapping("/selectPrscList")
    public LuluResult selectPrscList(@RequestBody HashMap<String, Object>  param) throws Exception {
        return this.IMSC_060000Service.selectPrscList(param);
    }

    @PostMapping("/reception")
    public LuluResult reception(@RequestBody PrscStatParam param) throws Exception {
        return this.IMSC_060000Service.reception(param);
    }

    @PostMapping("/save")
    public LuluResult save(@Valid @RequestBody SaveRequired param) throws Exception {
        return this.IMSC_060000Service.save(param);
    }

    @PostMapping("/updateMdtrHopeDate")
    public LuluResult updateMdtrHopeDate(@RequestBody List<HashMap<String, Object>> param) throws Exception {
        return this.IMSC_060000Service.updateMdtrHopeDate(param);
    }

    @PostMapping("/updateDcRqstY")
    public LuluResult updateDcRqstY(@RequestBody PrscStatParam param) throws Exception {
        return this.IMSC_060000Service.updateDcRqstY(param);
    }

    @PostMapping("/updateTrtmDt")
    public LuluResult updateTrtmDt(@Valid @RequestBody TrtrDtUpdate param) throws Exception {
        return this.IMSC_060000Service.updateTrtmDt(param);
    }

    @PostMapping("/selectRecord")
    public LuluResult selectRecord(@RequestBody HashMap<String, Object>  param) throws Exception {
        return this.IMSC_060000Service.selectRecord(param);
    }

    @PostMapping("/selectHistory")
    public LuluResult selectHistory(@Valid @RequestBody DetailQueryKey param) throws Exception {
        return this.IMSC_060000Service.selectHistory(param);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public LuluResult handleValidationException(MethodArgumentNotValidException ex) {
        return ExceptionUtil.parseMethodArgumentNotValidException(ex);
    }
}
