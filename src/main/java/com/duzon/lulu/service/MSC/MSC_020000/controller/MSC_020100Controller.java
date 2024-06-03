package com.duzon.lulu.service.MSC.MSC_020000.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.duzon.common.model.LuluResult;
import com.duzon.lulu.annotation.HealthRestController;
import com.duzon.lulu.service.MSC.MSC_020000.service.IMSC_020100Service;
import com.duzon.lulu.service.MSC.MSC_020000.service.impl.MSC_020100Service;
import com.duzon.lulu.service.MSC.util.MSLC.MSLCController;
import com.duzon.lulu.service.MSC.util.MSLC.MSLCError;
import com.duzon.lulu.service.MSC.util.MSLC.MSLCParamWrapper;

/*
    진료지원 진단검사
 */
@HealthRestController("/MSC_020100")
public class MSC_020100Controller extends MSLCController {

	@Autowired
	IMSC_020100Service imsc_020100ServiceImpl;

	/**
	 * 진단검사 접수, 바코드 사용여부 조회.
	 * 
	 * @author khgkjg12 강현구A
	 * @date Mar 18, 2024
	 * @return
	 *         <ul>
	 *         LuluResult.
	 *         <li>[CODE] 200 : [DATA] 바코드 사용여부 boolean.</li>
	 *         </ul>
	 * @see MSC_020100Controller#rtrvBrcdUseYn()
	 * @see IMSC_020100Service#rtrvBrcdUseYn()
	 */
	@PostMapping("/rtrvBrcdUseYn")
	public LuluResult rtrvBrcdUseYn() {
		return imsc_020100ServiceImpl.rtrvBrcdUseYn();
	}

	/**
	 * 진단검사 접수, 접수 현황 조회. (MST 그리드)
	 * 
	 * @author khgkjg12 강현구A
	 * @date Mar 18, 2024
	 * @param param
	 *              <ul>
	 *              HashMap<String, Object>
	 *              <li>[필수] exmn_hope_date : 검사 예정일.</li>
	 *              <li>[필수] hope_exrm_dept_sqno_list : 검사실 부서 일련번호 목록.</li>
	 *              <li>[선택] pid : 환자 번호.</li>
	 *              </ul>
	 * @return
	 *         <ul>
	 *         LuluResult
	 *         <li>[CODE] 200 : [DATA] 접수 현황 목록 List<RcpnSttsModel>.</li>
	 *         <li>[CODE] 402 : [MSG] 빈 값. exmn_hope_date. hope_exrm_dept_sqno_list
	 *         </li>
	 *         </ul>
	 * @see MSC_020100Service#rtrvRcpnSttsList(HashMap)
	 * @see IMSC_020100Service#rtrvRcpnSttsList(HashMap)
	 */
	@PostMapping("/rtrvRcpnSttsList")
	public LuluResult rtrvRcpnSttsList(@RequestBody HashMap<String, Object> param) {
		return imsc_020100ServiceImpl.rtrvRcpnSttsList(param);
	}

	/**
	 * 진단검사 접수, 검시 처방 목록 조회. (DTL 그리드)
	 * 
	 * @author khgkjg12 강현구A
	 * @date Mar 18, 2024
	 * @param param
	 *              <ul>
	 *              HashMap<String, Object>
	 *              <li>[필수] exmn_hope_date : 검사 예정일.</li>
	 *              <li>[필수] rcpn_no : 외래 접수 번호.</li>
	 *              <li>[필수] hope_exrm_dept_sqno : 검사실 부서 일련번호.</li>
	 *              </ul>
	 * @return
	 *         <ul>
	 *         LuluResult
	 *         <li>[CODE] 200 : [DATA] 검사 처방 목록 List<ExmnPrscModel>.</li>
	 *         <li>[CODE] 402 : [MSG] 빈 값. exmn_hope_date. rcpn_no.
	 *         hope_exrm_dept_sqno.</li>
	 *         </ul>
	 * @see MSC_020100Service#rtrvExmnPrscList(HashMap)
	 * @see IMSC_020100Service#rtrvExmnPrscList(HashMap)
	 */
	@PostMapping("/rtrvExmnPrscList")
	public LuluResult rtrvExmnPrscList(@RequestBody HashMap<String, Object> param) {
		return imsc_020100ServiceImpl.rtrvExmnPrscList(param);
	}

