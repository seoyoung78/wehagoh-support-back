package com.duzon.lulu.service.MSC.MSC_020000.model;

import lombok.Getter;
import lombok.Setter;

/**
 * lockExmnPrsc 쿼리 응답 모델.<br><br>
 * 검체 생성을 위한 인자를 포함함.<br>
 * 최종 수정일 : 2024-03-19<br>
 * 
 * @author 강현구
 * 
 */
@Setter
@Getter
public class LockPrscModel extends ExmnPrscPkModel{
	private String spcm_no;
	private String spcm_cd;
	private String rcpn_no;
	private Long hope_exrm_dept_sqno;
	private String exmn_hope_date;
	private String pt_dvcd;
	private String slip_cd;
	private String emrg_pt_yn;//외부컬럼. 응급환자 여부.
	private String prsc_prgr_stat_cd;//처방 진행 상태코드.
	private String dc_yn;//dc 여부.
	private String mdcr_cncl_yn;//처방 취소 여부.
	private String ents_exmn_inst_cd;//위탁검사기관코드.
	private String prsc_cd;
}
