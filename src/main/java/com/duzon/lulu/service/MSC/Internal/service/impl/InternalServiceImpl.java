package com.duzon.lulu.service.MSC.Internal.service.impl;

import com.duzon.common.model.LuluResult;
import com.duzon.lulu.service.MSC.Internal.mapper.InternalMapper;
import com.duzon.lulu.service.MSC.Internal.model.*;
import com.duzon.lulu.service.MSC.Internal.service.IInternalService;
import com.duzon.lulu.service.MSC.util.HealthSessionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class InternalServiceImpl implements IInternalService {
    @Autowired
    InternalMapper internalMapper;

    @Autowired
    private HealthSessionContext healthSessionContext;

    @Override
    public LuluResult getDidPatientList(DidParam param) throws Exception {
        LuluResult result = new LuluResult();
        // 필수 파라미터 체크
        if (param.getExrm_dept_sqno_list() == null || param.getExrm_dept_sqno_list().size() == 0) {
            result.setResultCode(500);
            result.setResultMsg("파라미터 [exrm_dept_sqno_list]은 필수 값입니다.");
            return result;
        }

        healthSessionContext.setContext();
        healthSessionContext.setSessionForModel(param);

        HashMap<String, Object> resultData = new HashMap<>();

        List<Patient> rcpnPatientList = new ArrayList<>();
        List<HashMap<String, Object>> watiPatient = new ArrayList<>();
        List<Patient> watiPatientList = internalMapper.selectWaitPatientList(param);
        for(Integer dept : param.getExrm_dept_sqno_list()) {
            HashMap<String, Integer> deptSqno = new HashMap<String, Integer>(){{
                put("exrm_dept_sqno", dept);
            }};
            // 검사실 별 최근 검사중/치료중 환자 1인 조회
            Patient patient = internalMapper.selectRcpnPatient(deptSqno);
            if (patient != null) rcpnPatientList.add(patient);

            // 검사실별 검사접수/치료대기 환자 목록
            HashMap<String, Object> watiPatients = new HashMap<>();
            watiPatients.put("exrm_dept_sqno", dept);
            watiPatients.put("patients", watiPatientList.stream()
                    .filter(list -> list.getExrm_dept_sqno().equals(dept))
                    .collect(Collectors.toList())
            );
            watiPatient.add(watiPatients);
        }

        resultData.put("exrm_patient", rcpnPatientList);
        resultData.put("waiting_patients", watiPatient);

        result.setResultCode(200);
        result.setResultData(resultData);

        return result;
    }
}