	/**
	 * 검체에 묶인 검사처방 목록을 조회.
	 * 
	 * @author khgkjg12 강현구A
	 * @date Dec 11, 2023
	 * @param param
	 *              <ul>
	 *              MSLCParamWrapper
	 *              <li>[필수] mslcString : 검체 번호.</li>
	 *              </ul>
	 * @return
	 * @return
	 *         <ul>
	 *         LuluResult
	 *         <li>[CODE] 200 : 검체에 묶인 검사 처방 목록 List<BrcdExmnPrscModel>.</li>
	 *         <li>[CODE] 404 : {@link MSLCError#NO_EXTC_TRGT}<i>"PK" | { "PK1" : "value1",
	 *         "PK2" : "value2",... }</i></li>
	 *         <li>[CODE] 408 : {@link MSLCError#EMPTY}</li>
	 *         <li>[CODE] 443 : {@link MSLCError#ALRD_RCPN}<i>"검체번호"</i></li>
	 *         </ul>
	 * @see MSC_020100Service#rtrvBrcdExmnPrscList(String)
	 * @see IMSC_020100Service#rtrvBrcdExmnPrscList(String)
	 */
	@PostMapping("/rtrvBrcdExmnPrscList")
	public Object rtrvBrcdExmnPrscList(@RequestBody MSLCParamWrapper param) {
		return imsc_020100ServiceImpl.rtrvBrcdExmnPrscList(param.getMslcString());
	}

	/**
	 * 검체 출력용 정보 조회.
	 * 
	 * @author khgkjg12 강현구A
	 * @date Mar 18, 2024
	 * @param param
	 *              <ul>
	 *              MSLCParamWrapper
	 *              <li>mslcString : 검체번호.</li>
	 *              </ul>
	 * @return
	 *         <ul>
	 *         LuluResult
	 *         <li>[CODE] 200 : [DATA] 출력용 검체 정보 BrcdModel.</li>
	 *         <li>[CODE] 402 : [MSG] 빈 값. spcm_no.</li>
	 *         </ul>
	 * @see MSC_020100Service#rtrvBrcd(String)
	 * @see IMSC_020100Service#rtrvBrcd(String)
	 */
	@PostMapping("/rtrvBrcd")
	public LuluResult rtrvBrcd(@RequestBody MSLCParamWrapper param) {
		return imsc_020100ServiceImpl.rtrvBrcd(param.getMslcString());
	}

	/**
	 * 검체 발행.
	 * 
	 * <ul>
	 * <li>접수대기 상태의 검체를 생성한다.</li>
	 * <li>이미 DC/진료취소 처리된 검사처방을 포함할 수 없다.</li>
	 * <li>이미 바코드로 묶인 검사처방에 대한 재 발행은 불가하다.</li>
	 * </ul>
	 * 
	 * 
	 * @author 강현구
	 * @implNote 최종 수정일 : 2024-03-18
	 * @param param 동일 바코드로 발행할 검사처방 목록.
	 *              <ul>
	 *              MSLCParamWrapper
	 *              <li>mslcMapList : List&lt;Map&gt;
	 *              <ul>
	 *              <li>pid : 환자번호 String.</li>
	 *              <li>prsc_date : 처방일자 String.</li>
	 *              <li>prsc_sqno : 처방일련번호 Long.</li>
	 *              </ul>
	 *              </li>
	 *              </ul>
	 * @return
	 *         <ul>
	 *         LuluResult
	 *         <li>[CODE] 200 : [DATA] 발행된 검체 번호 String.</li>
	 *         <li>[CODE] 402 : [MSG] 빈 값. (pid, prsc_date, prsc_sqno)</li>
	 *         <li>[CODE] 404 : [MSG] 대상이 없음.
	 *         <ul>
	 *         <li>해당 처방이 존재하지 않음</li>
	 *         </ul>
	 *         </li>
	 *         <li>[CODE] 406 : [MSG] 8비트 정수 값이 아님. (prsc_sqno)</li>
	 *         <li>[CODE] 407 : [MSG] 실행불가 상태.
	 *         <ul>
	 *         <li>검사처방 모델 생성 인자 애러</li>
	 *         <li>진료 취소된 처방이 존재.</li>
	 *         <li>DC된 처방이 존재.</li>
	 *         <li>존재하지 않는 처방이 포함됨.</li>
	 *         <li>처방 진행 상태 코드가 NULL인 처방이 존재.</li>
	 *         <li>접수대기 중이 아닌 처방이 존재.</li>
	 *         <li>이미 발행된 처방이 존재.</li>
	 *         <li>검체코드가 NULL인 처방이 존재.</li>
	 *         <li>검체코드가 일치하지 않는 처방이 존재.</li>
	 *         <li>접수번호 NULL인 처방이 존재.</li>
	 *         <li>접수번호가 일치하지 않는 처방이 존재.</li>
	 *         <li>검사실 부서번호가 없는 처방이 존재.</li>
	 *         <li>검사실 부서번호가 다른 처방이 존재.</li>
	 *         <li>검사 예정 일자가 없는 처방이 존재.</li>
	 *         <li>검사 예정 일자가 다른 처방이 존재.</li>
	 *         <li>환자 구분코드가 없는 처방이 존재.</li>
	 *         <li>환자 구분코드가 다른 처방이 존재.</li>
	 *         <li>환자번호가 다른 처방이 존재.</li>
	 *         </ul>
	 *         </li>
	 *         </ul>
	 * @see MSC_020100Service#issuBrcd(List)
	 * @see MSC_020100Controller#issuBrcd(MSLCParamWrapper)
	 */
	@PostMapping("/issuBrcd")
	public LuluResult issuBrcd(@RequestBody MSLCParamWrapper param) {
		return imsc_020100ServiceImpl.issuBrcd(param.getMslcMapList());
	}

