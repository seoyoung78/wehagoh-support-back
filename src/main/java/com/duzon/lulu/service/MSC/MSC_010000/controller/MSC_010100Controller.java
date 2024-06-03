package com.duzon.lulu.service.MSC.MSC_010000.controller;

import com.duzon.common.controller.LuluBaseController;
import com.duzon.lulu.annotation.HealthRestController;
import com.duzon.lulu.service.MSC.MSC_010000.model.MSC_010100ReqParamModel;
import com.duzon.lulu.service.MSC.MSC_010000.service.IMSC_010100Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@HealthRestController("/MSC_010100")
public class MSC_010100Controller extends LuluBaseController {
    @Autowired
    private IMSC_010100Service iMSC_010100Service;

    @PostMapping("/patient")
    public Object selectPatient(@RequestBody MSC_010100ReqParamModel param) { return iMSC_010100Service.selectPatient(param);}
}
