package com.duzon.lulu.service.MSC.util.CALC;

import com.duzon.lulu.service.MSC.ExternalInterface.Saerom.mapper.MSC_CalcMapper;
import com.duzon.lulu.service.MSC.ExternalInterface.Saerom.model.CreatinineCodeInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

@Component
public class CalculatorInfoFactory {

    private static final HashSet<String> examCodeSet = new HashSet<>(Arrays.asList("D2280003"));

    @Autowired
    MSC_CalcMapper calcMapper;

    @Autowired
    CalculatorStrategy strategy;


    public String getResultValue(HashMap param) {
        if (examCodeSet.contains((String)param.get("exmn_cd"))) {
            return getCalculatorValue(param);
        }else {
            return param.get("exmn_rslt_valu").toString();
        }
    }

    private String getCalculatorValue(HashMap param) {
        String examCd = (String)param.get("exmn_cd");
        try {
            switch (examCd) {
                case "D0013":
                    CreatinineCodeInfo info = calcMapper.getExamCodeInfo(param);
                    info.setExmn_rslt_valu((String) param.get("exmn_rslt_valu"));
                    int result = strategy.creatinineStrategy(info);
                    return result > 0 ? String.valueOf(result) : null;
                default:
                    return null;
            }
        } catch (Exception e) {
            return null;
        }
    }
}
