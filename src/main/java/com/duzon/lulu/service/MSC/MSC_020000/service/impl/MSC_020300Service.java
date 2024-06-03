package com.duzon.lulu.service.MSC.MSC_020000.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.duzon.lulu.service.MSC.MSC_020000.mapper.MSC_020300Mapper;
import com.duzon.common.model.LuluResult;
import com.duzon.lulu.service.MSC.MSC_020000.controller.MSC_020300Controller;
import com.duzon.lulu.service.MSC.MSC_020000.model.ExmnRsltHstrModel;
import com.duzon.lulu.service.MSC.MSC_020000.model.ExmnRsltPkModel;
import com.duzon.lulu.service.MSC.MSC_020000.model.IptnRsltParamModel;
import com.duzon.lulu.service.MSC.MSC_020000.model.LockSpcmWithRsltModel;
import com.duzon.lulu.service.MSC.MSC_020000.model.RptgRsltModel;
import com.duzon.lulu.service.MSC.MSC_020000.model.LockSpcmWithEntsRsltModel;
import com.duzon.lulu.service.MSC.MSC_020000.model.SpcmPkModel;
import com.duzon.lulu.service.MSC.MSC_020000.service.IMSC_020300Service;
import com.duzon.lulu.service.MSC.util.MSLC.Calculator;
import com.duzon.lulu.service.MSC.util.MSLC.MSLCError;
import com.duzon.lulu.service.MSC.util.MSLC.MSLCSessionHelper;
import com.duzon.lulu.service.MSC.util.MSLC.MSLCUtil;

import com.duzon.lulu.service.MSC.util.MSLC.MSLCRuntimeException;

import lombok.RequiredArgsConstructor;

/**
 * 진단검사 결과 API
 * 
 * @author khgkjg12 강현구A
 * 
 */
@RequiredArgsConstructor
@Service
public class MSC_020300Service implements IMSC_020300Service {

	private final MSC_020300Mapper msc_020300Mapper;
	private final MSLCSessionHelper session;

	/**
	 * 진단검사 결과, 검사 현황 조회 MST그리드.
	 * 
	 * @author 강현구
	 * @param param
	 *              <ul>
	 *              HashMap&lt;String, Object&gt;
	 *              <li>[필수] cndt_dy_to : 검사일 시작.</li>
	 *              <li>[필수] cndt_dy_from : 검사일 끝.</li>
	 *              <li>[필수] hope_exrm_dept_sqno_list : 검사실 부서일련 목록.</li>
	 *              <li>[선택] pid : 환자번호.</li>
	 *              </ul>
	 * @return
	 *         <ul>
	 *         LuluResult
	 *         <li>[CODE] 200 : [DATA] 검사 현황 목록 List&lt;RsltSttsModel&gt;.</li>
	 *         <li>[CODE] 402 : [MSG] 빈 값.
	 *         <ul>
	 *         <li>cndt_dy_to</li>
	 *         <li>cndt_dy_from</li>
	 *         <li>hope_exrm_dept_sqno_list</li>
	 *         </ul>
	 *         </li>
	 *         </ul>
	 */
	@Override
	public LuluResult rtrvRsltSttsList(HashMap<String, Object> param) {
		LuluResult luluResult = MSLCUtil.chckEmpty(param, "cndt_dy_to", "cndt_dy_from", "hope_exrm_dept_sqno_list");
		if (luluResult.getResultCode() != 200)
			return luluResult;
		luluResult.setResultData(msc_020300Mapper.rtrvRsltSttsList(param));
		return luluResult;
	}

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

	@Override
	public LuluResult rtrvExmnRsltList(List<String> param) {
		LuluResult luluResult = MSLCUtil.chckEmpty(param);
		if (luluResult.getResultCode() != 200)
			return luluResult;
		luluResult.setResultData(msc_020300Mapper.rtrvExmnRsltList(param));
		return luluResult;
	}

