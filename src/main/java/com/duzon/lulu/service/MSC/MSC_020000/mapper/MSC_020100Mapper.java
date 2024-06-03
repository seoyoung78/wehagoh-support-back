package com.duzon.lulu.service.MSC.MSC_020000.mapper;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.duzon.common.mapper.LuluAbstractMapper;
import com.duzon.lulu.service.MSC.util.MSLC.MSLCBatchSupport;
import com.duzon.lulu.service.MSC.util.MSLC.MSLCError;
import com.duzon.lulu.service.MSC.util.MSLC.MSLCRuntimeException;
import com.duzon.lulu.service.MSC.util.MSLC.MSLCSessionHelper;
import com.duzon.lulu.service.MSC.MSC_020000.model.BrcdExmnPrscModel;
import com.duzon.lulu.service.MSC.MSC_020000.model.BrcdModel;
import com.duzon.lulu.service.MSC.MSC_020000.model.ExmnPrscModel;
import com.duzon.lulu.service.MSC.MSC_020000.model.ExmnPrscPkModel;
import com.duzon.lulu.service.MSC.MSC_020000.model.RcpnSttsModel;
import com.duzon.lulu.service.MSC.MSC_020000.model.LockSpcmWithPrscModel;
import com.duzon.lulu.service.MSC.MSC_020000.model.SpcmPkModel;
import com.duzon.lulu.service.MSC.MSC_020000.model.ExmnRsltParamModel;
import com.duzon.lulu.service.MSC.MSC_020000.model.ExmnRsltPkModel;
import com.duzon.lulu.service.MSC.MSC_020000.model.LockPrscModel;
import com.duzon.lulu.service.MSC.MSC_020000.model.LockSpcmModel;
import com.duzon.lulu.service.MSC.MSC_020000.model.SpcmModel;
import com.duzon.lulu.service.MSC.MSC_020000.model.LockSpcmWithEntsRsltModel;

@Repository
public class MSC_020100Mapper extends LuluAbstractMapper {

	private static final String PATH = "com.duzon.lulu.service.MSC.MSC_020000.mapper.MSC_020100Mapper.";

	@Autowired
	private MSLCSessionHelper session;
	@Autowired
	private MSLCBatchSupport batch;

	/**
	 * 바코드 사용 여부 조회. <b>setContext() 필요.</b>
	 * 
	 * @author khgkjg12 강현구A
	 * @date Mar 18, 2024
	 * @return
	 */
	public HashMap<String, Object> rtrvBrcdUseYn() {
		return selectOne(PATH + "rtrvBrcdUseYn", session.getSession());
	}

	/* 환자 응급 여부 조회 */
	public String rtrvEmrgPtYn(String rcpn_no) {
		return selectOne(PATH + "rtrvEmrgPtYn");
	}

	/* 접수 현황 조회 */
	public List<RcpnSttsModel> rtrvRcpnSttsList(Map<String, Object> param) {
		return selectList(PATH + "rtrvRcpnSttsList", param);
	}

	/* 검사처방목록 조회 */
	public List<ExmnPrscModel> rtrvExmnPrscList(Map<String, Object> param) {
		return selectList(PATH + "rtrvExmnPrscList", param);
	}

	/**
	 * 바코드검사처방목록 조회. 해당 바코드의 검사처방 목록을 조회.
	 * 
	 * @author khgkjg12 강현구A
	 * @date Dec 11, 2023
	 * @param param 바코드번호(검체번호)
	 * @return 해당 바코드에 묶인 검사처방 목록.
	 * @see IMSC_020100Service#rtrvBrcdExmnPrscList(Map)
	 */
	public List<BrcdExmnPrscModel> rtrvBrcdExmnPrscList(String param) {
		return selectList(PATH + "rtrvBrcdExmnPrscList", param);
	}

	/*
	 * 검체 출력 정보 조회.
	 */
	public BrcdModel rtrvBrcd(String param) {
		return selectOne(PATH + "rtrvBrcd", param);
	}

	/**
	 * 채번 생성.
	 * 
	 * @author khgkjg12 강현구A
	 * @date Mar 18, 2024
	 * @return
	 */
	public String crtnNoen() {
		String result = selectOne(PATH + "crtnNoen");
		if (result == null) {
			throw new MSLCRuntimeException(MSLCError.EXCT_IMPB_STAT.withBody("crtnNoen"));
		}
		return result;
	}

	/**
	 * 검사처방목록을 lock.<br>
	 * <br>
	 * 바코드 발행에 필요한 인자를 포함.<br>
	 * <b>검사처방 LOCK</b>
	 * 
	 * @author khgkjg12 강현구A
	 * @date Mar 19, 2024
	 * @param param
	 * @return
	 */
	public List<LockPrscModel> lockPrsc(Collection<? extends ExmnPrscPkModel> param) {
		return selectList(PATH + "lockPrsc", param);
	}

