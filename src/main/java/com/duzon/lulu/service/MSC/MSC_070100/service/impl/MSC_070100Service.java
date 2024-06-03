package com.duzon.lulu.service.MSC.MSC_070100.service.impl;

import com.duzon.common.model.LuluResult;
import com.duzon.lulu.service.MSC.MSC_070100.controller.MSC_070100Controller;
import com.duzon.lulu.service.MSC.MSC_070100.mapper.MSC_070100Mapper;
import com.duzon.lulu.service.MSC.MSC_070100.service.IMSC_070100Service;
import com.duzon.lulu.service.MSC.util.MSLC.MSLCSessionHelper;
import com.duzon.lulu.service.MSC.util.MSLC.MSLCUtil;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 통합검사결과 API
 * 
 * @author khgkjg12 강현구A
 * @date Dec 15, 2023
 */
@RequiredArgsConstructor
@Service
public class MSC_070100Service implements IMSC_070100Service {

	private final MSC_070100Mapper mapper;
	private final MSLCSessionHelper mslcSessionHelper;

	@Override
	public LuluResult rtrvCardList(Map<String, Object> param) {
		LuluResult luluResult = MSLCUtil.chckEmpty(param, "pid", "date_from", "date_to");
		mslcSessionHelper.setSessionForAll(param);
		luluResult.setResultData(mapper.rtrvCardList(param));
		return luluResult;
	}

	/**
	 * 진단검사결과목록 조회.
	 * 
	 * @author khgkjg12 강현구A
	 * @date Dec 18, 2023
	 * @param param
	 * 
	 *              <pre>
	 *              1. 필수키.
	 *              	- rcpn_no,
	 *              	- cndt_dy
	 *              2. 옵션키
	 *              	- keyword : 검사명 필터.
	 *              </pre>
	 * 
	 * @return
	 * @throws MSLCException
	 * 
	 *                       <pre>
	 *                       1. {@link MSLCError#EMPTY_VALU} 필수값.
	 *                       </pre>
	 * 
	 * @see IMSC_070100Service#rtrvMslRsltList(Map)
	 * @see MSC_070100Controller#rtrvMslRsltList(Map)
	 */
	@Override
	public LuluResult rtrvMslRsltList(Map<String, Object> param) {
		LuluResult luluResult = MSLCUtil.chckEmpty(param, "rcpn_no", "cndt_dy");
		mslcSessionHelper.setSessionForAll(param);
		luluResult.setResultData(mapper.rtrvMslRsltList(param));
		return luluResult;
	}
	
	@Override
	public LuluResult rtrvMsfRsltList(Map<String, Object> param) {
		LuluResult luluResult = MSLCUtil.chckEmpty(param, "rcpn_no", "cndt_dy");
		mslcSessionHelper.setSessionForAll(param);
		luluResult.setResultData(mapper.rtrvMsfRsltList(param));
		return luluResult;
	}
	@Override
	public LuluResult rtrvMsrRsltList(Map<String, Object> param) {
		LuluResult luluResult = MSLCUtil.chckEmpty(param, "rcpn_no", "cndt_dy");
		mslcSessionHelper.setSessionForAll(param);
		luluResult.setResultData(mapper.rtrvMsrRsltList(param));
		return luluResult;
	}

	/**
	 * 내시경검사결과목록 조회
	 * 
	 * @author khgkjg12 강현구A
	 * @date Dec 18, 2023
	 * @param param
	 * 
	 *              <pre>
	 *              1. 필수키.
	 *              	- rcpn_no,
	 *              	- cndt_dy
	 *              2. 옵션키
	 *              	- keyword : 검사명 필터.
	 *              </pre>
	 * 
	 * @return
	 * @throws MSLCException
	 * 
	 *                       <pre>
	 *                       1. {@link MSLCError#EMPTY_VALU} 필수값.
	 *                       </pre>
	 * 
	 * @see IMSC_070100Service#rtrvMseRsltList(Map)
	 * @see MSC_070100Controller#rtrvMseRsltList(Map)
	 */
	@Override
	public LuluResult rtrvMseRsltList(Map<String, Object> param) {
		LuluResult luluResult = MSLCUtil.chckEmpty(param, "rcpn_no", "cndt_dy");
		mslcSessionHelper.setSessionForAll(param);
		luluResult.setResultData(mapper.rtrvMseRsltList(param));
		return luluResult;
	}

	@Override
	public LuluResult rtrvAccRsltList(Map<String, Object> param) {
		LuluResult luluResult = MSLCUtil.chckEmpty(param, "pid", "from", "to");
		mslcSessionHelper.setSessionForAll(param);
		luluResult.setResultData(mapper.rtrvAccRsltList(param));
		return luluResult;
	}
}