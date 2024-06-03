package com.duzon.lulu.service.MSC.common.model.Exam;

import com.duzon.lulu.service.MSC.util.HealthSessionContext;
import lombok.Data;

@Data
public class IssueRequest {
    public String pt_nm, userNm, authorization, transactionId, emrformsm, exrmClsfCd;
    public Integer mdfr_clsf_sqno;
}
