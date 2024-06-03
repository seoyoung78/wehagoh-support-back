package com.duzon.lulu.service.MSC.common.model.Common;

import lombok.Getter;
import lombok.Setter;

/**
 * @author khgkjg12 강현구A
 * 
 *         미시행-미완료 모델
 */
@Getter
@Setter
public class UnactgUncmplModel {
	private String pid;
	private String exmn_hope_date;
	private String hope_exrm_cd;
	private String prsc_prgr_stat_cd;
	private String mdcr_date;
	private String rcpn_dt;
	private String exmn_hope_dt;
	private String mdcr_user_nm;
	private String pt_nm;
	private String nm_dscm_dvcd;
	private String dobr;
	private String sex_labl;
	private String age_labl;
	private String rcpn_sqno;
	private String prsc_date; //영상검사에서 사용.
	private String prsc_sqno; //영상검사에서 사용.
	private String emrg_pt_yn;
	private String sex_age;
}
