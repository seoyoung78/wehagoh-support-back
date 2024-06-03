package com.duzon.lulu.service.MSC.common.service.impl;

import com.duzon.common.model.LuluResult;
import com.duzon.common.util.ValidationUtil;
import com.duzon.lulu.service.MSC.common.mapper.MobileMapper;
import com.duzon.lulu.service.MSC.common.service.IMobileService;
import com.duzon.lulu.service.MSC.util.HealthSessionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


@Service
public class MobileServiceImpl implements IMobileService {

    @Autowired
    private HealthSessionContext healthSessionContext;

    @Autowired
    private MobileMapper mobileMapper;

    @Override
    public LuluResult exrmList(HashMap<String, Object> param) {
        // 필수 파라미터 체크
        String[] requirementKeys = { "pid" };
        LuluResult result = ValidationUtil.checkContainsKeysValue(requirementKeys, param);
        if(result.getResultCode() != 200) return result;

        healthSessionContext.setContext();
        healthSessionContext.setSessionForHashMap(param);

        Map resultMap = new HashMap();
        resultMap.put("result", mobileMapper.getExrmList(param));

        result.setResultData(resultMap);
        result.setResultMsg("성공");

        return result;
    }

    @Override
    public LuluResult exrmRsltList(HashMap<String, Object> param) {
        // 필수 파라미터 체크
        String[] requirementKeys = { "pid", "prsc_date", "hope_exrm_cd" };
        LuluResult result = ValidationUtil.checkContainsKeysValue(requirementKeys, param);

        if(result.getResultCode() != 200) return result;

        healthSessionContext.setContext();
        healthSessionContext.setSessionForHashMap(param);

        Map resultMap = new HashMap();
        resultMap.put("result", mobileMapper.getExrmRsltList(param));

        result.setResultData(resultMap);
        result.setResultMsg("성공");

        return result;
    }
}
