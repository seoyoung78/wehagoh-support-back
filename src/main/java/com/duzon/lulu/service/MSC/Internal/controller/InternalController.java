package com.duzon.lulu.service.MSC.Internal.controller;

import com.duzon.lulu.annotation.HealthRestController;
import com.duzon.lulu.service.MSC.Internal.model.*;
import com.duzon.lulu.service.MSC.Internal.service.IInternalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@HealthRestController("/internal")
public class InternalController {
    @Autowired
    IInternalService iInternalService;

    @PostMapping("/getDidPatientList")
    public Object getDidPatientList(@RequestBody DidParam param) throws Exception { return this.iInternalService.getDidPatientList(param); }
}
