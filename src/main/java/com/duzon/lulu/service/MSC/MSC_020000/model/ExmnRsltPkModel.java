package com.duzon.lulu.service.MSC.MSC_020000.model;

import java.util.Map;

import com.duzon.lulu.service.MSC.util.MSLC.MSLCUtil;
import com.duzon.lulu.service.MSC.util.MSLC.PkModel;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 검사결과 PK 모델
 * 
 * @author khgkjg12 강현구A
 * @implNote last modification : 2024-03-13
 * @see WritableExmnRsltModel
 */
@Setter
@Getter
@NoArgsConstructor
public class ExmnRsltPkModel extends PkModel {

	public ExmnRsltPkModel(String spcm_no, String exmn_cd) {
		this.spcm_no = spcm_no;
		this.exmn_cd = exmn_cd;
	}

	private String spcm_no;
	private String exmn_cd;

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof ExmnRsltPkModel))
			return false;
		ExmnRsltPkModel dst = (ExmnRsltPkModel) o;
		if (spcm_no == null || exmn_cd == null || dst.getSpcm_no() == null || dst.getExmn_cd() == null)
			return false;
		return spcm_no.equals(dst.getSpcm_no()) && exmn_cd.equals(dst.getExmn_cd());
	}

	@Override
	public int hashCode() {
		if (spcm_no == null || exmn_cd == null)
			return super.hashCode();
		return new String(spcm_no + "/" + exmn_cd).hashCode();
	}

	@Override
	public void set(Map<String, Object> param) {
		this.spcm_no = param.get("spcm_no").toString();
		this.exmn_cd = param.get("exmn_cd").toString();
	}

	@Override
	public void set(Object... param) {
		this.spcm_no = MSLCUtil.parseString(param[0]);
		this.exmn_cd = MSLCUtil.parseString(param[1]);
	}

	@Override
	public String rtrvModelName() {
		return "RSLT";
	}
}
