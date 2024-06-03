package com.duzon.lulu.service.MSC.util.CALC.impl;

import com.duzon.lulu.service.MSC.ExternalInterface.Saerom.model.CreatinineCodeInfo;
import com.duzon.lulu.service.MSC.util.CALC.CalculatorStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
public class CreatinineCalculatorStrategy implements CalculatorStrategy {

    @Override
    public int creatinineStrategy(CreatinineCodeInfo info) {

        try {
            double age_cd = Double.parseDouble(info.getAge_cd());
            double cld_exmn_rslt_vl = 0;
            double exmn_rslt_vl = Double.parseDouble(info.getExmn_rslt_valu());
            if (age_cd > 0) {
                if (exmn_rslt_vl <= 0) {
                    cld_exmn_rslt_vl = 0;
                } else if (info.getSex_cd().equals("M")) {
                    cld_exmn_rslt_vl = Math.floor(175 * (Math.exp(-1.154 * Math.log(exmn_rslt_vl))) * (Math.exp(-0.203 * Math.log(age_cd))));
                } else if (info.getSex_cd().equals("F")) {
                    cld_exmn_rslt_vl = Math.floor(175 * (Math.exp(-1.154 * Math.log(exmn_rslt_vl))) * (Math.exp(-0.203 * Math.log(age_cd))) * 0.742);
                }
            }
            return (int) Math.round(cld_exmn_rslt_vl);
        } catch (Exception e) {
            return -1;
        }
    }
}
