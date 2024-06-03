package com.duzon.lulu.service.MSC.MSC_020000.model;

import lombok.Getter;
import lombok.Setter;

/**
 * 진단검사결과, 결과 목록 조회, DTL 그리드, 검사결과 모델.
 * @author khgkjg12 강현구
 * @implNote 최종 수정일 : 2024-03-13
 */
@Setter
@Getter
public class ExmnRsltModel {
	private String spcm_no;
	private String exmn_cd; /* 검사코드 */
	private String prsc_nm; /* 검사명 */
	private String spcm_labl_nm; /* 검체명 */
	private String pid; /* 환자 등록번호 */
	private String exmn_date; /* 검사일자 */
	private String rslt_type_dvsn;/* 결과유형 */
	private String exmn_rslt_valu; /* 검사결과값 */
	private String txt_rslt_valu; /* 텍스트결과값 */
	private String rfvl_lwlm_valu; /* 참고치하한값 */
	private String rfvl_uplm_valu; /* 참고치상한값 */
	private String rslt_unit_dvsn; /* 참고치단위 */
	private String spcm_ents_prgr_stat_cd; /* 수탁진행코드 */
	private String entd_exmn_yn; /* 수탁여부 */
	private String prsc_prgr_stat_cd;
	private String rptg_dy;
	private String rslt_prgr_stat_cd; /* 상태코드 */
	private String exmn_pich_nm;
	private String rslt_rgst_dt; /*결과 입력 일시*/
	private int inpr_nodg;
	private int dcpr_nodg;
	private int nodg;
	private String rfvl_lwlm_rang_type_cd;
	private String rfvl_uplm_rang_type_cd;
	private String exmn_item_rmrk_cnts;
}
