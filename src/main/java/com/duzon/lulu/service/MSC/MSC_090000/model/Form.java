package com.duzon.lulu.service.MSC.MSC_090000.model;

import com.duzon.lulu.service.MSC.util.HealthSessionContext;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigInteger;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class Form extends HealthSessionContext {
    // 검사정보
    private String prsc_cd;             // 처방코드
    private String prsc_nm;             // 처방명
    private String suga_hnm;            // 한글명
    private String insn_edi_cd;         // EDI코드
    private String prsc_clsf_cd;        // 처방분류 코드
    private String prsc_clsf_nm;        // 처방분류 명칭
    private String suga_clsf_no;        // 한글분류
    private String apdy;                // 시작일
    private String endy;                // 종료일
    private String grp_sngl_dvcd;       // S/G
    private String suga_enm;            // 영문명

    // 검사 기본 설정
    private String slip_cd;             // slip코드
    private String slip_nm;             // 검사구분
    private BigInteger exrm_dept_sqno;  // 검사실 일련번호
    private String exmn_need_time;      // 검사소요시간 코드
    private String exmn_rslt_tycd;      // 결과유형 코드(데이터소스 프론트에서 처리)
    private String exmn_rslt_uncd;      // 단위 코드
    private String dcpr_nodg;           // 유효숫자 코드
    private String prsc_nots;           // 메모

    // 내시경검사 설정
    private String mdtr_site_cd;        // 치료부위 코드

    // 영상검사 설정
    private String mdlt_dvcd;           // Modality 코드
    private String phtg_site_dvnm;      // 촬영부위 명칭
    private String onsd_btsd_dvnm;      // 편측양측 명칭
    private String cnmd_use_yn;         // 조영제사용유무

    // 진단검사 설정
    private String spcm_cd_1;           // 검체종류 코드
    private String entd_exmn_yn;        // 위탁여부
    private String ents_exmn_inst_nm;   // 위탁기관 또는 수탁기관 명칭
    private java.math.BigDecimal spcm_need_vol_1;   // 검체용량
    private String spcm_dosg_unit_1;    // 검체용량단위
    private String fix_vol_dvsn;        // 정량표시(데이터소스 프론트에서 처리)
    private String uprn_slip_clsf_cd;   // slip 상위 분류 코드

    // 기능검사 설정
    private String wrcn_need_yn;        // 동의서필요여부
    private String wrcn_cd;             // 동의서코드

    // 물리치료 설정
    private String exmn_rslt_rptg_yn;   // 검사결과보고여부

    private String exmn_rslt_yn;        // 검사결과만 사용하는 코드여부
    private String prsc_psbl_yn;        // 처방가능여부
    private String del_yn;              // 삭제여부

    private List<Group> setList;

    private String date;

    private String formState;
}
