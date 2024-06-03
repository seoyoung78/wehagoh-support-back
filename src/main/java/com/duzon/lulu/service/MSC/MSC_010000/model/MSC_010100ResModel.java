package com.duzon.lulu.service.MSC.MSC_010000.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MSC_010100ResModel {
    private String exrm_clsf_cd;
    private String pid;
    private String pt_nm;
    private String age_cd;
    private String rcpn_dt;
    private String mdcr_dr_nm;
    private String prsc_prgr_stat_cd;
    private Boolean emrg_stat;
    private String mdtr_site_cd;
    private String exmn_hope_date;
    private String cndt_dt;
}
