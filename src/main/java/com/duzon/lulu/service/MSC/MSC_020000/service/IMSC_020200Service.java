package com.duzon.lulu.service.MSC.MSC_020000.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.duzon.common.model.LuluResult;

public interface IMSC_020200Service {

	/**
	 * 위탁기관목록 조회
	 * 
	 * @author khgkjg12 강현구A
	 * 
	 * @return 위탁기관목록
	 */
	LuluResult rtrvEntsInstList();

	LuluResult rtrvEntsExmnPrscList(HashMap<String, Object> param);

	LuluResult trmsEntsExmnPrsc(List<? extends Map<String, Object>> param);

	/**
	 * 위탁검사처방 전송 취소
	 * 
	 * @author khgkjg12 강현구A
	 * @param param 취소할 검사처방 목록<br>
	 * 
	 *              <pre>
	 *              1. 필수값
	 *              	- pid : 검사처방 환자.
	 *              	- prsc_date : 검사처방 처방일자.
	 *              	- prsc_sqno : 검사처방 처방일련번호.
	 *              	- ents_exmn_inst_cd : 취소할 위탁기관 코드.
	 *              </pre>
	 * 
	 * @throws MSLCException
	 * 
	 *                       <pre>
	 *                       1. {@link MSLCError#NO_EXTC_TRGT} 검사처방이 존재하지 않을 때.
	 *                       2. {@link MSLCError#RDDC_TRGT}
	 *                       3. {@link MSLCError#ENTD_CONN_ERR} SCL 연결 실패.
	 *                       4/ {@link MSLCError#ENTS_RSPS_ERR} SCL 응답 오류.
	 *                       </pre>
	 * 
	 * @see IMSC_020200Service#cnclTrmsEntsExmnPrsc(List)
	 * @see MSC_020200Controller#cnclTrmsEntsExmnPrsc(List)
	 */
	LuluResult cnclTrmsEntsExmnPrsc(List<? extends Map<String, Object>> param);

	/**
	 * 위탁검사처방 회신
	 * 
	 * @author khgkjg12 강현구A
	 * @return 위탁검사처방회신 목록
	 * @throws MSLCException
	 * 
	 *                       <pre>
	 *                       {@link MSLCError#ENTS_CONN_FAIL} 연결 실패.
	 *                       {@link MSLCError#ENTS_RSPS_ERR} 응답 애러.
	 *                       </pre>
	 * 
	 * @see MSC_020200Controller#rplyEntsExmnPrsc()
	 * @see IMSC_020200Service#rplyEntsExmnPrsc()
	 */
	LuluResult rplyEntsExmnPrsc();

}
