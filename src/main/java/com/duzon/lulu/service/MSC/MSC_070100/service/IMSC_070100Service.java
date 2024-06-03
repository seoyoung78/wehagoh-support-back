package com.duzon.lulu.service.MSC.MSC_070100.service;


import java.util.Map;

import com.duzon.common.model.LuluResult;

public interface IMSC_070100Service {

	LuluResult rtrvCardList(Map<String, Object> param);

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
	LuluResult rtrvMslRsltList(Map<String, Object> param);

	LuluResult rtrvMsfRsltList(Map<String, Object> param);

	LuluResult rtrvMsrRsltList(Map<String, Object> param);

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
	LuluResult rtrvMseRsltList(Map<String, Object> param);

	LuluResult rtrvAccRsltList(Map<String, Object> param);

}
