package com.duzon.lulu.service.MSC.MSC_020000.model;

import java.util.Set;

import com.duzon.lulu.service.MSC.util.HealthSessionContext;

import lombok.Getter;

/**
 * 위탁 API 파라미터 생성용 모델.
 * 
 * @author khgkjg12 강현구A
 * @date Mar 14, 2024
 * @param spcm_no
 * @param exmn_cd
 * @param rrnKey
 */
@Getter
public class CreateEntsApiParamModel {
	String rrnKey;
	Set<ExmnRsltPkModel> exmnRsltSet;

	public CreateEntsApiParamModel(Set<ExmnRsltPkModel> exmnRsltSet, HealthSessionContext session) {
		this.rrnKey = session.getCrypt_key_data((long) session.getCompany_no());
		this.exmnRsltSet = exmnRsltSet;
	}
}
