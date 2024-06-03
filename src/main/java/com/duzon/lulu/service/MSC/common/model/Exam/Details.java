package com.duzon.lulu.service.MSC.common.model.Exam;

import lombok.Data;

@Data
public class Details {
    private String prsc_cd; // 검사코드
    private String prsc_nm; // 검사명
    private String prsc_dr_nm; // 진료의(처방)
    private String prsc_dr_id; // 처방의사
    private String prsc_dr_sqno; // 처장의사 사용자일련번호
    private String prsn_user_nm; // 검사담당자
    private String prsc_prgr_stat_cd; // 처방진행상태 코드
    private String entd_exmn_yn; // 수탁검사여부
    private String spcm_cd; // 검체코드
    private String spcm_hnm; // 검체명
    private String prsc_nots; // 검사메모
    private String prsc_memo; // 처방메모
    private String ctnr_cd; // 용기코드
    private String ctnr_abnm_and_rgb; // 용기약어명 + 용기색깔RGB

    private String pid;
    private String prsc_date;
    private String prsc_sqno;
    private String hope_exrm_cd; // 검사실
    private String exmn_hope_date; // 희망검사일자
    private String rcpn_hm; // 접수시간
    private String rcpn_dt;
    private String cndt_hm; // 실시시간
    private String cndt_dt;

    private String rcpn_sqno; // 접수일련번호

    // pacs
    private String mdlt_dvcd; // 장비유형
    private String pacs_no; // access number
    private String pacs_cd; // pacs 업체 코드
    private String pacs_rcpn_yn; // pacs 수신 여부

    private String spcm_no_1;//바코드 번호
    private String dc_rqst_yn; // DC 요청 여부
    private String wrcn_wrtn_yn; // 동의서작성여부
    private String wrcn_cd; // 동의서코드
    private String mdtr_site_cd; // 치료부위코드
    private String mdcr_dr_id; // 진료의사ID
}
