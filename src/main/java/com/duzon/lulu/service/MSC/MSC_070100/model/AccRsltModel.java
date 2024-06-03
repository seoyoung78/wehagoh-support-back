package com.duzon.lulu.service.MSC.MSC_070100.model;

import lombok.Getter;
import lombok.Setter;

/**
 * 누적검사결과 모델
 * 
 * @author khgkjg12 강현구A
 * 
 */
@Getter
@Setter
public class AccRsltModel {
	private String rcpn_no;
	private String cndt_dy;
	private String exmn_cd;
	private String prsc_nm;
	private String rfvl_lwlm_valu;
	private String rfvl_uplm_valu;
	private String exmn_rslt_unit_nm;
	private String exmn_rslt_1;
	private String rslt_type_dvsn;
	private String rfvl_lwlm_rang_type_cd;
	private String rfvl_uplm_rang_type_cd;
}
