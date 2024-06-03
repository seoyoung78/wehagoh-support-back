package com.duzon.lulu.service.MSC.MSC_090000.controller;

import com.duzon.common.controller.LuluBaseController;
import com.duzon.lulu.annotation.HealthRestController;
import com.duzon.lulu.service.MSC.MSC_090000.model.*;
import com.duzon.lulu.service.MSC.MSC_090000.service.IMSC_090200Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;

@HealthRestController("/MSC_090200")
public class MSC_090200Controller extends LuluBaseController {
    @Autowired
    private IMSC_090200Service imsc_090200Service;

    @PostMapping("/selectSpcmSetList")
    public Object selectSpcmSetList(@RequestBody HashMap<String, Object> param) throws Exception { return this.imsc_090200Service.selectSpcmSetList(param); }

    @PostMapping("/selectCtnrList")
    public Object selectCtnrList() throws Exception { return this.imsc_090200Service.selectCtnrList(); }

    @PostMapping("/saveSpcmSet")
    public Object saveSpcmSet(@RequestBody SpcmCtrn param) throws Exception { return this.imsc_090200Service.saveSpcmSet(param); }

    @PostMapping("/saveCtnr")
    public Object saveCtnr(@RequestBody SpcmCtrn param) throws Exception { return this.imsc_090200Service.saveCtnr(param); }

    @PostMapping("/saveCtnrList")
    public Object saveCtnrList(@RequestBody Ctrn param) throws Exception { return this.imsc_090200Service.saveCtnrList(param); }
}
