package com.duzon.lulu.service.MSC.common.controller;

import com.duzon.common.model.LuluResult;
import com.duzon.lulu.annotation.HealthRestController;
import com.duzon.lulu.service.MSC.common.model.Exam.*;
import com.duzon.lulu.service.MSC.common.service.IExamService;
import com.duzon.lulu.service.MSC.common.utils.ExceptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import javax.validation.Valid;
import java.util.HashMap;

@HealthRestController("/exam")
public class ExamController {
    @Autowired
    IExamService iExamService;

    @PostMapping("/selectReceptionList")
    public Object selectReceptionList(@RequestBody HashMap<String, Object> param) throws Exception { return this.iExamService.selectReceptionList(param); }

    @PostMapping("/selectResultList")
    public Object selectResultList(@RequestBody HashMap<String, Object> param) throws Exception { return this.iExamService.selectResultList(param); }

    @PostMapping("/selectPrscList")
    public Object selectPrscList(@RequestBody HashMap<String, Object> param) throws Exception { return this.iExamService.selectPrscList(param); }

    @PostMapping("/updatePrscStat")
    public Object updatePrscStat(@RequestBody PrscStatParam param) throws Exception { return this.iExamService.updatePrscStat(param); }

    @PostMapping("/updatePrscStatItem")
    public Object updatePrscStatItem(@RequestBody HashMap<String, Object> param) throws Exception { return this.iExamService.updatePrscStatItem(param); }

    @PostMapping("/selectExamCheck")
    public Object selectExamCheck(@RequestBody PrscStatParam param) throws Exception { return  this.iExamService.selectExamCheck(param); }

    @PostMapping("/requestPrsc")
    public Object requestPrsc(@RequestBody PrscStatParam param
            , @RequestHeader("Transaction-Id") String transactionId
            , @RequestHeader("Authorization") String authorization
    ) throws Exception {
        param.setTransactionId(transactionId);
        param.setAuthorization(authorization);
        return this.iExamService.requestPrsc(param); }

    @PostMapping("/dcPrsc")
    public Object dcPrsc(@RequestBody PrscStatParam param
            , @RequestHeader("Transaction-Id") String transactionId
            , @RequestHeader("Authorization") String authorization
    ) throws Exception {
        param.setTransactionId(transactionId);
        param.setAuthorization(authorization);
        return this.iExamService.dcPrsc(param);
    }

    @PostMapping("/sendNoti")
    public Object sendNoti(@RequestBody HashMap<String, Object> param
            , @RequestHeader("Transaction-Id") String transactionId
            , @RequestHeader("Authorization") String authorization
    ) throws Exception {
        param.put("transactionId", transactionId);
        param.put("authorization", authorization);
        return this.iExamService.sendNoti(param);
    }

    @PostMapping("/sendNotiList")
    public Object sendNotiList(@RequestBody PrscStatParam param
            , @RequestHeader("Transaction-Id") String transactionId
            , @RequestHeader("Authorization") String authorization
    ) throws Exception {
        param.setTransactionId(transactionId);
        param.setAuthorization(authorization);
        return this.iExamService.sendNotiList(param);
    }

    @PostMapping("/sendPrgrProgressNoti")
    public Object sendPrgrProgressNoti(@RequestBody Progress param
            , @RequestHeader("Transaction-Id") String transactionId
            , @RequestHeader("Authorization") String authorization) throws Exception {
        param.setTransactionId(transactionId);
        param.setAuthorization(authorization);
        return this.iExamService.sendPrgrProgressNoti(param);
    }

    @PostMapping("/sendCvrRequestNoti")
    public Object sendCvrRequestNoti(@Valid @RequestBody CvrRequest param
            , @RequestHeader("Transaction-Id") String transactionId
            , @RequestHeader("Authorization") String authorization) throws Exception {
        param.setTransactionId(transactionId);
        param.setAuthorization(authorization);
        return this.iExamService.sendCvrRequestNoti(param);
    }

    @PostMapping("/sendIssueNoti")
    public Object sendIssueNoti(@RequestBody IssueRequest param
            , @RequestHeader("Transaction-Id") String transactionId
            , @RequestHeader("Authorization") String authorization) throws Exception {
        param.setTransactionId(transactionId);
        param.setAuthorization(authorization);
        return this.iExamService.sendIssueNoti(param);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public LuluResult handleValidationException(MethodArgumentNotValidException ex) {
        return ExceptionUtil.parseMethodArgumentNotValidException(ex);
    }
}
