package com.duzon.lulu.service.MSC.MSC_020000.mapper;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.duzon.common.mapper.LuluAbstractMapper;
import com.duzon.lulu.service.MSC.MSC_020000.model.EntsInstModel;
import com.duzon.lulu.service.MSC.MSC_020000.model.EntsReqModel;
import com.duzon.lulu.service.MSC.MSC_020000.model.RplyEntsParamModel;
import com.duzon.lulu.service.MSC.MSC_020000.model.EntsExmnPrscModel;
import com.duzon.lulu.service.MSC.MSC_020000.model.SCLExmnPrscModel;
import com.duzon.lulu.service.MSC.MSC_020000.model.TrmsAndCnclSuccessModel;
import com.duzon.lulu.service.MSC.MSC_020000.model.CreateEntsApiParamModel;
import com.duzon.lulu.service.MSC.MSC_020000.model.EntsApiParamModel;
import com.duzon.lulu.service.MSC.util.MSLC.MSLCError;
import com.duzon.lulu.service.MSC.util.MSLC.MSLCRuntimeException;
import com.duzon.lulu.service.MSC.util.MSLC.MSLCSessionHelper;

@Repository
public class MSC_020200Mapper extends LuluAbstractMapper {

	private final String PATH = "com.duzon.lulu.service.MSC.MSC_020000.mapper.MSC_020200Mapper.";

	@Autowired
	private MSLCSessionHelper session;

	/* 위탁기관목록 조회 */
	public List<EntsInstModel> rtrvEntsInstList(Map<String, Object> param) {
		return selectList(PATH + "rtrvEntsInstList", param);
	}

	public String rtrvSclKey() {
		return selectOne(PATH + "rtrvSclKey");
	}

	/* 위탁검사처방목록 조회 */
	public List<EntsExmnPrscModel> rtrvEntsExmnPrscList(Map<String, Object> param) {
		return selectList(PATH + "rtrvEntsExmnPrscList", param);
	}

	/* SCL검사처방 조회 */
	public SCLExmnPrscModel rtrvSCLExmnPrsc(Map<String, Object> param) {
		return selectOne(PATH + "rtrvSCLExmnPrsc", param);
	}

	/**
	 * 위탁 API 요청 파라미터 생성.
	 * 
	 * @author khgkjg12 강현구A
	 * @date Mar 14, 2024
	 * @param param
	 * @return
	 */
	public List<EntsApiParamModel> createEntsApiParam(CreateEntsApiParamModel param) {
		return selectList(PATH + "createEntsApiParam", param);
	}

	/**
	 * 전송, 위탁의뢰 내역 삽입.
	 * <b>setContext() 필요</b>
	 * @author khgkjg12 강현구A
	 * @date Mar 21, 2024
	 * @param param
	 */
	public void trmsEnts(List<TrmsAndCnclSuccessModel> param) {
		if(insert(PATH+"trmsEnts", session.getParamModel(param))!=param.size()) {
			throw new MSLCRuntimeException(MSLCError.EXCT_IMPB_STAT.withBody("trmsEnts"));
		}
	}

	/**
	 * 전송 취소, 위탁의뢰 내역 삽입.
	 * <b>setContext() 필요</b>
	 * @author khgkjg12 강현구A
	 * @date Mar 21, 2024
	 * @param param
	 */
	public void cnclTrmsEnts(List<TrmsAndCnclSuccessModel> param) {
		if (insert(PATH + "cnclTrmsEnts", session.getParamModel(param)) != param.size()) {
			throw new MSLCRuntimeException(MSLCError.EXCT_IMPB_STAT.withBody("cnclTrmsEnts"));
		}
	}
	/**
	 * 회신시, 위탁 의뢰 내역 테이블 삽입.
	 * <b>setContext() 필요</b>
	 * 
	 * @author khgkjg12 강현구A
	 * @date Mar 11, 2024
	 * @param param
	 * @return 없으면 null.
	 */
	public void rplyEnts(Collection<RplyEntsParamModel> param) {
		if(insert(PATH + "rplyEnts", session.getParamModel(param))<param.size()) {
			throw new MSLCRuntimeException(MSLCError.EXCT_IMPB_STAT.withBody("rplyEnts"));
		}
	}

	/**
	 * HTTP응답 기록(조건 없음)
	 * 
	 * @author khgkjg12 강현구A
	 * @date Mar 11, 2024
	 * @param param
	 * @return
	 */
	public long logEntsReq(EntsReqModel param) {
		return selectOne(PATH + "logEntsReq", param);
	}
}
