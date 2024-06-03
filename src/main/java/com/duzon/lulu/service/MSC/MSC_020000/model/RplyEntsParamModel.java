package com.duzon.lulu.service.MSC.MSC_020000.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RplyEntsParamModel extends RplyEntsModel {
	public RplyEntsParamModel(RplyEntsModel rplyEntsModel
			, long spcm_ents_rqst_sqno, 
			String spcm_ents_trms_dt, String spcm_ents_trms_usid) {
		super(rplyEntsModel.getEnts_exmn_inst_cd(), rplyEntsModel.getSpcm_no(), rplyEntsModel.getExmn_cd(),
				rplyEntsModel.getSpcm_ents_rply_nmvl(), rplyEntsModel.getSpcm_ents_rply_txt(),
				rplyEntsModel.getSpcm_ents_rply_rmrk(), rplyEntsModel.getSpcm_ents_rply_rfvl(),
				rplyEntsModel.getSpcm_ents_rply_uplm(), rplyEntsModel.getSpcm_ents_rply_lwlm(),
				rplyEntsModel.getSpcm_ents_rply_dtrm(), rplyEntsModel.getSpcm_ents_rply_unit());
		this.spcm_ents_rqst_sqno = spcm_ents_rqst_sqno;
		this.spcm_ents_trms_dt = spcm_ents_trms_dt;
		this.spcm_ents_trms_usid = spcm_ents_trms_usid;
	}

	private long spcm_ents_rqst_sqno; // 검체 위탁 요청 일련번호.
	private String spcm_ents_trms_dt;
	private String spcm_ents_trms_usid;
}
