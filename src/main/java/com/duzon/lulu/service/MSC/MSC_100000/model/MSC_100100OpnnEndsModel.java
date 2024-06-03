package com.duzon.lulu.service.MSC.MSC_100000.model;

import lombok.Data;

@Data
/* 검사소견/내시경상세 join 모델 */
public class MSC_100100OpnnEndsModel {
    /* mscexmoed: 검사소견 내시경 상세 컬럼 */
    private long exmn_opnn_detl_sqno;
    private long exmn_opnn_sqno;
    private String obsr_opnn;
    private String obsr_opnn_cnts;
    private String obsr_opnn_site_1;
    private String obsr_opnn_site_2;
    private String advc_matr;
    private String advc_matr_cnts;
    private String etnl_obsr_opnn;
    private String dre_opnn;
    private String rslt_opnn_1;
    private String rslt_opnn_2;
    private String rslt_opnn_3;
    private String cncr_mdex_advc_matr;
    /* mscexmopm: 검사소견 마스터 컬럼 */
    private String exmn_dvcd;
    private String ends_exmn_dvcd;
    private String exmn_opnn_titl;
    private String exmn_opnn_cnts;
}