	/**
	 * 검체 접수.
	 * 
	 * <ul>
	 * <li>검체와 검사처방 테이블의 상태값을 접수 상태로 갱신한다.</li>
	 * <li>한번 접수 취소한 검체를 다시 접수할 수는 없다.</li>
	 * <li>검체 내 모든 오더가 DC/진료취소 된 경우 접수 불가하다.</li>
	 * </ul>
	 * 
	 * @author 강현구
	 * @apiNote 최종 수정일 : 2024-03-18
	 * @param param 접수할 검체 목록.
	 *              <ul>
	 *              MSLCParamWrapper
	 *              <li>mslcStringList : List&lt;String&gt;.</li>
	 *              </ul>
	 * @return
	 *         <li>LuluResult
	 *         <li>[CODE] 200 : [DATA] NULL.</li>
	 *         <li>[CODE] 402 : [MSG] 빈 값.</li>
	 *         <li>[CODE] 404 : [MSG] 대상이 없음.
	 *         <ul>
	 *         <li>존재하지 않는 검체가 포함됨.</li>
	 *         </ul>
	 *         </li>
	 *         <li>[CODE] 407 : [MSG] 실행 불가 상태.
	 *         <ul>
	 *         <li>모델 생성 인자가 잘못됨.</li>
	 *         <li>존재하지 않는 검체가 포함됨.</li>
	 *         <li>모든 검사처방이 DC/진료취소된 검체가 존재.{검체 번호}</li>
	 *         <li>접수 대기 상태가 아닌 검체가 존재.{검체 번호}</li>
	 *         <li>접수 취소된 검체가 존재.{검체 번호}</li>
	 *         </ul>
	 *         </li>
	 * @see MSC_020100Service#rcpnBrcd(List)
	 * @see MSC_020100Controller#rcpnBrcd(MSLCParamWrapper)
	 */
	@PostMapping("/rcpnBrcd")
	public LuluResult rcpnBrcd(@RequestBody MSLCParamWrapper param) {
		return imsc_020100ServiceImpl.rcpnBrcd(param.getMslcStringList());
	}

