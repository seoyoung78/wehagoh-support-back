package com.duzon.lulu.service.MSC.MSC_020000.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RptgRsltModel extends ExmnRsltPkModel{
	private String exmn_rslt_valu;
	private String cvr_lwlm_valu;
	private String cvr_lwlm_rang_type_cd;
	private String cvr_uplm_valu;
	private String cvr_uplm_rang_type_cd;
}
