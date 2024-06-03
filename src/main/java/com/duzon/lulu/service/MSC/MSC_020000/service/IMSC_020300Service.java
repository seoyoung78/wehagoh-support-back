package com.duzon.lulu.service.MSC.MSC_020000.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.duzon.common.model.LuluResult;
import com.duzon.lulu.service.MSC.MSC_020000.controller.MSC_020300Controller;
import com.duzon.lulu.service.MSC.MSC_020000.service.impl.MSC_020300Service;
import com.duzon.lulu.service.MSC.util.MSLC.MSLCError;

public interface IMSC_020300Service {

	/**
	 * 진단검사 결과, 검사 현황 조회 MST그리드.
	 * 
	 * @author 강현구
	 * @param param
	 * <ul>HashMap&lt;String, Object&gt;
	 * <li>[필수] cndt_dy_to : 검사일 시작.</li>
	 * <li>[필수] cndt_dy_from : 검사일 끝.</li>
	 * <li>[필수] hope_exrm_dept_sqno_list : 검사실 부서일련 목록.</li>
	 * <li>[선택] pid : 환자번호.</li>
	 * </ul>
	 * @return
	 * <ul>LuluResult
	 * <li>[CODE] 200 : [DATA] 검사 현황 목록 List&lt;RsltSttsModel&gt;.</li>
	 * <li>[CODE] 402 : [MSG] 빈 값.
	 * <ul>
	 * <li>cndt_dy_to</li>
	 * <li>cndt_dy_from</li>
	 * <li>hope_exrm_dept_sqno_list</li>
	 * </ul>
	 * </li>
	 * </ul>
	 */
	LuluResult rtrvRsltSttsList(HashMap<String, Object> param);

	/**
	 * 진단검사 결과, 결과 목록 조회 DTL그리드.
	 * 
	 * @author 강현구
	 * @param param 검체 번호 목록 List&lt;String&gt;
	 * @return
	 *         <ul>
	 *         LuluResult
	 *         <li>[CODE] 200 : [DATA] 검사 현황 목록 List&lt;ExmnRsltModel&gt;.</li>
	 *         <li>[CODE] 402 : [MSG] 빈 값.</li>
	 *         </ul>
	 */
	LuluResult rtrvExmnRsltList(List<String> param);

	/**
	 * 검사결과판독, 검사 결과에 결과값을 입력.
	 * 
	 * @author 강현구
	 * @param param
	 *              <ul>
	 *              List&lt;Map&lt;String, Object&gt;&gt;
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
	 *         <li>[CODE] 408 : {@link MSLCError#EMPTY}</li>
	 *         <li>[CODE] 404 : {@link MSLCError#NO_EXCT_CD}<i>"검사코드"</i></li>
	 *         <li>[CODE] 403 : {@link MSLCError#RDDC_TRGT}<i>RSLT</i></li>
	 *         <li>[CODE] 409 : {@link MSLCError#MODEL_CRTN_ERR}</li>
	 *         <li>[CODE] 431 :
	 *         {@link MSLCError#RSLT_VALU_TXT_LNGT_EXCS}<i>"검체번호":"검사코드"</i></li>
	 *         <li>[CODE] 432 :
	 *         {@link MSLCError#RSLT_VALU_FIGR_FRMT_ERR}<i>"검체번호":"검사코드"</i></li>
	 *         </ul>
	 * @see MSC_020300Service#iptnRslt(List)
	 * @see MSC_020300Controller#iptnRslt(java.util.ArrayList)
	 */
	LuluResult iptnRslt(List<? extends Map<String, Object>> param);

	LuluResult cnclIptnRslt(List<? extends Map<String, Object>> param);

	/**
	 * 검사결과 보고
	 * 
	 * @author khgkjg12 강현구A
	 * @param param         맵 목록
	 * 
	 *                      <pre>
	 *              1. 필수키
	 *              	- last_updt_dt : 마지막 갱신 일시.
	 *              	- spcm_no : 접수 번호.
	 *              	- exmn_cd : 희망 검사 일자.
	 *              	- exmn_rslt_valu : 기존 검사결과 값.
	 *              	- txt_rslt_valu : 기존 장문 결과 값.
	 *              	- rslt_prgt_stat_cd : 결과 진행 상태 코드.
	 *                      </pre>
	 * 
	 * @param authorization
	 * 
	 * 
	 * 
	 * @
	 * 
	 *   <pre>
	 *                       1. {@link MSLCError#EMPTY_VALU} spcm_no, exmn_cd
	 *                       3. {@link MSLCError#RDDC_TRGT} 검사결과 PK중복.
	 *                       4. {@link MSLCError#NO_EXTC_TRGT} 만료된 데이터.
	 *                       </pre>
	 * 
	 * @exception MSLCRuntimeException
	 * 
	 *                                 <pre>
	 *                                 1. {@link MSLCError#EXCT_IMPB_STAT} 검사결과 보고 불가 상태
	 *                                 </pre>
	 * 
	 * @see IMSC_020300Service#rptgRslt(Map)
	 * @see MSC_020300Controller#rptgRslt(HashMap)
	 */
	LuluResult rptgRslt(List<? extends Map<String, Object>> param);

