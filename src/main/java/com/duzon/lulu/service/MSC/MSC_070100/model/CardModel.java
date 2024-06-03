package com.duzon.lulu.service.MSC.MSC_070100.model;

import lombok.Getter;
import lombok.Setter;

/**
 * @author khgkjg12 강현구A 통합검사결과 카드리스트 모델.
 */
@Getter
@Setter
public class CardModel {
	private String rcpn_no;
	private String cndt_dy;
	private String prsc_clsf_nm_list;
	private String prsc_clsf_cd_list;
	private String mdcr_dr_nm;
}
