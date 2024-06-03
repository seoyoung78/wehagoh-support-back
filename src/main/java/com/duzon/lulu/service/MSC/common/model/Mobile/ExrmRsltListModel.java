package com.duzon.lulu.service.MSC.common.model.Mobile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExrmRsltListModel {
    private String pid;   // 환자등록번호
    private String prsc_date; // 검사날짜
    private String prsc_cd;  // 처방코드
    private String mdcr_dr_id;  // 진료의
    private String cndt_dt;     // 실시일시
    private String prsc_nm;     // 처방명
    private String exmn_rslt_tycd; // 결과유형
    private String spcm_hnm;        // 검체명
    private String brcd_no;         // 바코드번호
    private String rslt_sqno;       // 결과 일련번호
    private String prsc_sqno;       // 처방일련번호
    private String rslt_type_dvsn;  // 결과타입구분
    private String exmn_rslt_valu;  // 검사결과값
    private String txt_rslt_valu;   // 텍스트결과값
    private String rfvl_lwlm_valu;  // 참고치하한값
    private String rfvl_uplm_valu;  // 참고치상한값
    private String rslt_unit_dvsn;  // 참고치단위
    private String exmn_cd;     // 검사코드
    private String frst_rgdt;   // 최조등록일시
    private String trms_stat_dvsn;  // 수탁진행코드
    private String entd_exmn_yn;  // 수탁여부
    private String cmcd_nm;  // 보험코드
    private String rptg_dt;  // 등록날짜
    private String state_cd; // 검사상태
}
