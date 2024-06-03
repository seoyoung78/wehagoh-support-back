package com.duzon.lulu.service.MSC.MSC_090000.model;

import com.duzon.lulu.service.MSC.util.HealthSessionContext;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class RefCvr extends HealthSessionContext {
    private String prsc_cd;
    private Integer inpt_sqno;
    private String rfvl_dvsn_cd;
    private String sex_dvsn_cd;
    private String rfvl_age_clsf_cd;
    private Integer lwlm_rang_age;
    private String age_lwlm_rang_type_cd;
    private Integer uplm_rang_age;
    private String age_uplm_rang_type_cd;
    private String rfvl_lwlm_valu;
    private String rfvl_lwlm_rang_type_cd;
    private String rfvl_uplm_valu;
    private String rfvl_uplm_rang_type_cd;
    private String rfvl_rmrk;
    private String strt_date;
    private String end_date;
    private String origin_strt_date;
    private String del_yn;
}
