package com.duzon.lulu.service.MSC.MSC_020000.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TrmsAndCnclSuccessModel extends ExmnRsltPkModel{
	private String ents_exmn_inst_cd;
	private long spcm_ents_rqst_sqno;
	
	public TrmsAndCnclSuccessModel(EntsApiParamModel apiParam, long seqNo) {
		setSpcm_no(apiParam.getSpcm_no());
		setExmn_cd(apiParam.getExmn_cd());
		this.spcm_ents_rqst_sqno = seqNo;
		this.ents_exmn_inst_cd = apiParam.getEnts_exmn_inst_cd();
	}
}
