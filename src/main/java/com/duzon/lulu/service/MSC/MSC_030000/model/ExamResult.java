package com.duzon.lulu.service.MSC.MSC_030000.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExamResult {
    private String iptn_rslt; // 판독결과
    private String iptn_dt; // 판독일시
    private String iptn_dr_nm; // 판독의 성명
    private String mdcr_sign_lctn; // 진료의 서명
}
