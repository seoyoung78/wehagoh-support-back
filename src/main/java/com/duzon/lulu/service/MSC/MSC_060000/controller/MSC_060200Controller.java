package com.duzon.lulu.service.MSC.MSC_060000.controller;

import com.duzon.common.model.LuluResult;
import com.duzon.lulu.annotation.HealthRestController;
import com.duzon.lulu.service.MSC.MSC_060000.model.PatientQueryParams;
import com.duzon.lulu.service.MSC.MSC_060000.model.PrintPatientsDate;
import com.duzon.lulu.service.MSC.MSC_060000.model.SelectedPatient;
import com.duzon.lulu.service.MSC.MSC_060000.model.SelectedPatients;
import com.duzon.lulu.service.MSC.MSC_060000.service.IMSC_060200Service;
import com.duzon.lulu.service.MSC.common.utils.ExceptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@HealthRestController("/MSC_060200")
public class MSC_060200Controller {
    @Autowired
    private IMSC_060200Service imsc_060200Service;

    @PostMapping("/selectPatientList")
    public Object selectPatientList(@Valid @RequestBody PatientQueryParams param) throws Exception { return imsc_060200Service.selectPatientList(param); }

    @PostMapping("/selectPrscList")
    public Object selectPrscList(@Valid @RequestBody SelectedPatient param) throws Exception { return imsc_060200Service.selectPrscList(param); }

    @PostMapping("/selectPrintPatientList")
    public Object selectPrintPatientList(@Valid @RequestBody PrintPatientsDate param) throws Exception { return imsc_060200Service.selectPrintPatientList(param); }

    @PostMapping("/selectPrintMdtr")
    public Object selectPrintMdtr(@Valid @RequestBody SelectedPatients param) throws Exception { return imsc_060200Service.selectPrintMdtr(param); }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public LuluResult handleValidationException(MethodArgumentNotValidException ex) {
        return ExceptionUtil.parseMethodArgumentNotValidException(ex);
    }
}
