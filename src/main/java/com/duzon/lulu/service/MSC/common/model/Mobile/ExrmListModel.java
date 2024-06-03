package com.duzon.lulu.service.MSC.common.model.Mobile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExrmListModel {
    private int prsc_cnt;  // 검사 개수
    private String pid;  // 환자번호
    private String pt_nm;  // 환자이름
    private String real_pt_nm; // 실제환자이름(중복알파벳포함)
    private String frst_rgdt; // 처음 등록날짜
    private String prsc_date_ft; // 검사날짜
    private String prsc_date;  // 검사날짜(YYYY-MM-DD)
    private String mdcr_date; // 진료날짜
    private String prsc_nm;  // 검사명
    private String cndt_dt;
    private String age_cd; // 성별/나이
    private String hope_exrm_cd; // 희망검사실
    private String hope_exrm_nm; // 희망검사실 이름
    private String mdcr_user_nm; // 진료의명
    private String state_cd;  // 검사 상태코드(307 : 보고완료, 그외 보고대기)
    private String dobr; // 생년월일
    private String nm_dscm_dvsn;  // 이름 구분자
    private String dept_hnm; // 접수진료과 한글명
    private String dgns_nm; // 진단명
}
