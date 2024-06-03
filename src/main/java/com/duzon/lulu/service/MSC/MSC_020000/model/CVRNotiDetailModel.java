package com.duzon.lulu.service.MSC.MSC_020000.model;

import lombok.Getter;
import lombok.Setter;

/*
 * CVR알림 API호출 Detail 인자 조회API
 * 작성자 : 강현구
 */
@Getter
@Setter
public class CVRNotiDetailModel {
	private String exmn_date;//YYYY-MM-DD
	private String prsc_cd;
	private String prsc_nm;
	private String mdcr_dr_usr_sqno;
	private String exmn_rslt_valu;
	private String cvr_lwlm_valu;
	private String cvr_lwlm_rang_type_cd;
	private String cvr_uplm_valu;
	private String cvr_uplm_rang_type_cd;
	private String rslt_rgst_dt;//YYYY-MM-DD hh:mm
	private String prsc_dr_usr_sqno;
	private String exmn_rslt_unit_nm;
	private String pt_nm;
}
