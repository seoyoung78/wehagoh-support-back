package com.duzon.lulu.service.MSC.MSC_080000.controller;

import com.duzon.lulu.annotation.HealthRestController;
import com.duzon.lulu.service.MSC.MSC_080000.model.MSC_080100ReqParamModel;
import com.duzon.lulu.service.MSC.MSC_080000.service.IMSC_080100Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@HealthRestController("/MSC_080100")
public class MSC_080100Controller {

    @Autowired
    private IMSC_080100Service iMSC_080100Service;

    private Logger logger = LoggerFactory.getLogger(MSC_080100Controller.class);

    @PostMapping("/exmPatient")
    public Object getExmPatient(@RequestBody MSC_080100ReqParamModel param){
        return this.iMSC_080100Service.getExmPatient(param);
    }
}