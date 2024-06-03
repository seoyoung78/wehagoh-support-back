package com.duzon.lulu.service.MSC.MSC_020000.model;

import lombok.Getter;
import lombok.Setter;

/**
 * 진단검사 검사결과이력 모델.
 * @author khgkjg12 강현구A
 */
@Setter
@Getter
public class ExmnRsltHstrModel{
	private String spcm_no;
	private String exmn_cd;
	private String hstr_sqno;
	private String exmn_rslt_valu;
	private String txt_rslt_valu;
	private String rslt_unit_dvsn;
	private String rfvl_lwlm_valu;
	private String rfvl_uplm_valu;
	private String rslt_rgst_dt;
	private String rslt_rgst_usid;
	private String rslt_rgst_user_nm;
	private String dgsg_yn;
	private String hstr_stat_cd;
}
