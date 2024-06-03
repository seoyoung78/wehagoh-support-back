package com.duzon.lulu.service.MSC.ExternalInterface.Infinitt.controller;

import com.duzon.lulu.annotation.HealthRestController;
import com.duzon.lulu.service.MSC.ExternalInterface.Infinitt.service.IInfinittService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.HashMap;


@Slf4j
@HealthRestController("/infinitt")
public class InfinittContoller {

    @Autowired
    private IInfinittService iInfinittService;

    @PostMapping("/orderInfo")
    public Object orderInfo(@RequestBody HashMap<String, String> param) throws Exception {
        return iInfinittService.getOrderInfo(param);
    }

    @PostMapping("/userInfo")
    public Object userInfo(@RequestBody HashMap<String, String> param) throws Exception {
        return iInfinittService.getUerInfo(param);
    }

    @PostMapping("/verification")
    public Object verificationProcedureForOrderInfo(@RequestBody HashMap<String, String> param) throws Exception {
        return iInfinittService.updateOrderInfo(param);
    }

    @PostMapping("/studyInfo")
    public Object studoInfo(@RequestBody HashMap<String, String> param, @RequestHeader("Authorization")String authorization) throws Exception {
        param.put("authorization", authorization);
        return iInfinittService.getStudyInfo(param);
    }

    @PostMapping("/reportInfo")
    public Object reportInfo(@RequestBody HashMap<String, String> param, @RequestHeader("Authorization")String authorization) throws Exception {
        param.put("authorization", authorization);
        return iInfinittService.saveReport(param);
    }


}
