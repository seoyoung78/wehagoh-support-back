package com.duzon.lulu.service.MSC.MSC_060000.service.impl;

import com.duzon.common.model.LuluResult;
import com.duzon.lulu.service.MSC.MSC_060000.mapper.MSC_060200Mapper;
import com.duzon.lulu.service.MSC.MSC_060000.model.*;
import com.duzon.lulu.service.MSC.MSC_060000.service.IMSC_060200Service;
import com.duzon.lulu.service.MSC.common.mapper.CommonMapper;
import com.duzon.lulu.service.MSC.common.model.Common.PatientInfo;
import com.duzon.lulu.service.MSC.util.HealthSessionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MSC_060200ServiceImpl implements IMSC_060200Service {
    @Autowired
    MSC_060200Mapper msc060200Mapper;

    @Autowired
    CommonMapper commonMapper;

    @Autowired
    private HealthSessionContext healthSessionContext;

    @Override
    public LuluResult selectPatientList(PatientQueryParams param) {
        LuluResult result = new LuluResult();
        result.setResultData(msc060200Mapper.selectPatientList(param));
        return result;
    }

    @Override
    public LuluResult selectPrscList(SelectedPatient param) {
        LuluResult result = new LuluResult();
        result.setResultData(msc060200Mapper.selectPrscList(param));
        return result;
    }

    @Override
    public LuluResult selectPrintPatientList(PrintPatientsDate param) {
        LuluResult result = new LuluResult();
        result.setResultData(msc060200Mapper.selectPrintPatientList(param));
        return result;
    }

    @Override
    public LuluResult selectPrintMdtr(SelectedPatients param) {
        LuluResult result = new LuluResult();
        HashMap<String, Object> infoParam = new HashMap<>();
        infoParam.put("pid", param.getPatientList());

        List<PatientInfo> patientInfoList = commonMapper.selectOnlyPatient(infoParam);
        List<Prescription> prescriptionList = msc060200Mapper.selectPrintMdtr(param);

        // 환자 리스트 hashMap 으로 변환
        Map<String, PatientInfo> patientInfoMap = new HashMap<>();

        for (PatientInfo item : patientInfoList) {
            patientInfoMap.put(item.getPid(), item);
        }

        // 처방 리스트 hashMap 으로 변환
        Map<String, List<Prescription>> prescriptionMap = new HashMap<>();

        for (Prescription item : prescriptionList) {
            // Map에서 현재 Prescription 객체의 pid를 키로 하는 List를 찾습니다.
            List<Prescription> items = prescriptionMap.get(item.getPid());

            if (items == null) {
                // 키에 해당하는 List가 없으면 새로운 List를 생성하고, Map에 추가합니다.
                items = new ArrayList<>();
                prescriptionMap.put(item.getPid(), items);
            }

            // 찾았거나 새로 생성한 List에 현재 Prescription 객체를 추가합니다.
            items.add(item);
        }

        HashMap<String, Object> resultData = new HashMap<String, Object>();
        resultData.put("patientMap", patientInfoMap);
        resultData.put("prscMap", prescriptionMap);
        result.setResultData(resultData);
        return result;
    }
}
