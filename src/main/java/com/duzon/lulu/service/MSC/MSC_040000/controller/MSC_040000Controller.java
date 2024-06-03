package com.duzon.lulu.service.MSC.MSC_040000.controller;

import com.duzon.lulu.annotation.HealthRestController;
import com.duzon.lulu.service.MSC.MSC_040000.service.IMSC_040000Service;
import com.duzon.lulu.service.MSC.common.model.Exam.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/*
    진료지원 영상검사
 */
@HealthRestController("/MSC_040000")
public class MSC_040000Controller {
    @Autowired
    IMSC_040000Service imsc040000Service;

    @PostMapping("/saveConduct")
    public Object saveConduct(@RequestBody PrscStatParam param) throws Exception { return imsc040000Service.saveConduct(param); }

    @PostMapping("/selectRadiology")
    public Object selectRadiology(@RequestBody HashMap<String, Object> param) throws Exception { return this.imsc040000Service.selectRadiology(param); }

    @PostMapping("/save")
    public Object save(@RequestBody HashMap<String, Object> param) throws Exception { return imsc040000Service.save(param); }

    @PostMapping("/interpret")
    public Object interpret(@RequestBody HashMap<String, Object> param
            , @RequestHeader("Authorization") String authorization
    ) throws Exception {
        param.put("authorization", authorization);
        return imsc040000Service.interpret(param); }

    @PostMapping("/selectHistory")
    public Object selectHistory(@RequestBody HashMap<String, Object> param) throws Exception { return this.imsc040000Service.selectHistory(param); }
}
