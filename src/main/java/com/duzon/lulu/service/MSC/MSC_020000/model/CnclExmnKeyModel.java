package com.duzon.lulu.service.MSC.MSC_020000.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CnclExmnKeyModel {
	String pid;
	String prsc_date;
	long prsc_sqno;
	String hashExmnPrsc;
	String hashExmnRslt;
	String spcm_no;
	long rslt_sqno;
	String spcm_ents_prgr_stat_cd;
	String rslt_prgr_stat_cd;
}
