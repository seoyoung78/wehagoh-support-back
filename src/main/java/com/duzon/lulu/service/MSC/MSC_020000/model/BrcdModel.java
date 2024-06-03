package com.duzon.lulu.service.MSC.MSC_020000.model;

import lombok.Getter;
import lombok.Setter;

/**
 * 바코드 모델. 바코드 출력시 사용.
 * 
 * @author khgkjg12 강현구A
 * 
 */
@Getter
@Setter
public class BrcdModel {
	private String spcm_no;
	private String spcm_labl_nm;
	private String pid;
	private String pt_nm;
	private String nm_dscm_dvcd;
	private String brcd_issu_dt;
	private String sex_age;
}