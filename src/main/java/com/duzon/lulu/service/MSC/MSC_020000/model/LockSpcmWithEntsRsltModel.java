package com.duzon.lulu.service.MSC.MSC_020000.model;

import lombok.Getter;
import lombok.Setter;

/**
 * LockThree의 결과모델
 * 검사취소, 중간보고, 판독취소 에서 사용.
 * @author khgkjg12 강현구
 * @implNote 2024-03-13
 */
@Setter
@Getter
public class LockSpcmWithEntsRsltModel extends ExmnRsltPkModel{
	private String rslt_prgr_stat_cd;//결과 진행 상태.
	private String spcm_ents_prgr_stat_cd;//위탁 진행 상태.
	private String exmn_rslt_valu;//검사 결과 값.
	private String txt_rslt_valu;
	private String exmn_item_rmrk_cnts;
	private String ents_exmn_inst_cd;//위탁 기관 코드.
	private String spcm_ents_trms_dt;
	private String spcm_ents_trms_usid;

	private String exmn_rslt_tycd;//검사결과유형코드
	private int inpr_nodg;//정수부 자리수. -1은 무제약
	private int dcpr_nodg;//소수부 자리수. -1은 무제약
	private int nodg;//전체 자리수. -1은 무제약
	private String rslt_unit_nm;//결과 단위 명.
	private String rfvl_lwlm_valu;//참고치하한.
	private String rfvl_uplm_valu;//참고치상한.
	private String rfvl_lwlm_rang_type_cd;
	private String rfvl_uplm_rang_type_cd;
	
	//계산식에서 사용.
	private String pid;
    private String sex_cd;
    private Integer age;
}