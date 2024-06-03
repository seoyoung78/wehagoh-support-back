package com.duzon.lulu.service.MSC.MSC_040000.service.impl;

import com.duzon.common.model.LuluResult;
import com.duzon.common.util.ValidationUtil;
import com.duzon.lulu.service.MSC.MSC_040000.mapper.MSC_040000Mapper;
import com.duzon.lulu.service.MSC.MSC_040000.service.IMSC_040000Service;
import com.duzon.lulu.service.MSC.common.model.Exam.PrscStatParam;
import com.duzon.lulu.service.MSC.common.service.IExamService;
import com.duzon.lulu.service.MSC.util.HealthSessionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class MSC_040000ServiceImpl implements IMSC_040000Service {
    @Autowired
    MSC_040000Mapper MSC040100Mapper;
    @Autowired
    IExamService iExamService;
    @Autowired
    private HealthSessionContext healthSessionContext;

    @Override
    @Transactional
    public Integer insertHistory(HashMap<String, Object> param) {
        return MSC040100Mapper.insertHistory(param);
    }

    @Override
    @Transactional
    public LuluResult saveConduct(PrscStatParam param) {
        LuluResult result = new LuluResult();
        boolean check = false;
        for (HashMap<String, Object> detail: param.getDetailsList()) {
            detail.put("type", param.getType());
            result = iExamService.updatePrscStatItem(detail);
            if (result.getResultCode() == 400) {
                return result;
            } else if (result.getResultCode() == 200){
                if (param.getType().equals("ConductCancel")) {
                    detail.put("del_yn", "Y");
                } else {
                    detail.put("del_yn", "N");
                }
                MSC040100Mapper.insertConduct(detail);
                insertHistory(detail);
                check = true;
            }
        }

        if (check) {
            result.setResultCode(200);
        }

        return result;
    }

    @Override
    public LuluResult selectRadiology(HashMap<String, Object> param) {
        String[] keys = { "pid", "prsc_date", "prsc_sqno"};
        LuluResult result = ValidationUtil.checkContainsKeysValue(keys, param);
        if (result.getResultCode() != 200) {
            return result;
        }

        healthSessionContext.setContext();
        healthSessionContext.setSessionForHashMap(param);
        result.setResultData(MSC040100Mapper.selectRadiology(param));

        return result;
    }

    @Override
    @Transactional
    public LuluResult save(HashMap<String, Object> param) {
        LuluResult result = iExamService.updatePrscStatItem(param);

        if (result.getResultCode() == 200) {
            if (param.get("type").equals("Save")) {
                MSC040100Mapper.updateSave(param);
            } else {
                MSC040100Mapper.updateSaveCancel(param);
            }
            insertHistory(param);
        }

        return result;
    }

    @Override
    @Transactional
    public LuluResult interpret(HashMap<String, Object> param) throws Exception {
//        param.put("exrmClsfCd", "R");
        LuluResult result = iExamService.updatePrscStatItem(param);

        if (result.getResultCode() == 200) {
            if (param.get("type").equals("Interpret")) {
                MSC040100Mapper.updateInterpret(param);
                MSC040100Mapper.insertResult(param);
            } else {
                MSC040100Mapper.updateInterpretCancel(param);
            }
            insertHistory(param);
//            iExamService.sendNoti(param);
        }

        return result;
    }

    @Override
    public LuluResult selectHistory(HashMap<String, Object> param) {
        String[] keys = { "pid", "prsc_date", "prsc_sqno"};
        LuluResult result = ValidationUtil.checkContainsKeysValue(keys, param);
        if (result.getResultCode() != 200) {
            return result;
        }

        healthSessionContext.setContext();
        healthSessionContext.setSessionForHashMap(param);
        result.setResultData(MSC040100Mapper.selectHistory(param));

        return result;
    }

}
