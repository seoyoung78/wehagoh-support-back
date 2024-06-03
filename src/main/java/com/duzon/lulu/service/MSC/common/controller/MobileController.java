package com.duzon.lulu.service.MSC.common.controller;

import com.duzon.lulu.annotation.HealthRestController;
import com.duzon.lulu.service.MSC.common.service.IMobileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;

/*
    진료지원 모바일 API
 */
@HealthRestController("/mobile")
public class MobileController {

    @Autowired
    private IMobileService iMobileService;

    /** 검사결과 목록 조회 */
    @PostMapping("/exrmList")
    public Object exrmList(@RequestBody HashMap param) throws Exception {
        Object result = iMobileService.exrmList(param);
        return result;
    }

    /** 검사결과 목록 조회 */
    @PostMapping("/exrmRsltList")
    public Object exrmRsltList(@RequestBody HashMap param) throws Exception {
        Object result = iMobileService.exrmRsltList(param);
        return result;
    }
}
