package com.duzon.lulu.service.MSC.MSC_020000.service.impl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.duzon.common.model.LuluResult;
import com.duzon.lulu.service.MSC.MSC_020000.controller.MSC_020200Controller;
import com.duzon.lulu.service.MSC.MSC_020000.mapper.MSC_020200Mapper;
import com.duzon.lulu.service.MSC.MSC_020000.mapper.MSC_020300Mapper;
import com.duzon.lulu.service.MSC.MSC_020000.model.EntsReqModel;
import com.duzon.lulu.service.MSC.MSC_020000.model.ExmnRsltPkModel;
import com.duzon.lulu.service.MSC.MSC_020000.model.LockSpcmWithEntsRsltModel;
import com.duzon.lulu.service.MSC.MSC_020000.model.RplyEntsModel;
import com.duzon.lulu.service.MSC.MSC_020000.model.RplyEntsParamModel;
import com.duzon.lulu.service.MSC.MSC_020000.model.SpcmPkModel;
import com.duzon.lulu.service.MSC.MSC_020000.model.TrmsAndCnclSuccessModel;
import com.duzon.lulu.service.MSC.MSC_020000.model.CreateEntsApiParamModel;
import com.duzon.lulu.service.MSC.MSC_020000.model.EntsApiParamModel;
import com.duzon.lulu.service.MSC.MSC_020000.service.IMSC_020200Service;
import com.duzon.lulu.service.MSC.util.MSLC.MSLCError;
import com.duzon.lulu.service.MSC.util.MSLC.MSLCRuntimeException;
import com.duzon.lulu.service.MSC.util.MSLC.MSLCSessionHelper;
import com.duzon.lulu.service.MSC.util.MSLC.MSLCUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibm.icu.util.Calendar;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MSC_020200Service implements IMSC_020200Service {

	private final MSC_020200Mapper mapper;
	private final MSC_020300Mapper msc_020300Mapper;
	private final MSLCSessionHelper session;
	private final MSC_020300Service msc_020300Service;
	@Autowired
	private Properties luluProperties;

	private static final String SCL_CODE = "41349890";
	/**
	 * SCL전용 복호화 클래스
	 * 
	 * @author khgkjg12 강현구A
	 * 
	 */
	private class SCLCipher {

		private static final String secretKey = "Seoul_Clinical_Laboratories_OCS@"; // 32bit

		// 암호화
		private String encrypt(String str) throws java.io.UnsupportedEncodingException, NoSuchAlgorithmException,
				NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException,
				IllegalBlockSizeException, BadPaddingException {
			byte[] keyData = secretKey.getBytes();

			SecretKey secureKey = new SecretKeySpec(keyData, "AES");

			Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
			c.init(Cipher.ENCRYPT_MODE, secureKey, new IvParameterSpec(secretKey.substring(0, 16).getBytes()));

			byte[] encrypted = c.doFinal(str.getBytes("UTF-8"));
			String enStr = new String(Base64.encodeBase64(encrypted));

			return enStr;
		}

		// 복호화
		private String decrypt(String str) throws java.io.UnsupportedEncodingException, NoSuchAlgorithmException,
				NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException,
				IllegalBlockSizeException, BadPaddingException {
			byte[] keyData = secretKey.getBytes();
			SecretKey secureKey = new SecretKeySpec(keyData, "AES");
			Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
			c.init(Cipher.DECRYPT_MODE, secureKey, new IvParameterSpec(secretKey.substring(0, 16).getBytes("UTF-8")));

			byte[] byteStr = Base64.decodeBase64(str.getBytes());

			return new String(c.doFinal(byteStr), "UTF-8");
		}
	}

	/**
	 * 위탁기관목록 조회
	 * 
	 * @author khgkjg12 강현구A
	 * 
	 * @return 위탁기관목록
	 */
	@Override
	public LuluResult rtrvEntsInstList() {
		LuluResult luluResult = new LuluResult();
		HashMap<String, Object> param = new HashMap<>();
		session.setSessionForAll(param);
		luluResult.setResultData(mapper.rtrvEntsInstList(param));
		return luluResult;
	}

	@Override
	public LuluResult rtrvEntsExmnPrscList(HashMap<String, Object> param) {
		LuluResult luluResult = MSLCUtil.chckEmpty(param, "from_dy", "to_dy", "search_dy_cd");
		if (luluResult.getResultCode() != 200)
			return luluResult;
		session.setSessionForAll(param);
		luluResult.setResultData(mapper.rtrvEntsExmnPrscList(param));
		return luluResult;
	}

	@SuppressWarnings("unchecked")
	@Transactional
	@Override
	public LuluResult trmsEntsExmnPrsc(List<? extends Map<String, Object>> param) {
		LuluResult luluResult = MSLCUtil.chckEmptyList(param, "spcm_no", "exmn_cd");
		if (luluResult.getResultCode() != 200)
			return luluResult;
		luluResult = MSLCUtil.chckAndCreateSet(ExmnRsltPkModel.class, param);

		Set<ExmnRsltPkModel> exmnRsltSet = (Set<ExmnRsltPkModel>) luluResult.getResultData();
		session.setContext();
		List<EntsApiParamModel> entsApiParamList = mapper
				.createEntsApiParam(new CreateEntsApiParamModel(exmnRsltSet, session.getSession()));
		ArrayList<EntsApiParamModel> validApiParamList = new ArrayList<>();
		entsApiParamList.forEach(each -> {
			if ("F".equals(each.getSpcm_ents_prgr_stat_cd())) {
				validApiParamList.add(each);
			}
		});

		// 파라미터 기관별 분류작업 끝.
		if (validApiParamList.size() < 1) {
			return MSLCError.EXCT_IMPB_STAT.withBody("대상이 전송 대기 상태가 아님.").createLuluResult();
		}

		ArrayList<EntsApiParamModel> sclApiParamList = new ArrayList<>();
		validApiParamList.forEach(each -> {
			String ents_exmn_inst_cd = each.getEnts_exmn_inst_cd();
			if (SCL_CODE.equals(ents_exmn_inst_cd)) {
				sclApiParamList.add(each);// SCL요청 추가.
			}
		});

		ArrayList<TrmsAndCnclSuccessModel> successList = new ArrayList<>();
		luluResult = MSLCUtil.chckAndCreateSetIgnoreRddc(SpcmPkModel.class, param);
		if (luluResult.getResultCode() != 200) {
			return luluResult;
		}
		Set<SpcmPkModel> spcmSet = (Set<SpcmPkModel>) luluResult.getResultData();
		if (sclApiParamList.size() > 0) {// SCL 위탁이 있는 경우.
			luluResult = trmsSCLExmnPrsc(validApiParamList);
			if (luluResult.getResultCode() != 200)
				return luluResult;
			EntsReqModel entsReq = (EntsReqModel) luluResult.getResultData();
			session.getSession().setSessionForModel(entsReq);
			long sclReqNo = mapper.logEntsReq(entsReq);
			if (!"N".equals(entsReq.getSpcm_ents_rqst_sucs_yn())) {
				validApiParamList.forEach(each -> {
					successList.add(new TrmsAndCnclSuccessModel(each, sclReqNo));
					spcmSet.add(new SpcmPkModel(each.getSpcm_no()));
				});
			}
		}

		if (successList.size() < 1) {
			return MSLCError.ENTS_RSPS_ERR.createLuluResult();
		}

		// 요청결과를 DB에 반영하기 시작.

		mapper.trmsEnts(successList);
		msc_020300Mapper.syncSpcm(spcmSet);
		msc_020300Mapper.syncPrsc(spcmSet);
		return luluResult;
	}

	/**
	 * SCL 서버로 HTTP POST요청을 보내고 응답을 받는 메소드.
	 * 
	 * @author khgkjg12 강현구A
	 * @param api  요청할 api 마지막단 주소.
	 * @param body 요청 Body.
	 * 
	 * @throws MSLCException {@link MSLCError#ENTS_CONN_FAIL}
	 * 
	 */
	private LuluResult httpToSCL(String api, Map<String, Object> body, boolean encrypt) {

		String key = mapper.rtrvSclKey();
		if (key == null) {
			return MSLCError.ENTS_NOT_USE.withBody("SCL").createLuluResult();
		}
		EntsReqModel entsReq = new EntsReqModel();
		entsReq.setSpcm_ents_rqst_dt(MSLCUtil.getCurrentDate(MSLCUtil.DASH_TIMESTAMP));
		entsReq.setEnts_exmn_inst_cd(SCL_CODE);
		entsReq.setSpcm_ents_rqst_plain(body);
		HashMap<String, Object> result = null;
		try {
			ObjectMapper objMapper = new ObjectMapper();
			SCLCipher sclCipher = new SCLCipher();
			String json = encrypt ? sclCipher.encrypt(objMapper.writeValueAsString(body))
					: objMapper.writeValueAsString(body);
			entsReq.setSpcm_ents_rqst_cnts(json);
			HttpURLConnection urlConnection = (HttpURLConnection) (new URL(
					luluProperties.getProperty("globals.ENTS_SCL_URL") + api).openConnection());
			urlConnection.setRequestMethod("POST");

			urlConnection.setDoInput(true);
			urlConnection.setDoOutput(true);
			urlConnection.setRequestProperty("Content-Type", "application/json");
			urlConnection.setRequestProperty("x-api-key", key);
			urlConnection.setConnectTimeout(30000);// 30초
			urlConnection.setReadTimeout(30000);// 30초
			try (OutputStream os = urlConnection.getOutputStream()) {
				byte request_data[] = json.getBytes("UTF-8");
				os.write(request_data);
			}
			urlConnection.connect();
			BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));
			StringBuffer sb = new StringBuffer();
			String responseData;
			while ((responseData = br.readLine()) != null) {
				sb.append(responseData); // StringBuffer에 응답받은 데이터 순차적으로 저장 실시
			}
			entsReq.setSpcm_ents_rsps_cnts(sb.toString());
			result = objMapper.readValue(sclCipher.decrypt(sb.toString()),
					new TypeReference<HashMap<String, Object>>() {
					});
			entsReq.setSpcm_ents_rsps_plain(result);
		} catch (Exception e) {
		}

		LuluResult luluResult = new LuluResult();
		luluResult.setResultData(entsReq);
		return luluResult;
	}

	/**
	 * SCL검사처방 전송
	 * 
	 * @author khgkjg12 강현구A
	 * 
	 * @param param session정보가 삽입된 전송할 검사처방 집합
	 * @throws MSLCException
	 * 
	 *                       <pre>
	 *                       1. {@link MSLCError#NO_EXTC_TRGT} 검사처방이 존재하지 않을 때.
	 *                       2. {@link MSLCError#ENTD_CONN_ERR} SCL 연결 실패.
	 *                       3/ {@link MSLCError#ENTS_RSPS_ERR} SCL 응답 오류.
	 *                       4. {@link MSLCError#EXCT_IMPB_STAT} INVALID_INST : 미연동 기관.
	 *                       </pre>
	 * 
	 * @see #trmsEntsExmnPrsc(List)
	 */
	private LuluResult trmsSCLExmnPrsc(ArrayList<EntsApiParamModel> param) {
		List<HashMap<String, Object>> lsclp = new ArrayList<>();
		// null값 전처리 필요.(SCL에서 애러처리함), 상위 메서드에서 처리완료
		for (EntsApiParamModel eachParam : param) {
			HashMap<String, Object> map = new HashMap<>();
			map.put("hbarcode", eachParam.getSpcm_no());
			map.put("hitemcode", eachParam.getExmn_cd());
			map.put("chartno", eachParam.getPid());
			map.put("drname", eachParam.getDr_nm());
			map.put("hgetdate", eachParam.getBlcl_dy());
			map.put("hitemname", eachParam.getPrsc_nm());
			map.put("hsampcode", eachParam.getSpcm_cd());
			map.put("hsampname", eachParam.getSpcm_nm());
			map.put("peopid", eachParam.getPt_frrn() + "-" + eachParam.getPt_srrn());
			map.put("pname", eachParam.getPt_nm());
			map.put("sex", eachParam.getSex());
			lsclp.add(map);
		}
		HashMap<String, Object> body = new HashMap<>();
		body.put("TOTAL_CNT", lsclp.size());
		body.put("PATIENT_LIST", lsclp);
		LuluResult luluResult = httpToSCL("ReceiptApiSvr", body, true);
		if (luluResult.getResultCode() != 200)
			return luluResult;
		EntsReqModel result = (EntsReqModel) luluResult.getResultData();
		result.setSpcm_ents_rqst_dvcd("T");
		if (result.getSpcm_ents_rsps_plain() == null) {
			result.setSpcm_ents_rqst_sucs_yn("N");
			return luluResult;
		}

		Map<String, Object> plain = result.getSpcm_ents_rsps_plain();
		Integer totalCnt = MSLCUtil.parseInt(plain.get("TOTAL_CNT"));
		Integer successCnt = MSLCUtil.parseInt(plain.get("SUCC_CNT"));
		if (totalCnt == null || successCnt == null || totalCnt != successCnt || successCnt != param.size()) {
			result.setSpcm_ents_rqst_sucs_yn("N");
			return luluResult;
		}
		result.setSpcm_ents_rqst_sucs_yn("Y");
		return luluResult;
	}

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
	@SuppressWarnings("unchecked")
	@Transactional
	@Override
	public LuluResult cnclTrmsEntsExmnPrsc(List<? extends Map<String, Object>> param) {
		LuluResult luluResult = MSLCUtil.chckEmptyList(param, "spcm_no", "exmn_cd");
		if (luluResult.getResultCode() != 200)
			return luluResult;
		luluResult = MSLCUtil.chckAndCreateSet(ExmnRsltPkModel.class, param);
		if (luluResult.getResultCode() != 200)
			return luluResult;
		Set<ExmnRsltPkModel> exmnRsltSet = (Set<ExmnRsltPkModel>) luluResult.getResultData();
		// validation 끝.

		session.setContext();
		List<EntsApiParamModel> entsApiParamList = mapper
				.createEntsApiParam(new CreateEntsApiParamModel(exmnRsltSet, session.getSession()));
		ArrayList<EntsApiParamModel> validApiParamList = new ArrayList<>();
		entsApiParamList.forEach(each -> {
			if ("G".equals(each.getSpcm_ents_prgr_stat_cd())) {
				validApiParamList.add(each);
			}
		});

		// 파라미터 기관별 분류작업 끝.
		if (validApiParamList.size() < 1) {
			return MSLCError.EXCT_IMPB_STAT.withBody("대상이 전송 상태가 아님.").createLuluResult();
		}

		ArrayList<EntsApiParamModel> sclApiParamList = new ArrayList<>();
		validApiParamList.forEach(each -> {
			String ents_exmn_inst_cd = each.getEnts_exmn_inst_cd();
			if (SCL_CODE.equals(ents_exmn_inst_cd)) {
				sclApiParamList.add(each);// SCL요청 추가.
			}
		});

		ArrayList<TrmsAndCnclSuccessModel> successList = new ArrayList<>();
		luluResult = MSLCUtil.chckAndCreateSetIgnoreRddc(SpcmPkModel.class, param);
		if (luluResult.getResultCode() != 200)
			return luluResult;
		Set<SpcmPkModel> spcmSet = (Set<SpcmPkModel>) luluResult.getResultData();
		for (EntsApiParamModel apiParam : validApiParamList) {
			luluResult = cnclTrmsSCLExmnPrsc(apiParam);
			if (luluResult.getResultCode() != 200)
				return luluResult;
			EntsReqModel entsReq = (EntsReqModel) luluResult.getResultData();
			session.getSession().setSessionForModel(entsReq);
			long sclReqNo = mapper.logEntsReq(entsReq);
			if (!"N".equals(entsReq.getSpcm_ents_rqst_sucs_yn())) {
				successList.add(new TrmsAndCnclSuccessModel(apiParam, sclReqNo));
				spcmSet.add(new SpcmPkModel(apiParam.getSpcm_no()));
			}
		}

		if (successList.size() < 1)
			return MSLCError.ENTS_RSPS_ERR.createLuluResult();// 성공한 응답이 없음.

		mapper.cnclTrmsEnts(successList);
		msc_020300Mapper.syncSpcm(spcmSet);
		msc_020300Mapper.syncPrsc(spcmSet);
		return luluResult;
	}

	/**
	 * SCL검사처방 전송 취소
	 * 
	 * @author khgkjg12 강현구A
	 * @param param session정보가 삽입된 전송할 검사처방 집합
	 * @throws MSLCException
	 * 
	 *                       <pre>
	 *                       1. {@link MSLCError#NO_EXTC_TRGT} 검사처방이 존재하지 않을 때.
	 *                       2. {@link MSLCError#ENTD_CONN_ERR} SCL 연결 실패.
	 *                       3/ {@link MSLCError#ENTS_RSPS_ERR} SCL 응답 오류.
	 *                       </pre>
	 * 
	 * @see MSC_020200Controller#cnclTrmsEntsExmnPrsc(List)
	 * @see #cnclTrmsEntsExmnPrsc(List)
	 */
	private LuluResult cnclTrmsSCLExmnPrsc(EntsApiParamModel param) {
		List<HashMap<String, Object>> lsclcp = new ArrayList<>();
		HashMap<String, Object> map = new HashMap<>();
		map.put("hbarcode", param.getSpcm_no());
		map.put("hitemcode", param.getExmn_cd());
		lsclcp.add(map);
		HashMap<String, Object> body = new HashMap<>();
		body.put("TOTAL_CNT", lsclcp.size());
		body.put("PATIENT_LIST", lsclcp);
		LuluResult luluResult = httpToSCL("ReceiptCancelApiSvr", body, true);
		if (luluResult.getResultCode() != 200)
			return luluResult;
		EntsReqModel result = (EntsReqModel) luluResult.getResultData();
		result.setSpcm_ents_rqst_dvcd("C");
		if (result.getSpcm_ents_rsps_plain() == null) {
			result.setSpcm_ents_rqst_sucs_yn("N");
			return luluResult;
		}

		Map<String, Object> plain = result.getSpcm_ents_rsps_plain();

		Object errorCode = plain.get("ERROR_CODE");// 애러가 안뜨면 요청 항목들의 삭제가 성공한것.
		if (errorCode != null && ((int) errorCode != 201)) {// 항목 하나에 대한 201은 NO DATA로 해당 항목이 이미
															// 제거됨을 의미. 둘이상은 보장 못함.
			result.setSpcm_ents_rqst_sucs_yn("N");
			return luluResult;
		}
		result.setSpcm_ents_rqst_sucs_yn("Y");
		return luluResult;
	}

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
	@Transactional
	@Override
	public LuluResult rplyEntsExmnPrsc() {
		session.setContext();
		LuluResult luluResult = rplySCLExmnPrsc();
		if (luluResult.getResultCode() != 200)
			return luluResult;
		EntsReqModel req = (EntsReqModel) luluResult.getResultData();

		session.getSession().setSessionForModel(req);
		long sclReqNo = mapper.logEntsReq(req);
		if ("N".equals(req.getSpcm_ents_rqst_sucs_yn())) {
			return MSLCError.ENTS_RSPS_ERR.createLuluResult();
		}
		Set<RplyEntsModel> sclResultSet = req.getRplyList();
		if(sclResultSet.size()<1) {
			luluResult.setResultData(new ArrayList<>());
			return luluResult;
		}
		Set<SpcmPkModel> spcmSet = new HashSet<>();
		sclResultSet.forEach(each -> {
			spcmSet.add(new SpcmPkModel(each.getSpcm_no()));
		});
		List<LockSpcmWithEntsRsltModel> lockList = msc_020300Mapper.lockSpcmWithEntsRslt(spcmSet);
		Map<ExmnRsltPkModel, LockSpcmWithEntsRsltModel> lockMap = new HashMap<>();
		lockList.forEach(each -> {
			lockMap.put(each, each);
		});

		/*
		 * 유효한 위탁회신 데이터 1. 검체처방 내역 + 검사결과 내역 + 위탁의뢰 내역 테이블에 해당 spcm_no와 exmn_cd가 있어야한다.
		 * 2. 위탁의뢰 내역의 위탁 기관 코드가 회신 받은 위탁 기관 코드와 일치. 3. 해당 spcm_no,exmn_cd 쌍 검사결과 내역의
		 * rslt_prgr_stat_cd이 E or M 4. 해당 spcm_no,exmn_cd 쌍의 일련번호가 제일 높은 위탁의뢰 내역의
		 * spcm_ents_prgr_stat_cd가 G or H
		 */
		List<Map<String, Object>> iptnParamList = new ArrayList<>();
		ArrayList<RplyEntsParamModel> rplyEntsParamList = new ArrayList<>();
		List<RplyEntsModel> validSclResultList = new ArrayList<>();
		sclResultSet.forEach(each -> {
			LockSpcmWithEntsRsltModel lock = lockMap.get(each);
			if (lock != null && lock.getEnts_exmn_inst_cd() != null
					&& lock.getEnts_exmn_inst_cd().equals(each.getEnts_exmn_inst_cd())
					&& "EM".contains(lock.getRslt_prgr_stat_cd()) && lock.getSpcm_ents_prgr_stat_cd() != null
					&& "GH".contains(lock.getSpcm_ents_prgr_stat_cd())) {
				validSclResultList.add(each);
				rplyEntsParamList.add(new RplyEntsParamModel(each, sclReqNo, lock.getSpcm_ents_trms_dt(),
						lock.getSpcm_ents_trms_usid()));
				Map<String, Object> param = new HashMap<>();
				param.put("spcm_no", each.getSpcm_no());
				param.put("exmn_cd", each.getExmn_cd());
				param.put("exmn_rslt_valu", each.getSpcm_ents_rply_nmvl());
				param.put("txt_rslt_valu", each.getSpcm_ents_rply_txt());
				param.put("rfvl_uplm_valu", each.getSpcm_ents_rply_uplm());
				param.put("rfvl_lwlm_valu", each.getSpcm_ents_rply_lwlm());
				param.put("rslt_unit_nm", each.getSpcm_ents_rply_unit());
				param.put("exmn_item_rmrk_cnts", each.getSpcm_ents_rply_rmrk());
				iptnParamList.add(param);
			}

		});

		luluResult.setResultData(validSclResultList);
		if (validSclResultList.size() < 1)
			return luluResult;

		mapper.rplyEnts(rplyEntsParamList);
		LuluResult luluResult2 = msc_020300Service.iptnRslt(iptnParamList, false, false);
		if (luluResult2.getResultCode() != 200) {
			throw new MSLCRuntimeException(MSLCError.EXCT_IMPB_STAT
					.withBody("rplyEntsExmnPrsc : iptnRslt 호출 애러, [" + luluResult2.getResultCode() + "] "
							+ luluResult2.getResultMsg() + ", " + luluResult2.getResultData()));
		}
		return luluResult;
	}

	/**
	 * SCL검사 회신
	 * 
	 * @author khgkjg12 강현구A.
	 * @return 위탁검사회신 중복 불가 집합.
	 * @throws MSLCException
	 * 
	 *                       <pre>
	 *                       1. {@link MSLCError#ENTS_CONN_FAIL} 연결 실패.
	 *                       2. {@link MSLCError#ENTS_RSPS_ERR} 응답 애러.
	 *                       </pre>
	 */
	private LuluResult rplySCLExmnPrsc() {
		// SCL 결과 조회는 2주까지만 가능
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Calendar cal = Calendar.getInstance();
		String date_to = sdf.format(cal.getTime());
		cal.add(Calendar.DATE, -6); // 변경,원래는 13이었
		String date_from = sdf.format(cal.getTime());

		// SCL 결과 조회 Body 세팅(WEHAGO-H -> SCL)
		HashMap<String, Object> body = new HashMap<>();
		body.put("result_kind", 2); // 결과구분 (1:미생물 중간결과 포함 , 2: 검사 결과 완료)
		body.put("date_kind", 2); // 날짜구분 (1:접수일자, 2:보고일자)
		body.put("date_from", date_from); // 시작일자 (yyyyMMdd)
		body.put("date_to", date_to); // 종료일자 (yyyyMMdd) // * 일자범위는 2주이내로 조회
		body.put("send_kind", 0); // 전송여부 (0:전부, 1:전송 받은 것, 2:전송 안 받은 것 또는 신규)
		LuluResult luluResult = httpToSCL("ResultApiSvr", body, false);
		if (luluResult.getResultCode() != 200)
			return luluResult;
		EntsReqModel result = (EntsReqModel) luluResult.getResultData();

		result.setSpcm_ents_rqst_dvcd("R");
		if (result.getSpcm_ents_rsps_plain() == null) {
			result.setSpcm_ents_rqst_sucs_yn("N");
			return luluResult;
		}

		Map<String, Object> plain = result.getSpcm_ents_rsps_plain();

		// 결과값 Model
		HashSet<RplyEntsModel> sclResultSet = new HashSet<RplyEntsModel>();

		Object errorCodeObj = plain.get("ERROR_CODE");
		if (errorCodeObj != null) {
			if ((int) errorCodeObj != 201) {
				result.setSpcm_ents_rqst_sucs_yn("N");
				return luluResult;
			} else {
				result.setSpcm_ents_rqst_sucs_yn("Y");
				result.setRplyList(sclResultSet);
				return luluResult;
			}
		}

		@SuppressWarnings("unchecked")
		List<Map<String, Object>> patientListObj = (List<Map<String, Object>>) plain.get("PATIENT_LIST");
		if (patientListObj == null || patientListObj.size() < 1) {
			result.setSpcm_ents_rqst_sucs_yn("Y");
			result.setRplyList(sclResultSet);
			return luluResult;
		}

		for (Map<String, Object> patient : patientListObj) {
			RplyEntsModel rplyModel = new RplyEntsModel(SCL_CODE, (String) patient.get("hbarcode"),
					(String) patient.get("hitemcode"), (String) patient.get("result"), (String) patient.get("fresult"),
					(String) patient.get("comm"), (String) patient.get("korref"), (String) patient.get("high"),
					(String) patient.get("low"), (String) patient.get("lowhi"), (String) patient.get("unit"));

			sclResultSet.add(rplyModel);
			// list에 add
		}
		result.setSpcm_ents_rqst_sucs_yn("Y");
		result.setRplyList(sclResultSet);
		return luluResult;

	}
}
