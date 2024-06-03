package com.duzon.lulu.service.MSC.MSC_070100.model;

import com.duzon.lulu.service.MSC.MSC_030000.controller.MSC_030000Controller;

import lombok.Getter;
import lombok.Setter;

/**
 * 기능검사결과모델.
 * 
 * @author khgkjg12 강현구A
 * 
 */
@Setter
@Getter
public class MsfRsltModel {
	private String exmn_cd; // 검사코드.
	private String prsc_nm; // 검사명
	private String exmn_rslt_2;
	private String prsc_date; // 처방 일자.
	private String rptg_dy; // 보고일자
	private String exmn_pich_nm; // 검사담당자 이름.
	/**
	 * {@link MSC_030000Controller#detail(java.util.HashMap) 기능검사 파일목록 } 호출용.
	 */
	private String prsc_sqno;
}
