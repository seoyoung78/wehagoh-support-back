package com.duzon.lulu.service.MSC.MSC_060000.model;

import lombok.Data;

@Data
public class Details {
    private String prsc_cd; // 검사코드
    private String prsc_nm; // 검사명
    private String prsc_dr_nm; // 진료의(처방)
    private String prsc_dr_id; // 처방의사
    private String prsc_dr_sqno; // 처방의사 유저 시퀀스 번호
    private String prsc_prgr_stat_cd; // 처방진행상태 코드
    private String prsc_memo; // 처방메모

    private String pid;
    private String prsc_date;
    private String prsc_sqno;
    private String hope_exrm_cd; // 검사실
    private String mdtr_hope_date; // 희망검사일자

    private String slip_cd; // 슬립코드
    private String rcpn_sqno; // 접수일련번호
    private String rcps_id; // 접수자 아이디
    private String rcps_sign; // 접수자 서명
    private String rcps_nm; // 접수자 이름
    private String trtm_strt_dt; // 처치 시작 일시
    private String trtm_end_dt; // 처치 종료 일시
    private String mdtr_opnn; // 치료 소견
    private String mdtr_memo; // 치료 메모

    private String dr_dept_cd; // 처방의 부서
    private String mdcr_dr_id; // 진료의사ID

    private String dc_rqst_yn; // DC 요청 여부
    private String exmn_rslt_rptg_yn; // [마스터] 검사 결과 보고 여부
    private String mdtr_rslt_rptg_yn; // [물리치료 결과] 치료 결과 보고 여부
}
