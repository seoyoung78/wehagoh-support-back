package com.duzon.lulu.service.MSC.common.model.Common;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class GetPrscListResult {
	private String prsc_date;
	private String prsc_sqno;
	private String sort_sqno;
	private String prsc_cd;
	private String prsc_nm;
	private String user_nm;
	private String dc_yn;
	private String dc_resn;
	private String dc_resn_cd;
	private String prsc_pay_dvcd;
	private String rcpt_stat_cd;
}
