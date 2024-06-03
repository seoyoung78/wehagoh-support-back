package com.duzon.lulu.service.MSC.MSC_020000.service.impl;

import com.duzon.common.model.LuluResult;
import com.duzon.lulu.service.MSC.MSC_020000.controller.MSC_020100Controller;
import com.duzon.lulu.service.MSC.MSC_020000.mapper.MSC_020100Mapper;
import com.duzon.lulu.service.MSC.MSC_020000.mapper.MSC_020300Mapper;
import com.duzon.lulu.service.MSC.MSC_020000.model.BrcdExmnPrscModel;
import com.duzon.lulu.service.MSC.MSC_020000.model.LockPrscModel;
import com.duzon.lulu.service.MSC.MSC_020000.model.LockSpcmModel;
import com.duzon.lulu.service.MSC.MSC_020000.model.ExmnPrscPkModel;
import com.duzon.lulu.service.MSC.MSC_020000.model.ExmnRsltParamModel;
import com.duzon.lulu.service.MSC.MSC_020000.model.ExmnRsltPkModel;
import com.duzon.lulu.service.MSC.MSC_020000.model.SpcmModel;
import com.duzon.lulu.service.MSC.MSC_020000.model.LockSpcmWithPrscModel;
import com.duzon.lulu.service.MSC.MSC_020000.model.LockSpcmWithEntsRsltModel;
import com.duzon.lulu.service.MSC.MSC_020000.model.SpcmPkModel;
import com.duzon.lulu.service.MSC.MSC_020000.service.IMSC_020100Service;
import com.duzon.lulu.service.MSC.util.MSLC.MSLCError;
import com.duzon.lulu.service.MSC.util.MSLC.MSLCParamWrapper;
import com.duzon.lulu.service.MSC.util.MSLC.MSLCSessionHelper;
import com.duzon.lulu.service.MSC.util.MSLC.MSLCUtil;
import com.duzon.lulu.service.MSC.util.MSLC.MSLCRuntimeException;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class MSC_020100Service implements IMSC_020100Service {

	private final MSC_020100Mapper msc_020100Mapper;
	private final MSLCSessionHelper session;
	private final MSC_020300Mapper msc_020300Mapper;
	private final MSC_020300Service msc_020300Service;

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
	@Override
	public LuluResult rtrvBrcdUseYn() {
		session.setContext();
		LuluResult luluResult = new LuluResult();
		HashMap<String, Object> result = msc_020100Mapper.rtrvBrcdUseYn();
		if (result == null) {
			luluResult.setResultData(false);// 정보가 없어도 미사용 처리.
			return luluResult;
		}
		luluResult.setResultData(result.get("item_stup_valu").equals("Y") && result.get("use_yn").equals("Y"));
		return luluResult;
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
	 * @see IMSC_020100Service#rtrvRcpnSttsList(HashMap)
	 * @see MSC_020100Controller#rtrvRcpnSttsList(HashMap)
	 */
	@Override
	public LuluResult rtrvRcpnSttsList(HashMap<String, Object> param) {
		LuluResult luluResult = MSLCUtil.chckEmpty(param, "exmn_hope_date", "hope_exrm_dept_sqno_list");
		if (luluResult.getResultCode() != 200)
			return luluResult;
		luluResult.setResultData(msc_020100Mapper.rtrvRcpnSttsList(param));
		return luluResult;
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
	 * @see IMSC_020100Service#rtrvExmnPrscList(HashMap)
	 * @see MSC_020100Controller#rtrvExmnPrscList(HashMap)
	 */
	@Override
	public LuluResult rtrvExmnPrscList(HashMap<String, Object> param) {
		LuluResult luluResult = MSLCUtil.chckEmpty(param, "exmn_hope_date", "rcpn_no", "hope_exrm_dept_sqno");
		if (luluResult.getResultCode() != 200)
			return luluResult;
		luluResult.setResultData(msc_020100Mapper.rtrvExmnPrscList(param));
		return luluResult;
	}

	/**
	 * 검체에 묶인 검사처방 목록을 조회.
	 * 
	 * @author khgkjg12 강현구A
	 * @date Dec 11, 2023
	 * @param param 검체번호 String.
	 * @return
	 *         <ul>
	 *         LuluResult
	 *         <li>[CODE] 200 : 검체에 묶인 검사 처방 목록 List<BrcdExmnPrscModel>.</li>
	 *         <li>[CODE] 404 : {@link MSLCError#NO_EXTC_TRGT}<i>"PK" | { "PK1" :
	 *         "value1", "PK2" : "value2",... }</i></li>
	 *         <li>[CODE] 408 : {@link MSLCError#EMPTY}</li>
	 *         <li>[CODE] 443 : {@link MSLCError#ALRD_RCPN}<i>"검체번호"</i></li>
	 *         </ul>
	 * @see IMSC_020100Service#rtrvBrcdExmnPrscList(String)
	 * @see MSC_020100Controller#rtrvBrcdExmnPrscList(MSLCParamWrapper)
	 */
	@Override
	public LuluResult rtrvBrcdExmnPrscList(String param) {
		LuluResult luluResult = MSLCUtil.chckEmpty(param);
		if (luluResult.getResultCode() != 200) {
			return luluResult;
		}
		List<BrcdExmnPrscModel> brcdExmnPrscList = msc_020100Mapper.rtrvBrcdExmnPrscList(param);
		if (brcdExmnPrscList.size() < 1) {
			return MSLCError.NO_EXTC_SPCM.withBody(param).createLuluResult();
		}
		for (BrcdExmnPrscModel brcdExmnPrsc : brcdExmnPrscList) {
			if (!"B".equals(brcdExmnPrsc.getPrsc_prgr_stat_cd())) {
				return MSLCError.ALRD_RCPN.withBody(param).createLuluResult();
			}
		}
		luluResult.setResultData(brcdExmnPrscList);
		return luluResult;
	}

	/**
	 * 바코드 출력용 정보 조회.
	 * 
	 * @author khgkjg12 강현구A
	 * @date Mar 18, 2024
	 * @param param 검체번호 String.
	 * @return
	 *         <ul>
	 *         LuluResult
	 *         <li>[CODE] 200 : [DATA] 출력용 바코드 정보 BrcdModel.</li>
	 *         <li>[CODE] 402 : [MSG] 빈 값.</li>
	 *         </ul>
	 * @see IMSC_020100Service#rtrvBrcd(String)
	 * @see MSC_020100Controller#rtrvBrcd(MSLCParamWrapper)
	 */
	@Override
	public LuluResult rtrvBrcd(String param) {
		LuluResult luluResult = MSLCUtil.chckEmpty(param);
		if (luluResult.getResultCode() != 200) {
			return luluResult;
		}
		luluResult.setResultData(msc_020100Mapper.rtrvBrcd(param));
		return luluResult;
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
	 * @param param
	 *              <ul>
	 *              List&lt;Map&gt;
	 *              <li>pid : 환자번호 String.</li>
	 *              <li>prsc_date : 처방일자 String.</li>
	 *              <li>prsc_sqno : 처방일련번호 Long.</li>
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
	 *         </ul>
	 *         </li>
	 *         </ul>
	 * @see IMSC_020100Service#issuBrcd(List)
	 * @see MSC_020100Controller#issuBrcd(MSLCParamWrapper)
	 */
	@Transactional
	@Override
	public LuluResult issuBrcd(List<? extends Map<String, Object>> param) {
		LuluResult luluResult = MSLCUtil.chckEmptyList(param, "pid", "prsc_date", "prsc_sqno");
		if (luluResult.getResultCode() != 200) {
			return luluResult;
		}
		luluResult = MSLCUtil.chckLongList(param, "prsc_sqno");
		if (luluResult.getResultCode() != 200) {
			return luluResult;
		}
		luluResult = MSLCUtil.chckAndCreateSetIgnoreRddc(ExmnPrscPkModel.class, param);
		if (luluResult.getResultCode() != 200) {
			return luluResult;
		}
		@SuppressWarnings("unchecked")
		Set<ExmnPrscPkModel> prscSet = (Set<ExmnPrscPkModel>) luluResult.getResultData();
		List<LockPrscModel> prscList = msc_020100Mapper.lockPrsc(prscSet);

		if (prscList.size() < prscSet.size()) {
			prscSet.removeAll(prscList);
			return MSLCError.NO_EXTC_PRSC.withBody(prscSet).createLuluResult();
		}

		LockPrscModel initialLock = prscList.get(0);
		String spcm_cd = initialLock.getSpcm_cd();
		String rcpn_no = initialLock.getRcpn_no();
		Long hope_exrm_dept_sqno = initialLock.getHope_exrm_dept_sqno();
		String exmn_hope_date = initialLock.getExmn_hope_date();
		String pt_dvcd = initialLock.getPt_dvcd();
		String emrg_pt_yn = initialLock.getEmrg_pt_yn();
		String slip_cd = initialLock.getSlip_cd();

		String pid = initialLock.getPid();
		String prsc_date = initialLock.getPrsc_date();
		long prsc_sqno = initialLock.getPrsc_sqno();
		Set<String> prscCdSet = new HashSet<String>();
		for (LockPrscModel prsc : prscList) {
			if ("Y".equals(prsc.getMdcr_cncl_yn()))
				return MSLCError.CNCL_PRSC.withBody(prsc).createLuluResult();
			if ("Y".equals(prsc.getDc_yn()))
				return MSLCError.DC_PRSC.withBody(prsc).createLuluResult();
			if (!"B".equals(prsc.getPrsc_prgr_stat_cd()))
				return MSLCError.NO_WTNG_PRSC.withBody(prsc).createLuluResult();
			if (prsc.getSpcm_no() != null)
				return MSLCError.ALRD_ISSU_PRSC.withBody(prsc).createLuluResult();
			if (!prsc.getSpcm_cd().equals(spcm_cd))
				return MSLCError.SPCM_CD_DIFF_PRSC.withBody(prsc).createLuluResult();
			if (!prsc.getRcpn_no().equals(rcpn_no))
				return MSLCError.RCPN_NO_DIFF_PRSC.withBody(prsc).createLuluResult();
			if (!prsc.getHope_exrm_dept_sqno().equals(hope_exrm_dept_sqno))
				return MSLCError.EXRM_DIFF_PRSC.withBody(prsc).createLuluResult();
			if (!prsc.getExmn_hope_date().equals(exmn_hope_date))
				return MSLCError.EXMN_DATE_DIFF_PRSC.withBody(prsc).createLuluResult();
			if (!prscCdSet.add(prsc.getPrsc_cd())) {
				return MSLCError.RDDC_PRSC_CD.withBody(prsc).createLuluResult();
			}
			long tPrsc_sqno = prsc.getPrsc_sqno();
			String tPrsc_date = prsc.getPrsc_date();
			if (prsc_date.compareTo(tPrsc_date) < 0 || prsc_sqno < tPrsc_sqno) {
				prsc_date = tPrsc_date;
				prsc_sqno = tPrsc_sqno;
				slip_cd = prsc.getSlip_cd();// 대표 처방의 slip_cd 반영.
			}
		}

		SpcmModel spcm = new SpcmModel();
		String spcm_no = msc_020100Mapper.crtnNoen();
		spcm.setSpcm_no(spcm_no);
		spcm.setSpcm_cd(spcm_cd);
		spcm.setRcpn_no(rcpn_no);
		spcm.setHope_exrm_dept_sqno(hope_exrm_dept_sqno);
		spcm.setExmn_hope_date(exmn_hope_date);
		spcm.setBrcd_issu_plce_cd(null);// 나중에 관련 기획 추가될 때 사용.
		spcm.setPid(pid);
		spcm.setPt_dvcd(pt_dvcd);
		spcm.setSlip_cd(slip_cd);
		spcm.setEmrg_yn(emrg_pt_yn);
		spcm.setPrsc_date(prsc_date);
		spcm.setPrsc_sqno(prsc_sqno);

		session.setContext();
		msc_020100Mapper.issuPrsc(prscList, spcm_no);
		msc_020100Mapper.issuSpcm(spcm);

		luluResult.setResultData(spcm_no);
		return luluResult;
	}

	/**
	 * 검체 + 검사처방 테이블의 조건을 검사함.
	 * 
	 * 접수, 검사에서 사용.
	 * 
	 * @author khgkjg12 강현구A
	 * @date Mar 25, 2024
	 * @param param
	 * @param prscStat
	 * @return
	 */
	private LuluResult validateSpcmWithPrsc(List<String> param, String spcmStat) {

		LuluResult luluResult = MSLCUtil.chckEmpty(param);
		if (luluResult.getResultCode() != 200) {
			return luluResult;
		}
		luluResult = MSLCUtil.chckAndCreateSetIgnoreRddc(SpcmPkModel.class, param.toArray());
		if (luluResult.getResultCode() != 200) {
			return luluResult;
		}
		@SuppressWarnings("unchecked")
		HashSet<SpcmPkModel> spcmSet = (HashSet<SpcmPkModel>) luluResult.getResultData();
		List<LockSpcmWithPrscModel> lockList = msc_020100Mapper.lockSpcmWithPrsc(spcmSet);
		HashMap<SpcmPkModel, List<LockSpcmWithPrscModel>> lockMap = new HashMap<>();
		lockList.forEach(each -> {
			SpcmPkModel key = new SpcmPkModel(each.getSpcm_no());
			List<LockSpcmWithPrscModel> tempList = lockMap.get(key);
			if (tempList == null) {
				tempList = new ArrayList<>();
				lockMap.put(key, tempList);
			}
			tempList.add(each);
		});
		Set<SpcmPkModel> lockKeySet = lockMap.keySet();
		if (lockKeySet.size() < spcmSet.size()) {
			spcmSet.removeAll(lockKeySet);
			return MSLCError.NO_EXTC_SPCM.withBody(spcmSet).createLuluResult();
		}

		List<LockSpcmWithPrscModel> prscList = new ArrayList<>();

		Map<SpcmPkModel, String> errorMap = new HashMap<SpcmPkModel, String>();

		for (SpcmPkModel key : lockKeySet) {
			List<LockSpcmWithPrscModel> tempLockList = (List<LockSpcmWithPrscModel>) lockMap.get(key);
			String tempSpcmStat = tempLockList.get(0).getSpcm_prsc_prgr_stat_cd();

			if (!spcmStat.equals(tempSpcmStat)) {
				errorMap.put(key, tempSpcmStat);
				continue;
			}
			boolean isValid = false;
			for (LockSpcmWithPrscModel lock : tempLockList) {
				if (!"Y".equals(lock.getDc_yn()) && !"Y".equals(lock.getMdcr_cncl_yn())) {
					prscList.add(lock);
					isValid = true;
				}
			}
			if (!isValid) {
				errorMap.put(key, "PRSC_CNCL");
			}
		}
		if (errorMap.size() > 0) {
			return MSLCError.INVALID_STAT_SPCM.withBody(errorMap).createLuluResult();
		}
		HashMap<String, Object> result = new HashMap<>();
		result.put("prscList", prscList);
		result.put("spcmSet", spcmSet);
		luluResult.setResultData(result);
		return luluResult;
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
	 * @see IMSC_020100Service#rcpnBrcd(List)
	 * @see MSC_020100Controller#rcpnBrcd(MSLCParamWrapper)
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	@Override
	public LuluResult rcpnBrcd(List<String> param) {
		LuluResult luluResult = validateSpcmWithPrsc(param, "B");
		if (luluResult.getResultCode() != 200) {
			return luluResult;
		}
		Map<String, Object> result = (Map<String, Object>) luluResult.getResultData();
		List<LockSpcmWithPrscModel> prscList = (List<LockSpcmWithPrscModel>) result.get("prscList");
		Set<SpcmPkModel> spcmSet = (Set<SpcmPkModel>) result.get("spcmSet");

		session.setContext();
		msc_020100Mapper.rcpnSpcm(spcmSet);
		msc_020100Mapper.rcpnPrsc(prscList);
		return new LuluResult();
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
	 *              List&lt;Map&gt;
	 *              <li>pid : 환자번호 String.</li>
	 *              <li>prsc_date : 처방일자 String.</li>
	 *              <li>prsc_sqno : 처방일련번호 Long.</li>
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
	 * @see IMSC_020100Service#issuBrcdAndRcpn(List)
	 * @see MSC_020100Controller#issuBrcdAndRcpn(MSLCParamWrapper)
	 */
	@Transactional
	@Override
	public LuluResult issuBrcdAndRcpn(List<? extends Map<String, Object>> param) {
		LuluResult luluResult = issuBrcd(param);// 이곳에서 세션이 잡힘.
		if (luluResult.getResultCode() != 200) {
			return luluResult;
		}
		List<String> rcpnBrcdParam = new ArrayList<>();
		rcpnBrcdParam.add((String) luluResult.getResultData());
		LuluResult rcpnResult = rcpnBrcd(rcpnBrcdParam);
		if (rcpnResult.getResultCode() != 200)// 발생할 일은 없지만, 내부적으로 validation애러가 발생할 경우. 롤백을 수행해준다.
			throw new MSLCRuntimeException(
					MSLCError.EXCT_IMPB_STAT.withBody("issuBrcdAndRcpn : rcpnBrcd 호출 애러, [" + rcpnResult.getResultCode()
							+ "] " + rcpnResult.getResultMsg() + ", " + rcpnResult.getResultData()));
		return luluResult;
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
	 * @param param 접수취소할 검체 번호 목록 List&lt;String&gt;.
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
	 * @see IMSC_020100Service#cnclRcpnBrcd(List)
	 * @see MSC_020100Controller#cnclRcpnBrcd(MSLCParamWrapper)
	 */
	@Transactional
	@Override
	public LuluResult cnclRcpnBrcd(List<String> param) {
		LuluResult luluResult = MSLCUtil.chckEmpty(param);
		if (luluResult.getResultCode() != 200) {
			return luluResult;
		}
		luluResult = MSLCUtil.chckAndCreateSetIgnoreRddc(SpcmPkModel.class, param.toArray());
		if (luluResult.getResultCode() != 200) {
			return luluResult;
		}
		@SuppressWarnings("unchecked")
		HashSet<SpcmPkModel> spcmSet = (HashSet<SpcmPkModel>) luluResult.getResultData();
		List<LockSpcmModel> lockList = msc_020100Mapper.lockSpcm(spcmSet);
		HashMap<SpcmPkModel, String> errorMap = new HashMap<>();

		if (lockList.size() < spcmSet.size()) {
			spcmSet.removeAll(lockList);
			return MSLCError.NO_EXTC_SPCM.withBody(spcmSet).createLuluResult();
		}

		for (LockSpcmModel lock : lockList) {
			String stat = lock.getPrsc_prgr_stat_cd();
			if (!"C".equals(stat)) {
				errorMap.put(lock, stat);
			}
		}

		if (errorMap.size() > 0) {
			return MSLCError.INVALID_STAT_SPCM.withBody(errorMap).createLuluResult();
		}

		session.setContext();
		msc_020100Mapper.cnclRcpnSpcm(spcmSet);
		msc_020100Mapper.cnclRcpnPrsc(spcmSet);
		return new LuluResult();
	}

	/**
	 * 검체 검사.
	 * 
	 * @author 강현구.
	 * @apiNote 최종 수정일 : 2024-03-18
	 * @param param 검사할 검체 번호 목록 List&lt;String&gt;.
	 * @return
	 *         <li>LuluResult
	 *         <li>[CODE] 200 : [DATA] NULL.</li>
	 *         <li>[CODE] 402 : [MSG] 빈 값.</li>
	 *         <li>[CODE] 404 : [MSG] 대상이 없음.
	 *         <ul>
	 *         <li>{검체 번호}</li>
	 *         </ul>
	 *         </li>
	 *         <li>[CODE] 407 : [MSG] 실행 불가 상태.
	 *         <ul>
	 *         <li>모델 생성 인자가 잘못됨.</li>
	 *         <li>모든 검사처방이 DC된 검체가 존재.{검체 번호}</li>
	 *         <li>모든 검사처방이 진료취소된 검체가 존재.{검체 번호}</li>
	 *         <li>접수 상태가 아닌 검체가 존재.{검체 번호}</li>
	 *         </ul>
	 *         </li>
	 * @see IMSC_020100Service#exmnBrcd(List)
	 * @see MSC_020100Controller#exmnBrcd(MSLCParamWrapper)
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	@Override
	public LuluResult exmnBrcd(List<String> param) {

		LuluResult luluResult = validateSpcmWithPrsc(param, "C");
		Map<String, Object> result = (Map<String, Object>) luluResult.getResultData();
		Set<SpcmPkModel> spcmSet = (Set<SpcmPkModel>) result.get("spcmSet");
		List<LockSpcmWithPrscModel> prscList = (List<LockSpcmWithPrscModel>) result.get("prscList");
		Map<SpcmPkModel, ExmnRsltParamModel> exmnRsltParamMap = new HashMap<>();

		prscList.forEach(each -> {
			SpcmPkModel key = new SpcmPkModel(each.getSpcm_no());
			ExmnRsltParamModel exmnRsltParam = exmnRsltParamMap.get(key);
			if (exmnRsltParam == null) {
				exmnRsltParam = new ExmnRsltParamModel();
				exmnRsltParam.setPid(each.getPid());
				exmnRsltParam.setRcpn_no(each.getRcpn_no());
				exmnRsltParam.setSpcm_no(each.getSpcm_no());
				exmnRsltParam.setPrscInfoList(new ArrayList<>());
				exmnRsltParamMap.put(key, exmnRsltParam);
			}
			exmnRsltParam.getPrscInfoList().add(each);
		});

		session.setContext();
		msc_020100Mapper.exmnSpcm(spcmSet);
		msc_020100Mapper.exmnPrsc(prscList);
		List<ExmnRsltPkModel> rsltList = msc_020100Mapper.exmnRslt(exmnRsltParamMap.values());
		msc_020100Mapper.exmnEnts(prscList, rsltList);
		msc_020300Mapper.crtnRsltHstr(rsltList);
		return new LuluResult();
	}

	@SuppressWarnings("unchecked")
	private LuluResult validateSpcmWithEntsRslt(List<String> param, String rsltStat, String entsStat) {

		LuluResult luluResult = MSLCUtil.chckEmpty(param);
		if (luluResult.getResultCode() != 200)
			return luluResult;
		luluResult = MSLCUtil.chckAndCreateSetIgnoreRddc(SpcmPkModel.class, param.toArray());
		if (luluResult.getResultCode() != 200)
			return luluResult;
		Set<SpcmPkModel> spcmSet = (Set<SpcmPkModel>) luluResult.getResultData();

		List<LockSpcmWithEntsRsltModel> lockList = msc_020300Mapper.lockSpcmWithEntsRslt(spcmSet);

		HashMap<SpcmPkModel, ArrayList<LockSpcmWithEntsRsltModel>> lockMap = new HashMap<>();
		lockList.forEach(each -> {
			SpcmPkModel spcm = new SpcmPkModel(each.getSpcm_no());
			ArrayList<LockSpcmWithEntsRsltModel> value = lockMap.get(spcm);
			if (value == null) {
				value = new ArrayList<>();
				lockMap.put(spcm, value);
			}
			value.add(each);
		});

		Set<SpcmPkModel> lockKeySet = lockMap.keySet();
		if (lockKeySet.size() < spcmSet.size()) {// 어떤 검체가 없거나 검사중 이전 상태임.
			spcmSet.removeAll(lockKeySet);
			return MSLCError.NO_EXMN_SPCM.withBody(spcmSet).createLuluResult();
		}
		return msc_020300Service.validateSpcmWithEntsRslt(lockMap, rsltStat, entsStat);
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
	 * @param param 검사할 검체 번호 목록 List&lt;String&gt;.
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
	 * @see IMSC_020100Service#cnclExmnBrcd(List)
	 * @see MSC_020100Controller#cnclExmnBrcd(MSLCParamWrapper)
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	@Override
	public LuluResult cnclExmnBrcd(List<String> param) {
		LuluResult luluResult = validateSpcmWithEntsRslt(param, "E", "F");
		if (luluResult.getResultCode() != 200) {
			return luluResult;
		}

		List<LockSpcmWithEntsRsltModel> validRsltList = (ArrayList<LockSpcmWithEntsRsltModel>) ((Map<String, Object>) luluResult
				.getResultData()).get("rsltList");
		Set<SpcmPkModel> validSpcmList = (Set<SpcmPkModel>) ((Map<String, Object>) luluResult.getResultData())
				.get("spcmSet");
		List<LockSpcmWithEntsRsltModel> totalEntsList = (ArrayList<LockSpcmWithEntsRsltModel>) ((Map<String, Object>) luluResult
				.getResultData()).get("entsList");

		session.setContext();
		msc_020100Mapper.cnclExmnRsltHstr(validRsltList);
		msc_020100Mapper.cnclExmnRslt(validRsltList);
		if (totalEntsList.size() > 0)
			msc_020100Mapper.cnclExmnEnts(totalEntsList);
		msc_020100Mapper.cnclExmnSpcm(validSpcmList);
		msc_020100Mapper.cnclExmnPrsc(validSpcmList);
		return new LuluResult();
	}
}