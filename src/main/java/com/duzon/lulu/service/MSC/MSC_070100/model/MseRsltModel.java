package com.duzon.lulu.service.MSC.MSC_070100.model;

import lombok.Getter;
import lombok.Setter;

/**
 * @author khgkjg12 강현구A
 * 내시경결과 모델.
 */
@Setter
@Getter
public class MseRsltModel {
	private String exmn_cd; // 검사코드.
	private String prsc_nm; // 검사명
	private String prsc_date; // 처방 일자.
	private String rptg_dy; // 보고일자
	private String exmn_pich_nm; // 검사담당자 이름.
	private long prsc_sqno;//detail 호출용.
	private String mdtr_site_cd;
	private String iptn_prsn_nm;//판독자.
}
