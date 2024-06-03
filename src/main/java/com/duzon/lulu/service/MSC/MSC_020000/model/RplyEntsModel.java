package com.duzon.lulu.service.MSC.MSC_020000.model;

import org.springframework.util.StringUtils;

import com.duzon.lulu.service.MSC.util.MSLC.MSLCUtil;

import lombok.Getter;
import lombok.Setter;

/**
 * 위탁검사회신 모델.
 * @author khgkjg12 강현구A
 * @implNote 2024-03-13
 */
@Getter
public class RplyEntsModel extends ExmnRsltPkModel{
	
	private String ents_exmn_inst_cd;//위탁 기관 코드.

	private String spcm_ents_rply_nmvl; // 수치 결과.
	private String spcm_ents_rply_txt; // 문자 결과.
	private String spcm_ents_rply_rmrk; // 결과 단위.
	private String spcm_ents_rply_rfvl; // 검체 위탁 회신 참고치.
	private String spcm_ents_rply_uplm; // 검체 위탁 회신 상한.
	private String spcm_ents_rply_lwlm; // 검체 위탁 회신 하한.
	private String spcm_ents_rply_dtrm; // 검체 위탁 회신 판정.
	private String spcm_ents_rply_unit; // 검체 위탁 회신 단위.
	@Setter
	private String pid;
	private String spcm_ents_rply_dt = MSLCUtil.getCurrentDate(MSLCUtil.DASH_DATE); // 회신일시.

	/**
	 * 잘린 문자열을 반환.
	 * 
	 * @author khgkjg12 강현구A
	 * @date Dec 8, 2023
	 * @param str   nonnull.
	 * @param limit > 0
	 * @return 잘린 문자열.
	 */
	private String getLimitedString(String str, int limit) {
		return str.length() > limit ? str.substring(0, limit) : str;
	}

	/**
	 * HTTP응답을 파싱하면서 생성.
	 * 
	 * @author khgkjg12 강현구A
	 * @date Mar 11, 2024
	 * @param spcm_no
	 * @param exmn_cd
	 * @param spcm_ents_rply_nmvl
	 * @param spcm_ents_rply_txt
	 * @param spcm_ents_rply_rmrk
	 * @param spcm_ents_rply_rfvl
	 * @param spcm_ents_rply_uplm
	 * @param spcm_ents_rply_lwlm
	 * @param spcm_ents_rply_dtrm
	 * @param spcm_ents_rply_unit
	 */
	public RplyEntsModel(String ents_exmn_inst_cd, String spcm_no, String exmn_cd, String spcm_ents_rply_nmvl, String spcm_ents_rply_txt,
			String spcm_ents_rply_rmrk, String spcm_ents_rply_rfvl, String spcm_ents_rply_uplm,
			String spcm_ents_rply_lwlm, String spcm_ents_rply_dtrm, String spcm_ents_rply_unit) {
		this.ents_exmn_inst_cd = ents_exmn_inst_cd;
		if (spcm_no != null)
			setSpcm_no(getLimitedString(spcm_no, 20));
		if (exmn_cd != null)
			setExmn_cd(getLimitedString(exmn_cd, 20));
		if (spcm_ents_rply_nmvl != null) {
			this.spcm_ents_rply_nmvl = getLimitedString(spcm_ents_rply_nmvl, 4000);
		}
		this.spcm_ents_rply_txt = spcm_ents_rply_txt;
		this.spcm_ents_rply_rmrk = spcm_ents_rply_rmrk;
		this.spcm_ents_rply_rfvl = spcm_ents_rply_rfvl;
		if (spcm_ents_rply_uplm != null) {
			this.spcm_ents_rply_uplm = getLimitedString(spcm_ents_rply_uplm, 20);
		}
		if (spcm_ents_rply_lwlm != null) {
			this.spcm_ents_rply_lwlm = getLimitedString(spcm_ents_rply_lwlm, 20);
		}
		if (spcm_ents_rply_dtrm != null && StringUtils.hasText(spcm_ents_rply_dtrm)) {
			String tempStr = StringUtils.trimWhitespace(spcm_ents_rply_dtrm).toUpperCase();
			if (tempStr.charAt(0) == 'L' || tempStr.charAt(0) == 'H') {
				this.spcm_ents_rply_dtrm = tempStr;
			}
		}
		if (spcm_ents_rply_unit != null)
			this.spcm_ents_rply_unit = getLimitedString(spcm_ents_rply_unit, 100);
	}
}