	/**
	 * 검사결과의 상태 변화 가능 여부 검증 로직.
	 * 
	 * 검체별로 결과진행상태코드가 rsltStat에 포함되면서 검체위탁진행상태코드가 null or entsStat 과 일치하는지 검사.
	 * 
	 * 결과 진행상태 코드-위탁 진행상태 코드가 올바르게 싱크되고 있어야 정상 작동. E-NULL|F|G M-NULL|H
	 * 
	 * @author khgkjg12 강현구A
	 * @date Mar 25, 2024
	 * @param rsltMap
	 * @param rsltStat
	 * @param entsStat
	 * @return
	 */
	public LuluResult validateSpcmWithEntsRslt(HashMap<SpcmPkModel, ArrayList<LockSpcmWithEntsRsltModel>> rsltMap,
			String rsltStat, String entsStat) {

		ArrayList<LockSpcmWithEntsRsltModel> rsltList = new ArrayList<>();
		ArrayList<LockSpcmWithEntsRsltModel> entsList = new ArrayList<>();

		Map<ExmnRsltPkModel, String> errorMap = new HashMap<>();
		Set<SpcmPkModel> spcmSet = rsltMap.keySet();

		for (SpcmPkModel spcm : spcmSet) {
			ArrayList<LockSpcmWithEntsRsltModel> rsltListBySpcm = rsltMap.get(spcm);

			for (LockSpcmWithEntsRsltModel rslt : rsltListBySpcm) {
				String rslt_prgr_stat_cd = (String) rslt.getRslt_prgr_stat_cd();
				String spcm_ents_prgr_stat_cd = (String) rslt.getSpcm_ents_prgr_stat_cd();
				if (rsltStat.contains(rslt_prgr_stat_cd)) {
					if (entsStat != null && entsStat.equals(spcm_ents_prgr_stat_cd)) {
						rsltList.add(rslt);
						entsList.add(rslt);
					} else if (spcm_ents_prgr_stat_cd == null) {
						rsltList.add(rslt);
					} else {
						errorMap.put(rslt, spcm_ents_prgr_stat_cd);
					}
				} else {
					errorMap.put(rslt, rslt_prgr_stat_cd);
				}
			}
		}
		if (errorMap.size() > 0) {
			return MSLCError.INVALID_STAT_RSLT.withBody(errorMap).createLuluResult();
		}
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("rsltList", rsltList);
		resultMap.put("entsList", entsList);
		resultMap.put("spcmSet", spcmSet);
		LuluResult luluResult = new LuluResult();
		luluResult.setResultData(resultMap);
		return luluResult;

	}

