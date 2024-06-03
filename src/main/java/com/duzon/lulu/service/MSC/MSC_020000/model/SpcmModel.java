package com.duzon.lulu.service.MSC.MSC_020000.model;

import lombok.Getter;
import lombok.Setter;

/**
 * 임시 검체 PK. 최소한의 그룹화 기준에 따라 임시로 검사처방을 그룹화한 것.
 * 
 * @author khgkjg12 강현구A
 * 
 */
@Getter
@Setter
public class SpcmModel extends SpcmPkModel{
	// PK
	private String spcm_cd; // 검체코드 그룹화 기준. NN
	private String rcpn_no; // 외래 접수번호, 그룹화 기준. NN
	private long hope_exrm_dept_sqno;//NN
	private String exmn_hope_date;// 그룹화 기준. (컬럼없음)
	private String brcd_issu_plce_cd;
	private String pid; // NN G
	private String pt_dvcd; // NN G
	private String slip_cd; //
	private String emrg_yn; // NN
	private String prsc_date; // NN
	private long prsc_sqno;// NN
}