	/**
	 * 발행, 검사처방 업데티트<br>
	 * <br>
	 * <b>setContext() 필요</b>
	 * 
	 * @author khgkjg12 강현구A
	 * @date Mar 19, 2024
	 * @param param
	 * @param spcm_no
	 */
	public void issuPrsc(Collection<? extends ExmnPrscPkModel> param, String spcm_no) {
		if (update(PATH + "issuPrsc", session.getParamModel(param, spcm_no)) != param.size())
			throw new MSLCRuntimeException(MSLCError.EXCT_IMPB_STAT.withBody("issuPrsc"));
	}

	/**
	 * 검체처방내역에 바코드발행<br>
	 * <br>
	 * <b>setContext() 필요</b>
	 * 
	 * 실패할 수 없음. TX마다 고유한 채번 lock존재
	 * 
	 * @author khgkjg12 강현구A
	 * @date Jan 10, 2024
	 * @param spcmNo
	 * @return 정상수행여부.
	 */
	public void issuSpcm(SpcmModel param) {
		insert(PATH + "issuSpcm", session.getSessionMap(param));
	}

	/**
	 * 검체 LOCK<br>
	 * <br>
	 * <b>검체, 검사처방 LOCK</b>
	 * 
	 * @author khgkjg12 강현구A
	 * @date Mar 19, 2024
	 * @param param
	 * @return
	 */
	public List<LockSpcmWithPrscModel> lockSpcmWithPrsc(Collection<? extends SpcmPkModel> param) {
		return selectList(PATH + "lockSpcmWithPrsc", param);
	}

	/**
	 * 접수, 검체처방 내역 갱신. <br>
	 * <br>
	 * <b>setContext() 필요</b>
	 * 
	 * @author khgkjg12 강현구A
	 * @date Jan 10, 2024
	 * @param 검체번호 세트.
	 * @return
	 */
	public void rcpnSpcm(Collection<? extends SpcmPkModel> param) {
		if (update(PATH + "rcpnSpcm", session.getParamModel(param)) != param.size()) {
			throw new MSLCRuntimeException(MSLCError.EXCT_IMPB_STAT.withBody("rcpnSpcm"));
		}
	}

	/**
	 * 접수, 검사처방 갱신.<br>
	 * <br>
	 * <b>setContext() 필요</b>
	 * 
	 * @author khgkjg12 강현구A
	 * @date Jan 10, 2024
	 * @param param 검체번호를 포함한 세션 맵.
	 * @return
	 */
	public void rcpnPrsc(Collection<? extends ExmnPrscPkModel> param) {
		if (update(PATH + "rcpnPrsc", session.getParamModel(param)) != param.size()) {
			throw new MSLCRuntimeException(MSLCError.EXCT_IMPB_STAT.withBody("rcpnPrsc"));
		}
	}
	
	/**
	 * 
	 * @author khgkjg12 강현구A
	 * @date Mar 25, 2024
	 * @param param
	 * @return
	 */
	public List<LockSpcmModel> lockSpcm(Collection<?extends SpcmPkModel> param){
		return selectList(PATH+"lockSpcm", param);
	}

	/**
	 * 검체처방 내역 <br>
	 * <b>setContext() 필요</b>
	 * 
	 * @author khgkjg12 강현구A
	 * @date Mar 18, 2024
	 * @param param
	 */
	public void cnclRcpnSpcm(Collection<? extends SpcmPkModel> param) {
		if (update(PATH + "cnclRcpnSpcm", session.getParamModel(param)) != param.size()) {
			throw new MSLCRuntimeException(MSLCError.EXCT_IMPB_STAT.withBody("cnclSpcm"));
		}
	}

	/**
	 * <h1>검사처방에 접수취소</h1> <br>
	 * <b>setContext() 필요</b>
	 * 
	 * @author khgkjg12 강현구A
	 * @date Jan 10, 2024
	 * @param param 검체번호를 포함한 세션 맵. not empty.
	 * @return 정상수행여부.
	 */
	public void cnclRcpnPrsc(Collection<? extends SpcmPkModel> param) {
		update(PATH + "cnclRcpnPrsc", session.getParamModel(param));
	}

	/**
	 * 검체처방내역에 검사<br>
	 * </br>
	 * <b>setContext() 필요</b>
	 * 
	 * @author khgkjg12 강현구A
	 * @date Dec 7, 2023
	 * @param param 검체번호.
	 * @return 성공여부.
	 */
	public void exmnSpcm(Collection<? extends SpcmPkModel> param) {
		if (update(PATH + "exmnSpcm", session.getParamModel(param)) != param.size()) {
			throw new MSLCRuntimeException(MSLCError.EXCT_IMPB_STAT.withBody("exmnSpcm"));

		}
	}