	/**
	 * 결과단위의 상태변화 여부 검증. LOCK을 가지고 검체 + 검사결과 + 위탁의뢰 내역의 상태를 검증.
	 * 
	 * 검사 취소 : (검사중, NULL | 위탁 대기) 중간 보고 : (검사중|중간보고, NULL | 위탁 전송) 판독 취소 : (중간보고,
	 * NULL) 최종 보고 : (중간보고) 최종보고 취소 (최종보고)
	 * 
	 * @author khgkjg12 강현구A
	 * @date Mar 22, 2024
	 * @param param
	 * @param rsltStat 결과진행상태코드
	 * @param entsStat 검사할 위탁진행상태코드. NULL상태는 기본으로 검사한다.
	 * @return
	 *         <ul>
	 *         LuluResult
	 *         <li>[CODE] 200
	 *         <ul>
	 *         <li>rsltList : 대상 결과 목록</li>
	 *         <li>entsList : 대상 위탁 목록</li>
	 *         </ul>
	 *         </li>
	 *         <li>[CODE] 402 : {@link MSLCError#EMPTY_VALU} <i>["spcm_no",
	 *         "exmn_cd"]</i></li>
	 *         <li>[CODE] 408 : {@link MSLCError#EMPTY}</li>
	 *         <li>[CODE] 409 : {@link MSLCError#MODEL_CRTN_ERR}</li>
	 *         <li>[CODE] 443 : {@link MSLCError#NO_EXMN_SPCM} [ "검체번호" ]</li>
	 *         <li>[CODE] 446 : {@link MSLCError#INVALID_STAT_RSLT} [{ spcm_no :
	 *         "검체번호" , exmn_cd : "검사코드" } : E|F|G|H|M|N ,... ]</li></li>
	 */
	@SuppressWarnings("unchecked")
	private LuluResult validateSpcmWithEntsRslt(List<? extends Map<String, Object>> param, String rsltStat,
			String entsStat) {

		LuluResult luluResult = MSLCUtil.chckEmptyList(param, "spcm_no", "exmn_cd");
		if (luluResult.getResultCode() != 200)
			return luluResult;
		luluResult = MSLCUtil.chckAndCreateSet(ExmnRsltPkModel.class, param);
		if (luluResult.getResultCode() != 200)
			return luluResult;
		Set<ExmnRsltPkModel> rsltSet = (Set<ExmnRsltPkModel>) luluResult.getResultData();

		luluResult = MSLCUtil.chckAndCreateSetIgnoreRddc(SpcmPkModel.class, param);
		if (luluResult.getResultCode() != 200)
			return luluResult;

		Set<SpcmPkModel> spcmSet = (Set<SpcmPkModel>) luluResult.getResultData();

		List<LockSpcmWithEntsRsltModel> lockList = msc_020300Mapper.lockSpcmWithEntsRslt(spcmSet);

		HashMap<SpcmPkModel, ArrayList<LockSpcmWithEntsRsltModel>> lockMap = new HashMap<>();
		lockList.forEach(each -> {
			if (rsltSet.contains(each)) {
				SpcmPkModel spcm = new SpcmPkModel(each.getSpcm_no());
				ArrayList<LockSpcmWithEntsRsltModel> value = lockMap.get(spcm);
				if (value == null) {
					value = new ArrayList<>();
					lockMap.put(spcm, value);
				}
				value.add(each);
			}
		});

		Set<SpcmPkModel> lockKeySet = lockMap.keySet();

		ArrayList<LockSpcmWithEntsRsltModel> lockValues = new ArrayList<>();
		lockMap.values().forEach(each -> lockValues.addAll(each));
		if (lockKeySet.size() < spcmSet.size()) {// 어떤 검체가 없거나 검사중 이전 상태임.
			spcmSet.removeAll(lockKeySet);
			return MSLCError.NO_EXMN_SPCM.withBody(spcmSet).createLuluResult();
		}
		if (lockValues.size() < rsltSet.size()) {// 어떤 검체가 없거나 검사중 이전 상태임.
			rsltSet.removeAll(lockValues);
			return MSLCError.NO_EXTC_RSLT.withBody(rsltSet).createLuluResult();
		}
		luluResult = validateSpcmWithEntsRslt(lockMap, rsltStat, entsStat);
		if (luluResult.getResultCode() != 200) {
			return luluResult;
		}
		((Map<String, Object>) luluResult.getResultData()).put("allRsltList", lockList);
		return luluResult;
	}