	/**
	 * 검체 발행 후 접수까지 한번에 진행.
	 * 
	 * <ul>
	 * <li>접수대기 상태의 검사처방들로 검체를 발행 및 접수를 수행.</li>
	 * <li>이미 DC/진료취소 처리된 검사처방을 포함할 수 없다.</li>
	 * <li>이미 다른 바코드로 발행된 검사처방은 사용 불가하다.</li>
	 * </ul>
	 * 
	 * 
	 * @author 강현구
	 * @param param
	 *              <ul>
	 *              MSLCParamWrapper
	 *              <li>mslcMapList : List&lt;Map&gt;.
	 *              <ul>
	 *              <li>pid : 환자번호 String.</li>
	 *              <li>prsc_date : 처방일자 String.</li>
	 *              <li>prsc_sqno : 처방일련번호 Long.</li>
	 *              </ul>
	 *              </li>
	 *              </ul>
	 * @return
	 *         <ul>
	 *         LuluResult
	 *         <li>[CODE] 200 : [DATA] 발행된 검체 번호 String.</li>
	 *         <li>[CODE] 402 : [MSG] 빈 값. (pid, prsc_date, prsc_sqno)</li>
	 *         <li>[CODE] 404 : [MSG] 대상이 없음.
	 *         <ul>
	 *         <li>해당 처방이 존재하지 않음</li>
	 *         </ul>
	 *         </li>
	 *         <li>[CODE] 406 : [MSG] 8비트 정수 값이 아님. (prsc_sqno)</li>
	 *         <li>[CODE] 407 : [MSG] 실행불가 상태.
	 *         <ul>
	 *         <li>검사처방 모델 생성 인자 애러</li>
	 *         <li>진료 취소된 처방이 존재.</li>
	 *         <li>DC된 처방이 존재.</li>
	 *         <li>존재하지 않는 처방이 포함됨.</li>
	 *         <li>처방 진행 상태 코드가 NULL인 처방이 존재.</li>
	 *         <li>접수대기 중이 아닌 처방이 존재.</li>
	 *         <li>이미 발행된 처방이 존재.</li>
	 *         <li>검체코드가 NULL인 처방이 존재.</li>
	 *         <li>검체코드가 일치하지 않는 처방이 존재.</li>
	 *         <li>접수번호 NULL인 처방이 존재.</li>
	 *         <li>접수번호가 일치하지 않는 처방이 존재.</li>
	 *         <li>검사실 부서번호가 없는 처방이 존재.</li>
	 *         <li>검사실 부서번호가 다른 처방이 존재.</li>
	 *         <li>검사 예정 일자가 없는 처방이 존재.</li>
	 *         <li>검사 예정 일자가 다른 처방이 존재.</li>
	 *         <li>환자 구분코드가 없는 처방이 존재.</li>
	 *         <li>환자 구분코드가 다른 처방이 존재.</li>
	 *         <li>환자번호가 다른 처방이 존재.</li>
	 *         </ul>
	 *         </li>
	 *         </ul>
	 * @see MSC_020100Service#issuBrcdAndRcpn(List)
	 * @see IMSC_020100Service#issuBrcdAndRcpn(MSLCParamWrapper)
	 */
	@PostMapping("/issuBrcdAndRcpn")
	public LuluResult issuBrcdAndRcpn(@RequestBody MSLCParamWrapper param) {
		return imsc_020100ServiceImpl.issuBrcdAndRcpn(param.getMslcMapList());
	}

	/**
	 * 검체 접수 취소.
	 * 
	 * <ul>
	 * <li>검체 처방 내역과 해당 검체를 가진 검사처방 레코드들의 상태값을 접수 대기 상태로 갱신한다.</li>
	 * <li>한번 접수 취소한 검체는 다시 접수할 수는 없다.</li>
	 * <li>접수 취소시 각 검사처방에 할당된 검체번호가 삭제된다.</li>
	 * <li>검체 내 모든 오더가 DC된 경우에도 접수 취소 가능하다. 그러나 이 경우 검사처방 테이블에 대한 상태값 갱신은 이루어지
	 * 않는다.</li>
	 * </ul>
	 * 
	 * @author 강현구.
	 * @apiNote 최종 수정일 : 2024-03-18
	 * @param param
	 *              <ul>
	 *              MSLCParamWrapper
	 *              <li>mslcStringList : List&lt;String&gt;.</li>
	 *              </ul>
	 * 
	 * @return
	 *         <li>LuluResult
	 *         <li>[CODE] 200 : [DATA] NULL.</li>
	 *         <li>[CODE] 402 : [MSG] 빈 값.</li>
	 *         <li>[CODE] 404 : [MSG] 대상이 없음.
	 *         <ul>
	 *         <li>존재하지 않는 검체가 포함됨.</li>
	 *         </ul>
	 *         </li>
	 *         <li>[CODE] 407 : [MSG] 실행 불가 상태.
	 *         <ul>
	 *         <li>모델 생성 인자가 잘못됨.</li>
	 *         <li>접수 상태가 아닌 검체가 존재.{검체 번호}</li>
	 *         </ul>
	 *         </li>
	 * @see MSC_020100Service#cnclRcpnBrcd(List)
	 * @see IMSC_020100Service#cnclRcpnBrcd(List)
	 */
	@PostMapping("/cnclRcpnBrcd")
	public LuluResult cnclRcpnBrcd(@RequestBody MSLCParamWrapper param) {
		return imsc_020100ServiceImpl.cnclRcpnBrcd(param.getMslcStringList());
	}

