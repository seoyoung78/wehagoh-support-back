package com.duzon.lulu.service.MSC.MSC_020000.model;

import java.util.Map;
import java.util.Set;

import com.duzon.lulu.service.MSC.util.HealthSessionContext;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EntsReqModel extends HealthSessionContext {
	private String spcm_ents_rqst_dt;
	private String spcm_ents_rqst_dvcd;
	private String spcm_ents_rqst_cnts;
	private String spcm_ents_rsps_cnts;
	private String spcm_ents_rqst_sucs_yn;
	private String ents_exmn_inst_cd;
	private Map<String, Object> spcm_ents_rqst_plain;
	private Map<String, Object> spcm_ents_rsps_plain;
	private Set<RplyEntsModel> rplyList;
}
