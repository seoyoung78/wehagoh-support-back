package com.duzon.lulu.service.MSC.common.model.Exam;

import com.duzon.lulu.service.MSC.util.HealthSessionContext;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.*;

@Data
@EqualsAndHashCode(callSuper=false)
public class PrscStatParam extends HealthSessionContext {
    private List<HashMap<String, Object>> detailsList;
    private String type;
    private String exrmClsfCd;
    private String date;
    private String ptNm;
    private String pt_nm;
    private String dcResn;
    private String result;
    private String resultDate;
    private String range;
    private String authorization;
    private String transactionId;

    // 외부 호출 시 설정
    private String requestCompany;
    private String requestUser;
    private Long company_no;
}
