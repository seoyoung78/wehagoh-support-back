package com.duzon.lulu.service.MSC.MSC_070100.controller;

import com.duzon.common.model.LuluResult;
import com.duzon.lulu.annotation.HealthRestController;
import com.duzon.lulu.service.MSC.MSC_070100.service.IMSC_070100Service;
import com.duzon.lulu.service.MSC.util.MSLC.MSLCController;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@HealthRestController("/MSC_070100")
public class MSC_070100Controller extends MSLCController {
	@Autowired
	private IMSC_070100Service service;

	@PostMapping("/rtrvCardList")
	public LuluResult rtrvCardList(@RequestBody Map<String, Object> param) {
		return service.rtrvCardList(param);
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
	 *              	- mdcr_dr_empl_sqno :진료의 직원 일련번호.
	 *              2. 옵션키
	 *              	- keyword : 검사명 필터.
	 *              </pre>
	 * 
	 * @return
	 * 
	 *         <pre>
	 *                       - 200 : 진단검사결과목록.
	 *                       - 402 : 필수값 없음.
	 *                       - 500 : 기타 오류.
	 *         </pre>
	 * 
	 * @throws MSLCException
	 */
	@PostMapping("/rtrvMslRsltList")
	public LuluResult rtrvMslRsltList(@RequestBody Map<String, Object> param) {
		return service.rtrvMslRsltList(param);
	}

	/**
	 * 기능검사결과목록 조회.
	 * 
	 * @author khgkjg12 강현구A
	 * @date Dec 18, 2023
	 * @param param
	 * 
	 *              <pre>
	 *              1. 필수키.
	 *              	- rcpn_no,
	 *              	- cndt_dy
	 *              	- mdcr_dr_empl_sqno :진료의 직원 일련번호.
	 *              2. 옵션키
	 *              	- keyword : 검사명 필터.
	 *              </pre>
	 * 
	 * @return
	 * 
	 *         <pre>
	 *                       - 200 : 기능검사결과목록.
	 *                       - 402 : 필수값 없음.
	 *                       - 500 : 기타 오류.
	 *         </pre>
	 * 
	 * @throws MSLCException
	 */
	@PostMapping("/rtrvMsfRsltList")
	public LuluResult rtrvMsfRsltList(@RequestBody Map<String, Object> param) {
		return service.rtrvMsfRsltList(param);
	}

	/**
	 * 영상검사결과목록 조회.
	 * 
	 * @author khgkjg12 강현구A
	 * @date Dec 18, 2023
	 * @param param
	 * 
	 *              <pre>
	 *              1. 필수키.
	 *              	- rcpn_no,
	 *              	- cndt_dy
	 *              	- mdcr_dr_empl_sqno :진료의 직원 일련번호.
	 *              2. 옵션키
	 *              	- keyword : 검사명 필터.
	 *              </pre>
	 * 
	 * @return
	 * 
	 *         <pre>
	 *                       - 200 : 영상검사결과목록.
	 *                       - 402 : 필수값 없음.
	 *                       - 500 : 기타 오류.
	 *         </pre>
	 * 
	 * @throws MSLCException
	 */
	@PostMapping("/rtrvMsrRsltList")
	public LuluResult rtrvMsrRsltList(@RequestBody Map<String, Object> param) {
		return service.rtrvMsrRsltList(param);
	}

	/**
	 * 내시경검사결과목록 조회.
	 * 
	 * @author khgkjg12 강현구A
	 * @date Dec 18, 2023
	 * @param param
	 * 
	 *              <pre>
	 *              1. 필수키.
	 *              	- rcpn_no,
	 *              	- cndt_dy
	 *              	- mdcr_dr_empl_sqno :진료의 직원 일련번호.
	 *              2. 옵션키
	 *              	- keyword : 검사명 필터.
	 *              </pre>
	 * 
	 * @return
	 * 
	 *         <pre>
	 *                       - 200 : 내시경검사결과목록.
	 *                       - 402 : 필수값 없음.
	 *                       - 500 : 기타 오류.
	 *         </pre>
	 * 
	 * @throws MSLCException
	 */
	@PostMapping("/rtrvMseRsltList")
	public LuluResult rtrvMseRsltList(@RequestBody Map<String, Object> param) {
		return service.rtrvMseRsltList(param);
	}

	/**
	 * 누적검사결과목록 조회.
	 * 
	 * @author khgkjg12 강현구A
	 * @date Dec 18, 2023
	 * @param param
	 * 
	 *              <pre>
	 *              1. 필수키.
	 *              	- pid,
	 *              	- from
	 *              	- to
	 *              2. 옵션키
	 *              	- keyword : 검사명 필터.
	 *              </pre>
	 * 
	 * @return
	 * 
	 *         <pre>
	 *                       - 200 : 누적검사결과목록.
	 *                       - 402 : 필수값 없음.
	 *                       - 500 : 기타 오류.
	 *         </pre>
	 * 
	 * @throws MSLCException
	 */
	@PostMapping("/rtrvAccRsltList")
	public LuluResult rtrvAccRsltList(@RequestBody Map<String, Object> param) {
		return service.rtrvAccRsltList(param);

	}
}