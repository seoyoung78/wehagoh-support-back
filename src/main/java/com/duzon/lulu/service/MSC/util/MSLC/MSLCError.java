package com.duzon.lulu.service.MSC.util.MSLC;

import java.util.ArrayList;
import java.util.List;

import com.duzon.common.model.LuluResult;

import lombok.Getter;

/**
 * MSLC 패키지(진검, 통합검사결과) 에러 열거형. {@link MSLCException},
 * {@link MSLCRuntimeException}에서 사용.
 * 
 * @author khgkjg12 강현구A
 */
@Getter
public enum MSLCError {
	NO_EXTC_KEY("NO_EXTC_KEY", 401), EMPTY_VALU("EMPTY_VALU", 402), RDDC_TRGT("RDDC_TRGT", 403),
	NO_EXTC_SPCM("NO_EXTC_SPCM", 404), NO_EXTC_PRSC("NO_EXTC_PRSC", 404), NO_EXTC_EXMN_CD("NO_EXTC_EXMN_CD", 404),
	NO_EXTC_RSLT("NO_EXTC_RSLT", 404), NO_INT_VALU("NO_INT_VALU", 405), NO_LONG_VALU("NO_LONG_VALU", 406),
	EXCT_IMPB_STAT("EXCT_IMPB_STAT", 407), EMPTY("EMPTY", 408), MODEL_CRTN_ERR("MODEL_CRTN_ERR", 409),
	
	RSLT_VALU_TXT_LNGT_EXCS("RSLT_VALU_TXT_LNGT_EXCS", 431), RSLT_VALU_FIGR_FRMT_ERR("RSLT_VALU_FIGR_FRMT_ERR", 432),
	
	CNCL_EXMN_IPTN_WHLE("CNCL_EXMN_IPTN_WHLE", 441), CNCL_EXMN_RPTG_CMPL("CNCL_EXMN_RPTG_CMPL", 442),
	ALRD_RCPN("ALRD_RCPN", 443), CNCL_EXMN_ENTS_WHLE("위탁 전송된 검사 검사취소", 444), NO_EXMN_SPCM("NO_EXMN_SPCM", 445),
	INVALID_STAT_RSLT("INVALID_STAT_RSLT", 446), SAME_RSLT("동일한 결과값 입력", 447),
	INVALID_STAT_SPCM("INVALID_STAT_SPCM", 448),
	
	ENTS_CONN_FAIL("위탁 연결 실패.", 451), ENTS_RSPS_ERR("위탁 응답 오류", 452),
	ENTS_NOT_USE("미연동 기관", 453),

	MSG_SEND_ERR("메시지 전송 실패", 461),
	
	RDDC_PRSC_CD("RDDC_PRSC_CD", 470), SPCM_CD_DIFF_PRSC("SPCM_CD_DIFF_PRSC", 471),
	ALRD_ISSU_PRSC("ALRD_ISSU_PRSC", 472), NO_WTNG_PRSC("NO_WTNG_PRSC", 473), DC_PRSC("DC_PRSC", 474),
	CNCL_PRSC("CNCL_PRSC", 475), RCPN_NO_DIFF_PRSC("RCPN_NO_DIFF_PRSC", 476), EXRM_DIFF_PRSC("EXRM_DIFF_PRSC", 477),
	EXMN_DATE_DIFF_PRSC("EXMN_DATE_DIFF_PRSC", 478),

	INTIGRITY_ERR("무결성 애러", 490);

	private String message;
	private int code;
	private Object body;

	private MSLCError(String message, int code) {
		this.message = message;
		this.code = code;
	}

	public MSLCError withBody(Object body) {
		this.body = body;
		return this;
	}

	public MSLCError withIdxs(Integer... idxs) {
		ArrayList<Integer> idxList = new ArrayList<>();
		for (int idx : idxs) {
			idxList.add(idx);
		}
		return withBody(idxList);
	}

	public MSLCError withIdxs(List<Integer> idxs) {
		return withBody(idxs);
	}

	public MSLCError withKeys(String... keys) {
		ArrayList<String> keyList = new ArrayList<>();
		for (String key : keys) {
			keyList.add(key);
		}
		return withBody(keyList);
	}

	public MSLCError withKeys(List<String> keys) {
		return withBody(keys);
	}

	public LuluResult write(LuluResult luluResult) {
		luluResult.setResultCode(code);
		luluResult.setResultMsg(message);
		luluResult.setResultData(body);
		return luluResult;
	}

	/**
	 * 400코드가 뜬 ValidationUtil 결과 메시지를 넣어서 없는 키 값을 추출함.
	 * 
	 * @author khgkjg12 강현구A
	 * 
	 * @param result ValidationUtil의 결과 LuluResult객체
	 * @return {@link #NO_EXTC_KEY} 없는 키값이 body에 리스트로 삽입된 형태.
	 */
	public static MSLCError createWithValidationResult(LuluResult result) {
		String message = result.getResultMsg();
		message.substring(message.indexOf('['), message.indexOf(']'));
		return NO_EXTC_KEY.withBody(message.split(", "));
	}

	public LuluResult createLuluResult() {
		return write(new LuluResult());
	}
}
