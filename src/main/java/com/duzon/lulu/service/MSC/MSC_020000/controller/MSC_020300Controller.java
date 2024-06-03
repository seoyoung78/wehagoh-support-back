package com.duzon.lulu.service.MSC.MSC_020000.controller;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.duzon.common.model.LuluResult;
import com.duzon.lulu.annotation.HealthRestController;
import com.duzon.lulu.service.MSC.MSC_020000.service.IMSC_020300Service;
import com.duzon.lulu.service.MSC.MSC_020000.service.impl.MSC_020300Service;
import com.duzon.lulu.service.MSC.util.MSLC.MSLCController;
import com.duzon.lulu.service.MSC.util.MSLC.MSLCError;
import com.duzon.lulu.service.MSC.util.MSLC.MSLCParamWrapper;

@HealthRestController("/MSC_020300")
public class MSC_020300Controller extends MSLCController {

	@Autowired
	IMSC_020300Service service;

	/* 검사접수목록 조회 */
	@PostMapping("/rtrvRsltSttsList")
	public LuluResult rtrvExmnRcpnList(@RequestBody HashMap<String, Object> param) {
		return service.rtrvRsltSttsList(param);
	}

	/* 검사결과 목록 조회 */
	@PostMapping("/rtrvExmnRsltList")
	public LuluResult rtrvExmnRsltList(@RequestBody MSLCParamWrapper param) {
		return service.rtrvExmnRsltList(param.getMslcStringList());
	}

	/**
	 * 검사 결과값 입력.
	 * 
	 * 검사결과판독, 검사 결과에 결과값을 입력.
	 * 
	 * @author 강현구
	 * @param param
	 *              <ul>
	 *              ArrayList&lt;Map&lt;String, Object&gt;&gt;
	 *              <li>[필수] exmn_cd : 검사코드.</li>
	 *              <li>[필수] spcm_no : 검체번호.</li>
	 *              <li>[선택] exmn_rslt_valu : 결과값.</li>
	 *              <li>[선택] dgsg_no : 전자서명 값.</li>
	 *              <li>[선택] exmn_eqpm_cd : 장비 코드.</li>
	 *              <li>[선택] exmn_eqpm_rmrk_cnts : 장비 비고 내용.</li>
	 *              </ul>
	 * @return
	 *         <ul>
	 *         LuluResult
	 *         <li>[CODE] 200</li>
	 *         <li>[CODE] 402 : {@link MSLCError#EMPTY_VALU} <i>["spcm_no",
	 *         "exmn_cd"]</i></li>
	 *         <li>[CODE] 403 : {@link MSLCError#RDDC_TRGT}<i>RSLT</i></li>
	 *         <li>[CODE] 404 : {@link MSLCError#NO_EXCT_CD}<i>"검사코드"</i></li>
	 *         <li>[CODE] 408 : {@link MSLCError#EMPTY}</li>
	 *         <li>[CODE] 409 : {@link MSLCError#MODEL_CRTN_ERR}</li>
	 *         <li>[CODE] 431 :
	 *         {@link MSLCError#RSLT_VALU_TXT_LNGT_EXCS}<i>"검체번호":"검사코드"</i></li>
	 *         <li>[CODE] 432 :
	 *         {@link MSLCError#RSLT_VALU_FIGR_FRMT_ERR}<i>"검체번호":"검사코드"</i></li>
	 *         </ul>
	 *         
	 * @see MSC_020300Service#iptnRslt(java.util.List)
	 * @see IMSC_020300Service#iptnRslt(java.util.List)
	 */
	@PostMapping("/iptnRslt")
	public LuluResult iptnRslt(@RequestBody MSLCParamWrapper param) {
		return service.iptnRslt(param.getMslcMapList());
	}

	@PostMapping("/cnclIptnRslt")
	public LuluResult cnclIptnRslt(@RequestBody MSLCParamWrapper param) {
		return service.cnclIptnRslt(param.getMslcMapList());
	}

	@PostMapping("/rptgRslt")
	public LuluResult rptgExmnRslt(@RequestBody MSLCParamWrapper param) {
		return service.rptgRslt(param.getMslcMapList());
	}

	@PostMapping("/cnclRptgRslt")
	public LuluResult cnclRptgExmnRslt(@RequestBody MSLCParamWrapper param) {
		return service.cnclRptgRslt(param.getMslcMapList());
	}

	@PostMapping("/rtrvExmnRsltHstrList")
	public LuluResult rtrvExmnRsltHstrList(@RequestBody MSLCParamWrapper param) {
		return service.rtrvExmnRsltHstrList(param.getMslcMap());
	}
	
	/**
	 * CVR 알림 요청을 위한 파라미터 조회
	 *
	 * @author khgkjg12 강현구A
	 * @date Apr 17, 2024
	 * @param param mslcMapList : 진단검사결과 PK 목록.
	 * @return CVR알림 요청필요 인자.
	 */
	@PostMapping("/rtrvCvrNotiDetailList")
	public LuluResult rtrvCvrNotiDetailList(@RequestBody MSLCParamWrapper param) {
		return service.rtrvCvrNotiDetailList(param.getMslcMapList());
	}
	
}