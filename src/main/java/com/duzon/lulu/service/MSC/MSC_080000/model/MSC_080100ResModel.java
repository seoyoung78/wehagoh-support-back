package com.duzon.lulu.service.MSC.MSC_080000.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MSC_080100ResModel {
    private String pid;                 //등록번호
    private String pt_nm;               //이름
    private String age_cd;              //성별 / 나이
    private String dobr;                //주민등록번호
    private String prsc_date;           //검사일자
    private String hope_exrm_cd;        //검사실코드
    private String exmn_hope_date;
    private String hope_exrm_nm;        //검사실명칭
    private String prsc_cd;             //검사코드
    private String prsc_nm;             //검사명
    private String mdcr_dr_id;          //진료실id
    private String mdcr_user_nm;        //진료의
    private String state_cd;            //상태코드
    private String state_nm;            //상태코드명
    private String prsc_date_to;
    private String prsc_prgr_stat_cd;   //진행상태
    private Boolean emrg_stat;
    private String exrm_clsf_cd;
    private String mdtr_yn;
    private String rcpn_dt;
    private String cndt_dt;
    private String mdtr_site_cd;
}