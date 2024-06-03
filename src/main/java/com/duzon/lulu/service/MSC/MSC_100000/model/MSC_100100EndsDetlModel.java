package com.duzon.lulu.service.MSC.MSC_100000.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
/* 내시경 상세 테이블 */
public class MSC_100100EndsDetlModel {
    private long exmn_opnn_detl_sqno;
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
}
