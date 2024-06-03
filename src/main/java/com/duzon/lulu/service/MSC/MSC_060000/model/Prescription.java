package com.duzon.lulu.service.MSC.MSC_060000.model;

import lombok.Data;

@Data
public class Prescription {
    private String mdtr_hope_date;
    private String prsc_cd;
    private String prsc_nm;
    private String trtm_dt;
    private String rcps_nm;
    private String mdtr_opnn;

    private String pid;
    private String pt_nm;

    private String trtm_strt_dt;
    private String trtm_end_dt;
    private String prsc_dr_nm;
}
