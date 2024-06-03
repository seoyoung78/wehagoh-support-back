package com.duzon.lulu.service.MSC.ExternalInterface.Irm.controller;

import com.duzon.lulu.annotation.HealthRestController;
import com.duzon.lulu.service.MSC.ExternalInterface.Irm.service.IIrmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.*;

@HealthRestController("/irm")
public class IrmController {
    @Autowired
    IIrmService iIrmService;

    @PostMapping("/sendIan")
    public Object sendIan(@RequestBody HashMap<String, Object> param
            , @RequestHeader("Authorization") String authorization
    ) throws Exception {
        param.put("authorization", authorization);
        return iIrmService.sendIan(param); }

    @PostMapping("/saveReport")
    public Object saveReport(@RequestBody HashMap<String, Object> param
            , @RequestHeader("Authorization") String authorization
    ) throws Exception {
        param.put("authorization", authorization);
        return iIrmService.saveReport(param);
    }
}
