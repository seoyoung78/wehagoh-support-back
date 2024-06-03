package com.duzon.lulu.service.MSC.MSC_070100.model;

import lombok.Getter;
import lombok.Setter;

/**
 * 진단검사 결과 모델.
 */
@Getter
@Setter
public class MslRsltModel {

	private String exmn_cd;// 검사코드.
	private String prsc_nm;
	private String prsc_date; // 처방 일자.
	private String rptg_dy; // 보고일자
	private String exmn_rslt_1; // 단문검사결과.
	private String exmn_rslt_2; // 장문검사결과.
	private String rfvl_lwlm_valu; // 진검 참고치 하단.
	private String rfvl_uplm_valu; // 진검 참고치 상단.
	private String exmn_rslt_unit_nm; // 진검 단위.
	private String exmn_pich_nm; // 검사담당자 이름.
	private String spcm_labl_nm; // 검체 라벨.
	private String rfvl_lwlm_rang_type_cd;
	private String rfvl_uplm_rang_type_cd;
}
