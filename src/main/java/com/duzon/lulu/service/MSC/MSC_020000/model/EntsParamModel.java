package com.duzon.lulu.service.MSC.MSC_020000.model;

import lombok.Getter;

@Getter
public class EntsParamModel extends ExmnRsltPkModel{
	private long spcm_ents_rqst_sqno;
	private RplyEntsModel rplyEntsModel;
	
	public static EntsParamModel createTrmsAndTrmsCncl(ExmnRsltPkModel pk, long spcm_ents_rqst_sqno) {
		EntsParamModel result = new EntsParamModel();
		result.setSpcm_no(pk.getSpcm_no());
		result.setExmn_cd(pk.getExmn_cd());
		result.spcm_ents_rqst_sqno = spcm_ents_rqst_sqno;
		return result;
	}
	
	public static EntsParamModel createRply(ExmnRsltPkModel pk, long spcm_ents_rqst_sqno, RplyEntsModel rplyEntsModel) {
		EntsParamModel result = new EntsParamModel();
		result.setSpcm_no(pk.getSpcm_no());
		result.setExmn_cd(pk.getExmn_cd());
		result.spcm_ents_rqst_sqno = spcm_ents_rqst_sqno;
		result.rplyEntsModel = rplyEntsModel;
		return result;
	}
}
