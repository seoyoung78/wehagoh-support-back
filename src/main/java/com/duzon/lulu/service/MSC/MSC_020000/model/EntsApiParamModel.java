package com.duzon.lulu.service.MSC.MSC_020000.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EntsApiParamModel extends ExmnRsltPkModel {
	private String ents_exmn_inst_cd;
	private String pid;
	private String pt_nm;// 구분자 포함.
	private String sex;
	private String blcl_dy;
	private String dr_nm;
	private String prsc_nm;
	private String spcm_nm;
	private String spcm_cd;
	private String pt_frrn;
	private String pt_srrn;
	private String spcm_ents_prgr_stat_cd;
}