	/**
	 * 검사결과 보고 취소
	 * 
	 * @author khgkjg12 강현구A
	 * 
	 * @param param 맵 목록
	 *
	 * @see IMSC_020300Service#cnclRptgRslt(Map)
	 * @see MSC_020300Controller#cnclRptgRslt(HashMap)
	 */
	LuluResult cnclRptgRslt(List<? extends Map<String, Object>> param);

	/**
	 * 검사결과이력 목록 조회.
	 * 
	 * @author khgkjg12 강현구A
	 * @date Jan 17, 2024
	 * @param param
	 *              <ol>
	 *              조회 대상 검사결과 PK
	 *              <li>spcm_no : 검체번호.</li>
	 *              <li>exmn_cd : 결과 일련번호</li>
	 *              </ol>
	 * @return 검사결과이력 목록 {@link ExmnRsltHstrModel}
	 * @
	 *   <ol>
	 *   <li>{@link MSLCError#EMPTY_VALU} 빈값이 있음.</li>
	 *   <li>{@link MSLCError#NO_LONG_VALU} exmn_cd가 8비트 정수형으로 표현 불가한 값임.</li>
	 *   </ol>
	 * @see IMSC_020300Service#rtrvExmnRsltHstrList(Map)
	 */
	LuluResult rtrvExmnRsltHstrList(Map<String, Object> param);

	/**
	 * 검사결과판독, 검사 결과에 결과값을 입력. <br>
	 * <br>
	 * 중간보고인 검사의 경우 단/장문 결과값 중 하나라도 달라야 저장된다.
	 * 
	 * 위탁회신 상태에서 입력시 참고치 상/하한, 결과 단위를 같이 갱신할 수 있댜. 그러나 전달 받은 값이 빈 값이라면 저장하지 않는다.
	 * 
	 * @author 강현구
	 * @param param
	 *              <ul>
	 *              List&lt;Map&lt;String, Object&gt;&gt;
	 *              <li>[필수] exmn_cd : 검사코드.</li>
	 *              <li>[필수] spcm_no : 검체번호.</li>
	 *              <li>[선택] exmn_rslt_valu : 결과값.</li>
	 *              <li>[선택] txt_rslt_valu : 결과값.</li>
	 *              <li>[선택] rfvl_lwlm_valu : 하한치.</li>
	 *              <li>[선택] rfvl_uplm_valu : 상한치.</li>
	 *              <li>[선택] rslt_unit_dvsn : 단위코드.</li>
	 *              <li>[선택] dgsg_no : 전자서명 값.</li>
	 *              <li>[선택] exmn_eqpm_cd : 장비 코드.</li>
	 *              <li>[선택] exmn_eqpm_rmrk_cnts : 장비 비고 내용.</li>
	 *              </ul>
	 *              
	 * @oaram enableEqltChck 동일 값 입력 체크 활성화
	 * @param enableNodgChck 자리수 체크 활성화, 활성화시 0 자동 변경로직도 활성화 된다.
	 *
	 * @return
	 *         <ul>
	 *         LuluResult
	 *         <li>[CODE] 200</li>
	 *         <li>[CODE] 402 : {@link MSLCError#EMPTY_VALU} <i>["spcm_no",
	 *         "exmn_cd"]</i></li>
	 *         <li>[CODE] 408 : {@link MSLCError#EMPTY}</li>
	 *         <li>[CODE] 404 : {@link MSLCError#NO_EXCT_CD}<i>"검사코드"</i></li>
	 *         <li>[CODE] 403 : {@link MSLCError#RDDC_TRGT}<i>RSLT</i></li>
	 *         <li>[CODE] 409 : {@link MSLCError#MODEL_CRTN_ERR}</li>
	 *         <li>[CODE] 431 :
	 *         {@link MSLCError#RSLT_VALU_TXT_LNGT_EXCS}<i>"검체번호":"검사코드"</i></li>
	 *         <li>[CODE] 432 :
	 *         {@link MSLCError#RSLT_VALU_FIGR_FRMT_ERR}<i>"검체번호":"검사코드"</i></li>
	 *         </ul>
	 * @see IMSC_020300Service#iptnRslt(List)
	 * @see MSC_020300Controller#iptnRslt(java.util.ArrayList)
	 */
	LuluResult iptnRslt(List<? extends Map<String, Object>> param, boolean enableEqltChck, boolean enableNodgChck);

	/**
	 * CVR 알림 발송을 위한 파라미터 조회
	 * 
	 * @author khgkjg12 강현구A
	 * @date Apr 17, 2024
	 * @param param (spcm_no, exmn_cd) 리스트.
	 * @return CVR발송 인자 리스트.
	 * @see IMSC_020300Service#rtrvCvrNotiDetailList(List)
	 * @see MSC_020300Controller#rtrvCvrNotiDetailList(List)
	 */
	LuluResult rtrvCvrNotiDetailList(List<? extends Map<String, Object>> param);

}
