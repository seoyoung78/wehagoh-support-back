package com.duzon.lulu.service.MSC.MSC_040000.model;

import lombok.Data;

@Data
public class Detail {
    private String prsc_cd;
    private String prsc_nm;
    private String mdcr_user_nm;
    private String prsn_user_nm;
    private String prsc_prgr_stat_cd;
    private String mst_dt; // 접수시간
    private String slip_cd; // 슬립코드
    private String rcpn_sqno; // 접수일련번호
    private String prsc_dr_id; // 처방의사

    private String pid;
    private String prsc_date;
    private String prsc_sqno;
    private String hope_exrm_cd; // 검사실
    private String exmn_hope_date; // 희망검사일자

    // 다른곳에 표현하기 위해서 부르는 것
    private String pt_nm; // 환자명
    private String age_cd; // 성별/나이
    private String dobr; // 생년월일
    private String rcpn_dt; // 접수일시
    private String rcps_id; // 접수자
    private String iptn_dr_nm; // 판독의
    private String iptn_rslt; // 판독결과

    private String reportResult;

    // pacs
    private String pacs_rcpn_yn; // pacs 회신여부
    private String mdlt_dvcd; // 장비유형
    private String prsn_user_id; // 검사담당자 id
    private String prsc_memo; // 처방메모
    private String pacs_no; // access number
    private String dr_dept_cd; // 처방의 부서
    private String pacs_cd; // pacs code

    private String pacs_domn; // pacs 업체 도메인
    private String pacs_sr_link_path; // SR Link
    private String iptn_pacs_nm; // pacs 업체에서 판독할 경우 판독자/기관 명칭
}
