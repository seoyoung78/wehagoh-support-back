package com.duzon.lulu.service.MSC.MSC_020000.model;

import java.util.Map;

import com.duzon.lulu.service.MSC.util.MSLC.MSLCUtil;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 진단검사 결과 값 입력 파라미터 모델.
 * 
 * @author 강현구
 * @implNote 최종수정일 : 2024-03-18
 */
@Getter
@Setter
@NoArgsConstructor
public class IptnRsltParamModel extends ExmnRsltPkModel {
	private String exmn_rslt_valu;
	private String dgsg_no;
	private String exmn_eqpm_cd;
	private String exmn_eqpm_rmrk_cnts;
	private String rfvl_lwlm_valu;
	private String rfvl_uplm_valu;
	private String txt_rslt_valu;
	private String exmn_item_rmrk_cnts;
	private String rslt_unit_nm;
	private String rfvl_lwlm_rang_type_cd;
	private String rfvl_uplm_rang_type_cd;
	
	public IptnRsltParamModel(String spcm_no, String exmn_cd) {
		super(spcm_no, exmn_cd);
	}

	@Override
	public void set(Map<String, Object> param) {
		super.set(param);
		exmn_rslt_valu = MSLCUtil.parseString(param.get("exmn_rslt_valu"));
		dgsg_no = MSLCUtil.parseString(param.get("dgsg_no"));
		exmn_eqpm_cd = MSLCUtil.parseString(param.get("eqmp_cd"));
		exmn_eqpm_rmrk_cnts = MSLCUtil.parseString(param.get("eqmp_cd"));
		rfvl_lwlm_valu = MSLCUtil.parseString(param.get("rfvl_lwlm_valu"));
		rfvl_uplm_valu = MSLCUtil.parseString(param.get("rfvl_uplm_valu"));
		txt_rslt_valu = MSLCUtil.parseString(param.get("txt_rslt_valu"));
		rslt_unit_nm = MSLCUtil.parseString(param.get("rslt_unit_nm"));
		exmn_item_rmrk_cnts = MSLCUtil.parseString(param.get("exmn_item_rmrk_cnts"));
	}

	/**
	 * PK와 참고치/단위 필드를 입력받은 lock데이터로 채워넣음.
	 * 
	 * @author khgkjg12 강현구A
	 * @date Apr 16, 2024
	 * @param lock
	 * @return
	 */
	public IptnRsltParamModel setFieldsWithLock(LockSpcmWithEntsRsltModel lock) {
		setRfvl_lwlm_valu(lock.getRfvl_lwlm_valu());
		setRfvl_uplm_valu(lock.getRfvl_uplm_valu());
		setRfvl_lwlm_rang_type_cd(lock.getRfvl_lwlm_rang_type_cd());
		setRfvl_uplm_rang_type_cd(lock.getRfvl_uplm_rang_type_cd());
		setRslt_unit_nm(lock.getRslt_unit_nm());
		return this;
	}
}
