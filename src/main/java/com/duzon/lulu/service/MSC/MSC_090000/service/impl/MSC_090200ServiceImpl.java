package com.duzon.lulu.service.MSC.MSC_090000.service.impl;

import com.duzon.common.model.LuluResult;
import com.duzon.lulu.service.MSC.MSC_090000.mapper.MSC_090200Mapper;
import com.duzon.lulu.service.MSC.MSC_090000.model.*;
import com.duzon.lulu.service.MSC.MSC_090000.service.IMSC_090200Service;
import com.duzon.lulu.service.MSC.util.HealthSessionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class MSC_090200ServiceImpl implements IMSC_090200Service {
    @Autowired
    MSC_090200Mapper msc090200Mapper;

    @Autowired
    private HealthSessionContext healthSessionContext;

    @Override
    public List<SpcmCtrn> selectSpcmSetList(HashMap<String, Object> param) {
        healthSessionContext.setContext();
        healthSessionContext.setSessionForHashMap(param);
        return msc090200Mapper.selectSpcmSetList(param);
    }

    @Override
    public List<CommonData> selectCtnrList() {
        HashMap<String, Object> param = new HashMap<String, Object>(){};
        healthSessionContext.setContext();
        healthSessionContext.setSessionForHashMap(param);
        return msc090200Mapper.selectCtnrList(param);
    }

    @Transactional
    @Override
    public LuluResult saveSpcmSet(SpcmCtrn param) {
        healthSessionContext.setContext();
        healthSessionContext.setSessionForModel(param);

        LuluResult result = new LuluResult();
        result.setResultCode(200);

        msc090200Mapper.insertSpcmHistory(param);
        result.setResultData(msc090200Mapper.saveSpcmSet(param));

        return result;
    }

    @Transactional
    @Override
    public LuluResult saveCtnr(SpcmCtrn param) {
        healthSessionContext.setContext();
        healthSessionContext.setSessionForModel(param);

        LuluResult result = new LuluResult();
        result.setResultCode(200);

        msc090200Mapper.insertCtnrHistory(param);
        msc090200Mapper.saveCtnr(param);

        return result;
    }

    @Transactional
    @Override
    public LuluResult saveCtnrList(Ctrn param) {
        LuluResult result = new LuluResult();
         for (SpcmCtrn spcmCtrn : param.getChangeList()) {
            saveCtnr(spcmCtrn);
        }
         return result;
    }
}