	/**
	 * 검체 접수.
	 * 
	 * <ul>
	 * <li>검체와 검사처방 테이블의 상태값을 접수 상태로 갱신한다.</li>
	 * <li>한번 접수 취소한 검체를 다시 접수할 수는 없다.</li>
	 * <li>검체 내 모든 오더가 DC/진료취소 된 경우 접수 불가하다.</li>
	 * </ul>
	 * 
	 * @author 강현구
	 * @apiNote 최종 수정일 : 2024-03-18
	 * @param param 접수할 검체 목록 List&lt;String&lt;.
	 * @return
	 *         <li>LuluResult
	 *         <li>[CODE] 200 : [DATA] NULL.</li>
	 *         <li>[CODE] 402 : [MSG] 빈 값.</li>
	 *         <li>[CODE] 404 : [MSG] 대상이 없음.
	 *         <ul>
	 *         <li>존재하지 않는 검체가 포함됨.</li>
	 *         </ul>
	 *         </li>
	 *         <li>[CODE] 407 : [MSG] 실행 불가 상태.
	 *         <ul>
	 *         <li>모델 생성 인자가 잘못됨.</li>
	 *         <li>존재하지 않는 검체가 포함됨.</li>
	 *         <li>모든 검사처방이 DC/진료취소된 검체가 존재.{검체 번호}</li>
	 *         <li>접수 대기 상태가 아닌 검체가 존재.{검체 번호}</li>
	 *         <li>접수 취소된 검체가 존재.{검체 번호}</li>
	 *         </ul>
	 *         </li>
	 * @see MSC_020100Service#exmnBrcd(List)
	 * @see IMSC_020100Service#exmnBrcd(MSLCParamWrapper)
	 */
	@PostMapping("/exmnBrcd")
	public LuluResult exmnBrcd(@RequestBody MSLCParamWrapper param) {
		return imsc_020100ServiceImpl.exmnBrcd(param.getMslcStringList());
	}

	/**
	 * 검체 검사 취소.
	 * 
	 * <ul>
	 * <li>이미 중간보고 상태에 들어간 검사의 경우 취소 불가</li>
	 * <li>위탁 검사의 경우 미전송 상태에서만 취소 가능</li></li>
	 * <li>전부 DC/진료취소된 검체도 검사 취소 가능 다만, 상태 갱신할 검사처방은 없음.</li>
	 * </ul>
	 * 
	 * @author 강현구.
	 * @param param 
	 *              <ul>
	 *              MSLCParamWrapper
	 *              <li>[필수] mslcStringList : 검사취소할 검체 번호 목록 List&lt;String&gt;</li>
	 *              </ul>
	 
	 * @return
	 *         <ul>
	 *         LuluResult
	 *         <li>[CODE] 200 : [DATA] NULL.</li>
	 *         <li>[CODE] 402 : [MSG] 빈 값.</li>
	 *         <li>[CODE] 404 : [MSG] 대상이 없음.
	 *         <ul>
	 *         <li>검체가 없거나, 검사중이 아닙니다.</li>
	 *         </ul>
	 *         </li>
	 *         <li>[CODE] 441 : [MSG] 판독중 검사 검사취소.</li>
	 *         <li>[CODE] 442 : [MSG] 보고 완료 검사 검사취소.</li>
	 *         <li>[CODE] 444 : [MSG] 위탁 전송된 검사 검사취소.</li>
	 *         </ul>
	 * @see MSC_020100Service#cnclExmnBrcd(List)
	 * @see IMSC_020100Service#cnclExmnBrcd(List)
	 */
	@PostMapping("/cnclExmnBrcd")
	public LuluResult cnclExmnBrcd(@RequestBody MSLCParamWrapper param) {
		return imsc_020100ServiceImpl.cnclExmnBrcd(param.getMslcStringList());
	}

}
