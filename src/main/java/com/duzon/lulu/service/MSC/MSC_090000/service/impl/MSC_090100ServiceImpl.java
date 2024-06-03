package com.duzon.lulu.service.MSC.MSC_090000.service.impl;

import com.duzon.common.model.LuluResult;
import com.duzon.lulu.service.MSC.MSC_090000.model.*;
import com.duzon.lulu.service.MSC.util.HealthSessionContext;
import com.duzon.lulu.service.MSC.MSC_090000.mapper.MSC_090100Mapper;
import com.duzon.lulu.service.MSC.MSC_090000.service.IMSC_090100Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class MSC_090100ServiceImpl implements IMSC_090100Service {
    @Autowired
    MSC_090100Mapper MSC090100Mapper;

    @Autowired
    private HealthSessionContext healthSessionContext;

    @Override
    public List<Tree> selectTreeList(){
        HashMap<String, Object> param = new HashMap<String, Object>(){};
        healthSessionContext.setContext();
        healthSessionContext.setSessionForHashMap(param);
        return MSC090100Mapper.selectTreeList(param);
    }

    @Override
    public List<CommonData> selectCommonData() {
        HashMap<String, Object> param = new HashMap<String, Object>(){};
        healthSessionContext.setContext();
        healthSessionContext.setSessionForHashMap(param);
        return MSC090100Mapper.selectCommonData(param);
    }

    @Override
    public List<Prescription> selectPrscList(HashMap<String, Object> param) {
        healthSessionContext.setContext();
        healthSessionContext.setSessionForHashMap(param);
        return MSC090100Mapper.selectPrscList(param);
    }

    @Override
    public List<Tree> selectSearchList(HashMap<String, Object> param){
        healthSessionContext.setContext();
        healthSessionContext.setSessionForHashMap(param);
        return MSC090100Mapper.selectSearchList(param);
    }

    /**
     * Form 조회
     * @param param
     * @return CSMS4000FormModel
     */
    @Override
    public Form selectForm(HashMap<String, Object> param){
        healthSessionContext.setContext();
        healthSessionContext.setSessionForHashMap(param);
        // Form 조회
        Form form = MSC090100Mapper.selectForm(param);
        form.setSetList(MSC090100Mapper.selectSetList(param));
        return form;
    }

    @Override
    public List<Group> selectExmnList(HashMap<String, Object> param){
        healthSessionContext.setContext();
        healthSessionContext.setSessionForHashMap(param);
        return MSC090100Mapper.selectExmnList(param);
    }

    @Transactional
    @Override
    public LuluResult save(Form param){
        healthSessionContext.setContext();
        healthSessionContext.setSessionForModel(param);

        LuluResult result = new LuluResult();

        // 신규 검사 추가 중복 코드 체크
        if (param.getFormState() != null && param.getFormState().equals("Added")) {
            Boolean check = this.MSC090100Mapper.selectExsistPrsc(param);
            if (check) {
                result.setResultCode(401);
                return result;
            }
        }

        MSC090100Mapper.saveForm(param);
        if (param.getPrsc_clsf_cd().equals("C1")) {
            MSC090100Mapper.saveC1Form(param);
            MSC090100Mapper.insertC1FormHistory(param);
        }

        // 검사 SET 등록&수정
        if (param.getSetList() != null && param.getSetList().size() > 0) {
            for (Group set: param.getSetList()) {
                healthSessionContext.setSessionForModel(set);
                MSC090100Mapper.saveSet(set);
            }
        }

        result.setResultCode(200);
        return result; // 정상적으로 저장
    }

    @Override
    public LuluResult delete(Form param){
        healthSessionContext.setContext();
        healthSessionContext.setSessionForModel(param);

        // 하위 검사 SET 유무 확인
        Boolean check = this.MSC090100Mapper.selectExsistSet(param);
        LuluResult result = new LuluResult();

        if (check) {
            result.setResultCode(401);
            return result;
        }

        param.setDel_yn("Y");
        MSC090100Mapper.saveC1Form(param);
        MSC090100Mapper.insertC1FormHistory(param);
        MSC090100Mapper.deleteRefCvr(param);
        MSC090100Mapper.deleteUnit(param);

        RefCvr refCvr = new RefCvr();
        refCvr.setPrsc_cd(param.getPrsc_cd());
        refCvr.setDel_yn("Y");
        this.MSC090100Mapper.insertRefCvrHistory(refCvr);

        Unit unit = new Unit();
        unit.setPrsc_cd(param.getPrsc_cd());
        unit.setDel_yn("Y");
        this.MSC090100Mapper.insertUnitHistory(unit);

        result.setResultCode(200);

        return result;
    }

    /**
     * 검체 상/하한치 리스트 조회
     * @param param
     * @return List<CSMS4000SpcmModel>
     */
    @Override
    public List<RefCvr> selectRefCvrList(HashMap<String, Object> param) {
        healthSessionContext.setContext();
        healthSessionContext.setSessionForHashMap(param);
        return MSC090100Mapper.selectRefCvrList(param);
    }

    @Override
    public LuluResult saveRefCvr(RefCvrParam param) {
        healthSessionContext.setContext();

        LuluResult luluResult = new LuluResult();
        luluResult.setResultCode(200);

        int count = 0;
        for (RefCvr refCvr : param.getRefCvrList()) {
            healthSessionContext.setSessionForModel(refCvr);
            count += this.MSC090100Mapper.saveRefCvr(refCvr);
            this.MSC090100Mapper.insertRefCvrHistory(refCvr);
        }
        luluResult.setResultData(count);

        return  luluResult;
    }

    @Override
    public List<Unit> selectUnitList(HashMap<String, Object> param) {
        healthSessionContext.setContext();
        healthSessionContext.setSessionForHashMap(param);
        return this.MSC090100Mapper.selectUnitList(param);
    }

    @Override
    public LuluResult saveUnit (UnitParam param) {
        healthSessionContext.setContext();

        LuluResult luluResult = new LuluResult();
        luluResult.setResultCode(200);

        int count = 0;
        for (Unit unit : param.getUnitList()) {
            healthSessionContext.setSessionForModel(unit);
            count += this.MSC090100Mapper.saveUnit(unit);
            this.MSC090100Mapper.insertUnitHistory(unit);
        }

        luluResult.setResultData(count);

        return  luluResult;
    }
}
