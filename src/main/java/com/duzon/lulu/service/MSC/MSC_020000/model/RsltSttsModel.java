package com.duzon.lulu.service.MSC.MSC_020000.model;

import lombok.Getter;
import lombok.Setter;

/**
 * 진단검사 결과, 결과 현황, MST 그리드 모델.
 * @author khgkjg12 강현구A
 */
@Getter
@Setter
public class RsltSttsModel {
	private String cndt_dy;//실 검사일자.
	private String rcpn_no; // 접수일련번호
	private long hope_exrm_dept_sqno; // 검사실 코드
	private String pid; // 등록번호 (우측상단 환자정보에도 같이 사용함)
	private String pt_nm;
	private String nm_dscm_dvcd;
	private String mdcr_dr_user_nm; // 진료의 명(우측상단 환자정보에도 같이 사용함)
	private String prsc_prgr_stat_cd; // 상태코드
	private String mdcr_dr_sign_lctn;
	private String mdcr_date;
	private String emrg_pt_yn;
	private String pt_use_yn;//환자기본정보 use_yn,
	private String sex_age;
	private String spcm_no_set;
}
