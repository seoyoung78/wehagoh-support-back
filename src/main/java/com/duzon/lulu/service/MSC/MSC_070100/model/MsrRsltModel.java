package com.duzon.lulu.service.MSC.MSC_070100.model;

import lombok.Getter;
import lombok.Setter;

/**
 * 영상검사결과모델.
 * 
 * @author khgkjg12 강현구A
 * 
 */
@Setter
@Getter
public class MsrRsltModel {
	private String exmn_cd; // 검사코드.
	private String prsc_nm; // 검사명
	private String exmn_rslt_2;
	private String prsc_date; // 처방 일자.
	private String rptg_dy; // 보고일자
	private String exmn_pich_nm; // 검사담당자 이름.
	private String pacs_no; // PACS 일련번호
	private String pacs_co_cd; // PACS 업체코드
}
