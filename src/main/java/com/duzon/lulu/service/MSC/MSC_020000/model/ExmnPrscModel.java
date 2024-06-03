package com.duzon.lulu.service.MSC.MSC_020000.model;

import lombok.Getter;
import lombok.Setter;

/**
 * 진단검사 접수, 검사처방목록, DTL그리드 모델
 * @implNote 최종 수정일 : 2024-03-12
 * @author khgkjg12 강현구A
 */
@Getter
@Setter
public class ExmnPrscModel {
	private String pid;// NN
	private String prsc_date;// NN
	private Long prsc_sqno;// NN
	// 여기까지가 PK
	private String prsc_cd;// NN
	private String prsc_nm;
	private String prsc_dr_id;//NN
	private String prsc_prgr_stat_cd;// NN
	private String entd_exmn_yn;// NN
	private String spcm_cd;// NN
	private String prsc_memo;
	private String spcm_no;
	private String exmn_pich_nm;
	private String dc_rqst_yn;// NN
	private String rcpn_dt;
	private String trms_stat_dvsn;
	private String cndt_dt;
	private String exmn_hope_dt;
	
	//타테이블 데이터
	private String prsc_nots;
	private String spcm_labl_nm;
	private String ctnr_cd;
	private String prsc_dr_nm;
	private String ctnr_labl_nm;
	private String ctnr_colr;
	private String spcm_need_vol;
	private String fix_vol_dvsn_nm;//정량 구분 이름.
	private String spcm_dosg_unit_nm;//검체 용량 단위.
	
	//DC요청에 활용.
	private String prsc_dr_usr_sqno;
}