	/**
	 * 검사결과의 상태 변화 가능 여부 검증 로직.
	 * 
	 * @author khgkjg12 강현구A
	 * @date Mar 25, 2024
	 * @param rsltMap
	 * @param rsltStat
	 * @param entsStat
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private LuluResult validateSpcmWithRslt(List<? extends Map<String, Object>> param, String rsltStat) {

		LuluResult luluResult = MSLCUtil.chckEmptyList(param, "spcm_no", "exmn_cd");
		if (luluResult.getResultCode() != 200)
			return luluResult;
		luluResult = MSLCUtil.chckAndCreateSet(ExmnRsltPkModel.class, param);
		if (luluResult.getResultCode() != 200)
			return luluResult;
		Set<ExmnRsltPkModel> rsltSet = (Set<ExmnRsltPkModel>) luluResult.getResultData();

		luluResult = MSLCUtil.chckAndCreateSetIgnoreRddc(SpcmPkModel.class, param);
		if (luluResult.getResultCode() != 200)
			return luluResult;
		Set<SpcmPkModel> spcmSet = (Set<SpcmPkModel>) luluResult.getResultData();

		List<LockSpcmWithRsltModel> lockList = msc_020300Mapper.lockSpcmWithRslt(spcmSet);

		HashMap<SpcmPkModel, ArrayList<LockSpcmWithRsltModel>> lockMap = new HashMap<>();
		lockList.forEach(each -> {
			if (rsltSet.contains(each)) {
				SpcmPkModel spcm = new SpcmPkModel(each.getSpcm_no());
				ArrayList<LockSpcmWithRsltModel> value = lockMap.get(spcm);
				if (value == null) {
					value = new ArrayList<>();
					lockMap.put(spcm, value);
				}
				value.add(each);
			}
		});

		Set<SpcmPkModel> lockKeySet = lockMap.keySet();
		ArrayList<LockSpcmWithRsltModel> lockValues = new ArrayList<>();
		lockMap.values().forEach(each -> lockValues.addAll(each));
		if (lockKeySet.size() < spcmSet.size()) {// 어떤 검체가 없거나 검사중 이전 상태임.
			spcmSet.removeAll(lockKeySet);
			return MSLCError.NO_EXMN_SPCM.withBody(spcmSet).createLuluResult();
		}
		if (lockValues.size() < rsltSet.size()) {// 어떤 검체가 없거나 검사중 이전 상태임.
			rsltSet.removeAll(lockValues);
			return MSLCError.NO_EXTC_RSLT.withBody(rsltSet).createLuluResult();
		}

		ArrayList<LockSpcmWithRsltModel> rsltList = new ArrayList<>();

		Map<ExmnRsltPkModel, String> errorMap = new HashMap<>();

		for (SpcmPkModel spcm : spcmSet) {
			ArrayList<LockSpcmWithRsltModel> rsltListBySpcm = lockMap.get(spcm);

			for (LockSpcmWithRsltModel rslt : rsltListBySpcm) {
				String rslt_prgr_stat_cd = (String) rslt.getRslt_prgr_stat_cd();
				if (rsltStat.contains(rslt_prgr_stat_cd)) {
					rsltList.add(rslt);
				} else {
					errorMap.put(rslt, rslt_prgr_stat_cd);
				}
			}
		}
		if (errorMap.size() > 0) {
			return MSLCError.INVALID_STAT_RSLT.withBody(errorMap).createLuluResult();
		}
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("rsltList", rsltList);
		resultMap.put("spcmSet", spcmSet);
		luluResult.setResultData(resultMap);
		return luluResult;

	}

	/**
	 * 간접 빌리루빈 호출 결과 저장 파라미터 생성 로직.
	 * 
	 * @author khgkjg12 강현구A
	 * @date Apr 16, 2024
	 * @param lockD1820A
	 * @param valuD1820  nullable
	 * @param valuD1830  nullable
	 * @return 결과 저장 파라미터.
	 */
	private IptnRsltParamModel calculateD1820A(LockSpcmWithEntsRsltModel lockD1820A, String valuD1820,
			String valuD1830) {
		IptnRsltParamModel exmnD1820A = new IptnRsltParamModel(lockD1820A.getSpcm_no(), lockD1820A.getExmn_cd())
				.setFieldsWithLock(lockD1820A);
		String result;
		try {
			double tempResult = Calculator.indirectBilirubin(Double.parseDouble(valuD1830),
					Double.parseDouble(valuD1820));
			result = (tempResult < 0) ? null : Double.toString(tempResult);
		} catch (NullPointerException | NumberFormatException e) {
			result = null;
		}
		exmnD1820A.setExmn_rslt_valu(result);
		return exmnD1820A;
	}

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
	 *              <li>[선택] rslt_unit_nm : 단위 명.</li>
	 *              <li>[선택] dgsg_no : 전자서명 값.</li>
	 *              <li>[선택] exmn_eqpm_cd : 장비 코드.</li>
	 *              <li>[선택] exmn_eqpm_rmrk_cnts : 장비 비고 내용.</li>
	 *              <li>[선텍[ exmn_item_rmrk_cnts : 검사결과 비고 내용</li>
	 *              </ul>
	 * 
	 * @oaram enableEqltChck 동일 값 입력 체크 활성화
	 * @param enableNodgChck 자리수 체크 활성화, 활성화시 0 자동 변경로직도 활성화 된다. (사용 안하면, 검사코드 마스터를
	 *                       조회하지 않는다)
	 *
	 * @return
	 *         <ul>
	 *         LuluResult
	 *         <li>[CODE] 200</li>
	 *         <li>[CODE] 402 : {@link MSLCError#EMPTY_VALU} <i>["spcm_no",
	 *         "exmn_cd"]</i></li>
	 *         <li>[CODE] 408 : {@link MSLCError#EMPTY}</li>
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
	@SuppressWarnings("unchecked")
	@Transactional
	@Override
	public LuluResult iptnRslt(List<? extends Map<String, Object>> param, boolean enableEqltChck,
			boolean enableNodgChck) {
		LuluResult luluResult = MSLCUtil.chckEmptyList(param, "spcm_no", "exmn_cd");
		if (luluResult.getResultCode() != 200)
			return luluResult;
		luluResult = MSLCUtil.chckAndCreateSet(IptnRsltParamModel.class, param);
		if (luluResult.getResultCode() != 200)
			return luluResult;
		Set<IptnRsltParamModel> iptnParamSet = (Set<IptnRsltParamModel>) luluResult.getResultData();

		luluResult = validateSpcmWithEntsRslt(param, "EM", "H");
		if (luluResult.getResultCode() != 200) {
			return luluResult;
		}
		Set<SpcmPkModel> spcmSet = (Set<SpcmPkModel>) ((Map<String, Object>) luluResult.getResultData()).get("spcmSet");
		List<LockSpcmWithEntsRsltModel> lockRsltList = (List<LockSpcmWithEntsRsltModel>) ((Map<String, Object>) luluResult
				.getResultData()).get("rsltList");
		List<LockSpcmWithEntsRsltModel> allRsltList = (List<LockSpcmWithEntsRsltModel>) ((Map<String, Object>) luluResult
				.getResultData()).get("allRsltList");

		Map<ExmnRsltPkModel, LockSpcmWithEntsRsltModel> lockRsltMap = new HashMap<>();
		lockRsltList.forEach(each -> {
			lockRsltMap.put(each, each);
		});

		for (IptnRsltParamModel each : iptnParamSet) {
			LockSpcmWithEntsRsltModel lockRslt = lockRsltMap.get(each);
			if (lockRslt == null) {
				return MSLCError.NO_EXTC_RSLT.withBody((ExmnRsltPkModel) each).createLuluResult();
			}
			String valuObj = each.getExmn_rslt_valu();
			String txtObj = each.getTxt_rslt_valu();
			String rmrkObj = each.getExmn_item_rmrk_cnts();
			if (enableEqltChck && "M".equals(lockRslt.getRslt_prgr_stat_cd())
					&& ((!StringUtils.hasText(lockRslt.getExmn_rslt_valu()) && !StringUtils.hasText(valuObj))
							|| (StringUtils.hasText(valuObj) && valuObj.equals(lockRslt.getExmn_rslt_valu())))
					&& ((!StringUtils.hasText(lockRslt.getTxt_rslt_valu()) && !StringUtils.hasText(txtObj))
							|| (StringUtils.hasText(txtObj) && txtObj.equals(lockRslt.getTxt_rslt_valu())))
					&& ((!StringUtils.hasText(lockRslt.getExmn_item_rmrk_cnts()) && !StringUtils.hasText(rmrkObj))
							|| (StringUtils.hasText(rmrkObj) && rmrkObj.equals(lockRslt.getExmn_item_rmrk_cnts())))) {
				return MSLCError.SAME_RSLT.withBody((ExmnRsltPkModel) lockRslt).createLuluResult();
			}
			if (valuObj != null) {
				String valu = valuObj.toString();
				if (valu.length() > 4000) {
					return MSLCError.RSLT_VALU_TXT_LNGT_EXCS.withBody(each.getSpcm_no() + ":" + each.getExmn_cd())
							.createLuluResult();
				}
				if (enableNodgChck && "W".equals(lockRslt.getExmn_rslt_tycd()) && valu.length() > 0) {// 진검 코드마스터에 정보가
																										// 없으면 검사 안한다.
					String inprRegex;
					int inprNodg = lockRslt.getInpr_nodg();
					if (inprNodg > 0) {
						inprRegex = "(([1-9]\\d{0," + (inprNodg - 1) + "})|0)";
					} else if (inprNodg > -1) {
						inprRegex = "0";
					} else {
						inprRegex = "(([1-9]\\d*)|0)";
					}
					String dcprRegex;
					int dcprNodg = lockRslt.getDcpr_nodg();
					if (dcprNodg > 0) {
						dcprRegex = "(\\.\\d{1," + dcprNodg + "})?";
					} else if (dcprNodg > -1) {
						dcprRegex = "";
					} else {
						dcprRegex = "(\\.\\d+)?";
					}
					int nodg = lockRslt.getNodg();
					// 전체 유효자릿수 검사.
					if (nodg > -1) {
						int count = 0;
						Matcher matcher = Pattern.compile("[0-9]").matcher(valu);
						while (matcher.find()) {
							count++;
						}
						if (count > nodg) {
							return MSLCError.RSLT_VALU_FIGR_FRMT_ERR
									.withBody(each.getSpcm_no() + ":" + each.getExmn_cd()).createLuluResult();
						}

					}
					// 정수부, 소수부 검사.
					if (!Pattern.matches("^-?" + inprRegex + dcprRegex + "$", valu)) {
						return MSLCError.RSLT_VALU_FIGR_FRMT_ERR.withBody(each.getSpcm_no() + ":" + each.getExmn_cd())
								.createLuluResult();
					}
					// 값이 0인 값들 표기를 0으로 통일
					if (Pattern.matches("^-?0(\\.0+)?", valu)) {
						each.setExmn_rslt_valu("0");
					}
				}

			}

			/**
			 * 회신 데이터에 한해서 참고치, 단위값이 넘어온게 있으면 해당값을 입력.
			 */
			if ("H".equals(lockRslt.getSpcm_ents_prgr_stat_cd())) {
				if (!StringUtils.hasText(each.getRfvl_lwlm_valu())) {
					each.setRfvl_lwlm_valu(lockRslt.getRfvl_lwlm_valu());
					each.setRfvl_lwlm_rang_type_cd("M");// 이상.
				}
				if (!StringUtils.hasText(each.getRfvl_uplm_valu())) {
					each.setRfvl_uplm_valu(lockRslt.getRfvl_uplm_valu());
					each.setRfvl_uplm_rang_type_cd("U");// 이하.
				}
				if (!StringUtils.hasText(each.getRslt_unit_nm())) {
					each.setRslt_unit_nm(lockRslt.getRslt_unit_nm());
				}
			} else {
				each.setFieldsWithLock(lockRslt);
			}
		}
		// 검체별로 정리.
		HashMap<String, HashMap<String, IptnRsltParamModel>> iptnParamMap = new HashMap<>();
		HashMap<String, HashMap<String, LockSpcmWithEntsRsltModel>> allLockMap = new HashMap<>();
		iptnParamSet.forEach(e -> {
			HashMap<String, IptnRsltParamModel> innerMap = iptnParamMap.get(e.getSpcm_no());
			if (innerMap == null) {
				innerMap = new HashMap<>();
				iptnParamMap.put(e.getSpcm_no(), innerMap);
			}
			innerMap.put(e.getExmn_cd(), e);
			System.out.println(e);
		});
		allRsltList.forEach(e -> {
			HashMap<String, LockSpcmWithEntsRsltModel> innerMap = allLockMap.get(e.getSpcm_no());
			if (innerMap == null) {
				innerMap = new HashMap<>();
				allLockMap.put(e.getSpcm_no(), innerMap);
			}
			innerMap.put(e.getExmn_cd(), e);
		});

		// 검체 단위 계산식 적용.
		for (SpcmPkModel eachSpcm : spcmSet) {
			HashMap<String, IptnRsltParamModel> innerParamMap = iptnParamMap.get(eachSpcm.getSpcm_no());
			HashMap<String, LockSpcmWithEntsRsltModel> innerLockMap = allLockMap.get(eachSpcm.getSpcm_no());
			// 크레아티닌 계산식 호출.
			IptnRsltParamModel paramD2280 = innerParamMap.get("D2280");
			LockSpcmWithEntsRsltModel lockD2280A = innerLockMap.get("D2280A");
			if (paramD2280 != null && innerParamMap.get("D2280A") == null && lockD2280A != null) {// 인자로 D2280은 받았지만
																									// D2280A는 없어야함. 그리고
																									// 검체에는있어야함.
				IptnRsltParamModel exmnD2280A = new IptnRsltParamModel(eachSpcm.getSpcm_no(), "D2280A")
						.setFieldsWithLock(lockD2280A);
				String result;
				try {
					long tempResult = Calculator.mdrd(Double.parseDouble(paramD2280.getExmn_rslt_valu()),
							lockD2280A.getAge(), lockD2280A.getSex_cd().equals("F"), false);
					result = (tempResult < 0) ? null : Long.toString(tempResult);
				} catch (NullPointerException | NumberFormatException e) {
					result = null;
				}
				exmnD2280A.setExmn_rslt_valu(result);
				iptnParamSet.add(exmnD2280A);
			}
			// 간접 빌리루빈 계산식 호출
			IptnRsltParamModel paramD1820 = innerParamMap.get("D1820");
			IptnRsltParamModel paramD1830 = innerParamMap.get("D1830");
			LockSpcmWithEntsRsltModel lockD1820A = innerLockMap.get("D1820A");
			LockSpcmWithEntsRsltModel lockD1820 = innerLockMap.get("D1820");
			LockSpcmWithEntsRsltModel lockD1830 = innerLockMap.get("D1830");
			if (innerParamMap.get("D1820A") == null && lockD1820A != null) {// 검체에 1820A가 있으면서, 1820A가 인자에 없음.
				if (paramD1820 != null && paramD1830 == null && lockD1830 != null
						&& lockD1830.getRslt_prgr_stat_cd().equals("M")) {// 1820만 인자값에 있고 대신 검체 내에 1830이 존재 및 중간보고 상태
					iptnParamSet.add(
							calculateD1820A(lockD1820A, paramD1820.getExmn_rslt_valu(), lockD1830.getExmn_rslt_valu()));
				} else if (paramD1830 != null && paramD1820 == null && lockD1820 != null
						&& lockD1820.getRslt_prgr_stat_cd().equals("M")) {
					iptnParamSet.add(
							calculateD1820A(lockD1820A, lockD1820.getExmn_rslt_valu(), paramD1830.getExmn_rslt_valu()));

				} else if (paramD1820 != null && paramD1830 != null) {
					iptnParamSet.add(calculateD1820A(lockD1820A, paramD1820.getExmn_rslt_valu(),
							paramD1830.getExmn_rslt_valu()));
				}
			}
		}

		session.setContext();
		msc_020300Mapper.iptnRslt(iptnParamSet);
		msc_020300Mapper.syncSpcm(spcmSet);
		msc_020300Mapper.syncPrsc(spcmSet);
		msc_020300Mapper.crtnRsltHstr(iptnParamSet);
		return new LuluResult();
	}

	/**
	 * 사용자 요청에 직접 연결되는 서비스 메소드
	 * 
	 * 동일 값 체크, 자릿수 체크가 활성화 되어있다.
	 * 
	 * @author khgkjg12 강현구
	 */
	@Transactional
	@Override
	public LuluResult iptnRslt(List<? extends Map<String, Object>> param) {
		return iptnRslt(param, true, true);
	}

	@SuppressWarnings("unchecked")
	@Transactional
	@Override
	public LuluResult cnclIptnRslt(List<? extends Map<String, Object>> param) {

		LuluResult luluResult = validateSpcmWithEntsRslt(param, "M", null);// M 이면서 NULL
		if (luluResult.getResultCode() != 200) {
			return luluResult;
		}
		Set<SpcmPkModel> spcmSet = (Set<SpcmPkModel>) ((Map<String, Object>) luluResult.getResultData()).get("spcmSet");

		luluResult = MSLCUtil.chckAndCreateSet(IptnRsltParamModel.class, param);
		if (luluResult.getResultCode() != 200)
			return luluResult;
		Set<IptnRsltParamModel> iptnParamSet = (Set<IptnRsltParamModel>) luluResult.getResultData();

		session.setContext();
		msc_020300Mapper.cnclIptnRslt(iptnParamSet);
		msc_020300Mapper.syncSpcm(spcmSet);
		msc_020300Mapper.syncPrsc(spcmSet);
		msc_020300Mapper.crtnRsltHstr(iptnParamSet);
		return new LuluResult();
	}

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
	@SuppressWarnings("unchecked")
	@Transactional
	@Override
	public LuluResult rptgRslt(List<? extends Map<String, Object>> param) {

		LuluResult luluResult = validateSpcmWithRslt(param, "M");// M 이면서 NULL
		if (luluResult.getResultCode() != 200) {
			return luluResult;
		}
		Set<SpcmPkModel> spcmSet = (Set<SpcmPkModel>) ((Map<String, Object>) luluResult.getResultData()).get("spcmSet");

		List<ExmnRsltPkModel> rsltList = (List<ExmnRsltPkModel>) ((Map<String, Object>) luluResult.getResultData())
				.get("rsltList");

		session.setContext();
		List<RptgRsltModel> rptgRsltList = msc_020300Mapper.rptgRslt(rsltList);
		msc_020300Mapper.syncSpcm(spcmSet);
		msc_020300Mapper.syncPrsc(spcmSet);
		msc_020300Mapper.crtnRsltHstr(rsltList);
		msc_020300Mapper.rptgMscRslt(rsltList);

		luluResult.setResultData(rptgRsltList);
		return luluResult;
	}

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
	@SuppressWarnings("unchecked")
	@Transactional
	@Override
	public LuluResult cnclRptgRslt(List<? extends Map<String, Object>> param) {
		LuluResult luluResult = validateSpcmWithRslt(param, "N");
		if (luluResult.getResultCode() != 200) {
			return luluResult;
		}
		Set<SpcmPkModel> spcmSet = (Set<SpcmPkModel>) ((Map<String, Object>) luluResult.getResultData()).get("spcmSet");

		List<ExmnRsltPkModel> rsltList = (List<ExmnRsltPkModel>) ((Map<String, Object>) luluResult.getResultData())
				.get("rsltList");

		session.setContext();
		msc_020300Mapper.cnclRptgRslt(rsltList);
		msc_020300Mapper.syncSpcm(spcmSet);
		msc_020300Mapper.syncPrsc(spcmSet);
		msc_020300Mapper.crtnRsltHstr(rsltList);
		msc_020300Mapper.cnclRptgMscRslt(rsltList);

		luluResult.setResultData(rsltList);
		return luluResult;
	}

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
	@Override
	public LuluResult rtrvExmnRsltHstrList(Map<String, Object> param) {
		LuluResult luluResult = MSLCUtil.chckEmpty(param, "spcm_no", "exmn_cd");
		if (luluResult.getResultCode() != 200)
			return luluResult;
		// 세션 불필요.
		luluResult.setResultData(msc_020300Mapper.rtrvExmnRsltHstrList(param));
		return luluResult;
	}
	
	
	/**
	 * CVR 알림 발송을 위한 파라미터 조회
	 * 
	 * @author khgkjg12 강현구A
	 * @date Apr 17, 2024
	 * @param param (spcm_no, exmn_cd) 리스트.
	 * @return CVR발송 인자 리스트.
	 */
	@Override
	@SuppressWarnings("unchecked")
	public LuluResult rtrvCvrNotiDetailList(List<? extends Map<String, Object>> param) {
		LuluResult luluResult = MSLCUtil.chckEmptyList(param, "spcm_no", "exmn_cd");
		if(luluResult.getResultCode()!=200)return luluResult;
		luluResult = MSLCUtil.chckAndCreateSetIgnoreRddc(ExmnRsltPkModel.class, param);
		if(luluResult.getResultCode()!=200)return luluResult;
		luluResult.setResultData(msc_020300Mapper.rtrvCvrNotiDetailList((Set<ExmnRsltPkModel>) luluResult.getResultData()));
		return luluResult;
	}
}
