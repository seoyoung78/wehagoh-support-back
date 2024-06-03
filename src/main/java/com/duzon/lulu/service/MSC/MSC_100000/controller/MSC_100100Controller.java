package com.duzon.lulu.service.MSC.MSC_100000.controller;

import com.duzon.common.model.LuluResult;
import com.duzon.lulu.annotation.HealthRestController;
import com.duzon.lulu.service.MSC.MSC_100000.model.MSC_100100ExmnAndEndsDetlList;
import com.duzon.lulu.service.MSC.MSC_100000.model.MSC_100100OpnnModel;
import com.duzon.lulu.service.MSC.MSC_100000.model.MSC_100100Request;
import com.duzon.lulu.service.MSC.MSC_100000.service.IMSC_100100Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@HealthRestController("/MSC_100100")
public class MSC_100100Controller {
    @Autowired
    private IMSC_100100Service imsc100100Service;
    private Logger logger = LoggerFactory.getLogger(MSC_100100Controller.class);

    @PostMapping("/exmnDvcd")
    public Object getExcmnDvcd() {
        return imsc100100Service.getExmnDvcd();
    }

    @PostMapping("/opnnList")
    public Object getOpnnList() {
        return imsc100100Service.getOpnnList();
    }

    @PostMapping("/searchOpnn")
    public Object searchOpnn(@RequestBody MSC_100100Request.SearchOpnn param) {
        return imsc100100Service.searchOpnn(param);
    }

    @PostMapping("/opnn/{exmn_opnn_sqno}")
    public Object getOpnn(@PathVariable String exmn_opnn_sqno) {
        long exmnOpnnSqno = 0;
        try {
            exmnOpnnSqno = Long.parseLong(exmn_opnn_sqno);
        } catch (NumberFormatException e) {
            LuluResult result = new LuluResult();
            result.setResultCode(404);
            result.setResultData(null);
            result.setResultMsg("유효하지 않은 숫자형식");
            return result;
        }
        return imsc100100Service.getOpnn(exmnOpnnSqno);
    }

    @PostMapping("/ends/{exmn_opnn_sqno}")
    public Object getEnds(@PathVariable String exmn_opnn_sqno) {
        long exmnOpnnSqno = 0;
        try {
            exmnOpnnSqno = Long.parseLong(exmn_opnn_sqno);
        } catch (NumberFormatException e) {
            LuluResult result = new LuluResult();
            result.setResultCode(404);
            result.setResultData(null);
            result.setResultMsg("유효하지 않은 숫자형식");
            return result;
        }
        return imsc100100Service.getEnds(exmnOpnnSqno);
    }

    @PostMapping("/saveExmnOpnn")
    public Object saveExmnOpnn(@RequestBody MSC_100100OpnnModel param) { return imsc100100Service.saveExmnOpnn(param); }

    @PostMapping("/saveGsspOpnn")
    public Object saveGsspOpnn(@RequestBody MSC_100100Request.SaveGsspOpnn param) {
        return imsc100100Service.saveEndsOpnn(param.toMSC_100100ExmnAndEndsDetlList());
    }

    @PostMapping("/saveClnsOpnn")
    public Object saveClnsOpnn(@RequestBody MSC_100100Request.SaveClnsOpnn param) {
        return imsc100100Service.saveEndsOpnn(param.toMSC_100100ExmnAndEndsDetlList());
    }

    @PostMapping("/editExmnOpnn/{exmn_opnn_sqno}")
    public Object editExmnOpnn(@PathVariable String exmn_opnn_sqno, @RequestBody MSC_100100OpnnModel param) {
        long exmnOpnnSqno = 0;
        try {
            exmnOpnnSqno = Long.parseLong(exmn_opnn_sqno);
        } catch (NumberFormatException e) {
            LuluResult result = new LuluResult();
            result.setResultCode(404);
            result.setResultData(null);
            result.setResultMsg("유효하지 않은 숫자형식");
            return result;
        }

        param.setExmn_opnn_sqno(exmnOpnnSqno);
        return imsc100100Service.editExmnOpnn(param);
    }

    @PostMapping("/editGsspOpnn/{exmn_opnn_sqno}")
    public Object editGsspOpnn(@PathVariable String exmn_opnn_sqno, @RequestBody MSC_100100Request.SaveGsspOpnn param) {
        long exmnOpnnSqno = 0;
        try {
            exmnOpnnSqno = Long.parseLong(exmn_opnn_sqno);
        } catch (NumberFormatException e) {
            LuluResult result = new LuluResult();
            result.setResultCode(404);
            result.setResultData(null);
            result.setResultMsg("유효하지 않은 숫자형식");
            return result;
        }

        MSC_100100ExmnAndEndsDetlList paramTrans = param.toMSC_100100ExmnAndEndsDetlList();
        paramTrans.setExmn_opnn_sqno(exmnOpnnSqno);
        return imsc100100Service.editEndsOpnn(paramTrans);
    }

    @PostMapping("/editClnsOpnn/{exmn_opnn_sqno}")
    public Object editClns(@PathVariable String exmn_opnn_sqno, @RequestBody MSC_100100Request.SaveClnsOpnn param) {
        long exmnOpnnSqno = 0;
        try {
            exmnOpnnSqno = Long.parseLong(exmn_opnn_sqno);
        } catch (NumberFormatException e) {
            LuluResult result = new LuluResult();
            result.setResultCode(404);
            result.setResultData(null);
            result.setResultMsg("유효하지 않은 숫자형식");
            return result;
        }

        MSC_100100ExmnAndEndsDetlList paramTrans = param.toMSC_100100ExmnAndEndsDetlList();
        paramTrans.setExmn_opnn_sqno(exmnOpnnSqno);
        return imsc100100Service.editEndsOpnn(paramTrans);
    }

    @PostMapping("/deleteExmnOpnn/{exmn_opnn_sqno}")
    public Object deleteExmnOpnn(@PathVariable String exmn_opnn_sqno) {
        long exmnOpnnSqno = 0;
        try {
            exmnOpnnSqno = Long.parseLong(exmn_opnn_sqno);
        } catch (NumberFormatException e) {
            LuluResult result = new LuluResult();
            result.setResultCode(404);
            result.setResultData(null);
            result.setResultMsg("유효하지 않은 숫자형식");
            return result;
        }

        return imsc100100Service.deleteExmnOpnn(exmnOpnnSqno);
    }

    @PostMapping("/deleteEndsOpnn/{exmn_opnn_sqno}")
    public Object deleteEndsOpnn(@PathVariable String exmn_opnn_sqno) {
        long exmnOpnnSqno = 0;
        try {
            exmnOpnnSqno = Long.parseLong(exmn_opnn_sqno);
        } catch (NumberFormatException e) {
            LuluResult result = new LuluResult();
            result.setResultCode(404);
            result.setResultData(null);
            result.setResultMsg("유효하지 않은 숫자형식");
            return result;
        }

        return imsc100100Service.deleteEndsOpnn(exmnOpnnSqno);
    }
}
