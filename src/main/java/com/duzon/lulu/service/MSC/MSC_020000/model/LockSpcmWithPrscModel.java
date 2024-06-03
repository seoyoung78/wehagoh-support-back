package com.duzon.lulu.service.MSC.MSC_020000.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 검사 전 단계에서 사용하는 검체단위 lock 응답 객체.<br>
 * <br>
 * 최종 수정일 : 2024-03-19
 * 
 * @author 강현구
 */
@Getter
@Setter
@ToString
public class LockSpcmWithPrscModel extends ExmnPrscPkModel {
	private String spcm_prsc_prgr_stat_cd;
	private String spcm_rcpn_cncl_dt;
	private String mdcr_cncl_yn;
	private String dc_yn;
	private String spcm_no;
	private String rcpn_no;
	private String prsc_cd;
	private String ents_exmn_inst_cd;
}