	/**
	 * 검사처방 갱신. <br>
	 * <br>
	 * <b>setContext() 필요.</b>
	 * 
	 * @author khgkjg12 강현구A
	 * @date Mar 15, 2024
	 * @param param
	 * @return
	 */
	public void exmnPrsc(Collection<? extends ExmnPrscPkModel> param) {
		if (update(PATH + "exmnPrsc", session.getParamModel(param)) != param.size()) {
			throw new MSLCRuntimeException(MSLCError.EXCT_IMPB_STAT.withBody("exmnPrsc"));
		}
	}

	/**
	 * 검사결과내역 삽입, 검사.
	 * 
	 * @author khgkjg12 강현구A
	 * @date Mar 15, 2024
	 * @param param
	 */
	public List<ExmnRsltPkModel> exmnRslt(Collection<? extends ExmnRsltParamModel> param) {
		List<ExmnRsltPkModel> result = batch.selectBatch(PATH + "exmnRslt", session.getBatchParamModel(param));
		if (result.size() < param.size()) {
			throw new MSLCRuntimeException(MSLCError.EXCT_IMPB_STAT.withBody("exmnRslt"));
		}
		return result;
	}

	/**
	 * 위탁 검사 내역 밀어넣기. <b>위탁 검사가 없으면 아예 삽입이 안될수 있어서 검증 없음</b>
	 * 
	 * @author khgkjg12 강현구A
	 * @date Mar 15, 2024
	 * @param prscInfoList
	 * @param rsltList
	 */
	public void exmnEnts(Collection<? extends LockSpcmWithPrscModel> prscInfoList,
			Collection<? extends ExmnRsltPkModel> rsltList) {
		insert(PATH + "exmnEnts", session.getParamModel(prscInfoList, rsltList));
	}

	/**
	 * 검체의 검사결과-위탁의뢰 내역 진행 상태를 반환. <b>검체 LOCK</b>
	 * 
	 * @author khgkjg12 강현구A
	 * @date Mar 15, 2024
	 * @param param
	 * @return
	 */
	public List<LockSpcmWithEntsRsltModel> rtrvExmnRsltState(Collection<SpcmPkModel> param) {
		return selectList(PATH + "rtrvExmnRsltState", param);
	}

	/**
	 * 검시결과내역 삭제, 검사 취소.
	 * 
	 * @author khgkjg12 강현구A
	 * @date Dec 7, 2023
	 * @param param
	 */
	public void cnclExmnRslt(List<? extends ExmnRsltPkModel> param) {
		if (delete(PATH + "cnclExmnRslt", param) != param.size()) {
			throw new MSLCRuntimeException(MSLCError.EXCT_IMPB_STAT.withBody("cnclExmnRslt"));
		}
	}

	/**
	 * 위탁의뢰내역 삭제, 검사 취소.
	 * 
	 * <b>setContext() 필요.</b>
	 * 
	 * @author khgkjg12 강현구A
	 * @date Mar 15, 2024
	 * @param param
	 */
	public void cnclExmnEnts(Collection<? extends LockSpcmWithEntsRsltModel> param) {
		if (insert(PATH + "cnclExmnEnts", session.getParamModel(param)) != param.size()) {
			throw new MSLCRuntimeException(MSLCError.EXCT_IMPB_STAT.withBody("cnclExmnEnts"));
		}
	}

	/**
	 * 검체처방내역 업데이트, 검사 취소.
	 * 
	 * <b>setContext() 필요.</b>
	 * 
	 * 
	 * @author khgkjg12 강현구A
	 * @date Dec 7, 2023
	 * @param param 검체번호 집합.
	 * @return 성공여부.
	 */
	public void cnclExmnSpcm(Collection<? extends SpcmPkModel> param) {
		if (update(PATH + "cnclExmnSpcm", session.getParamModel(param)) != param.size()) {
			throw new MSLCRuntimeException(MSLCError.EXCT_IMPB_STAT.withBody("cnclExmnSpcm"));
		}
	}

	/**
	 * 검사처방 업데이트, 검사취소
	 * 
	 * <b>setContext() 필요.</b>
	 * 
	 * @author khgkjg12 강현구A
	 * @date Dec 7, 2023
	 * @param param 검체번호 집합.
	 * @return 성공여부.
	 */
	public void cnclExmnPrsc(Collection<? extends SpcmPkModel> param) {
		update(PATH + "cnclExmnPrsc", session.getParamModel(param));
	}

	/**
	 * 검사결과이력 테이블에 삭제 이력 삽입. 검사결과 삭제 이전에 호출해야함.
	 * 
	 * @author khgkjg12 강현구A
	 * @date Mar 13, 2024
	 * @param param
	 */
	public void cnclExmnRsltHstr(Collection<? extends ExmnRsltPkModel> param) {
		if (insert(PATH + "cnclExmnRsltHstr", session.getParamModel(param)) != param.size()) {
			throw new MSLCRuntimeException(MSLCError.EXCT_IMPB_STAT.withBody("cnclExmnRsltHstr"));
		}
	}
}
