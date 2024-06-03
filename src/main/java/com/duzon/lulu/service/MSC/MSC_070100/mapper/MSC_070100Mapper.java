package com.duzon.lulu.service.MSC.MSC_070100.mapper;

import com.duzon.common.mapper.LuluAbstractMapper;
import com.duzon.lulu.service.MSC.MSC_070100.model.AccRsltModel;
import com.duzon.lulu.service.MSC.MSC_070100.model.CardModel;
import com.duzon.lulu.service.MSC.MSC_070100.model.MseRsltModel;
import com.duzon.lulu.service.MSC.MSC_070100.model.MsfRsltModel;
import com.duzon.lulu.service.MSC.MSC_070100.model.MslRsltModel;
import com.duzon.lulu.service.MSC.MSC_070100.model.MsrRsltModel;
import com.duzon.lulu.service.MSC.MSC_070100.service.impl.MSC_070100Service;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class MSC_070100Mapper extends LuluAbstractMapper {

	private static final String PATH = "com.duzon.lulu.service.MSC.MSC_070100.mapper.MSC_070100Mapper.";

	/**
	 * 카드목록 조회 쿼리.
	 * 
	 * @author khgkjg12 강현구A
	 * @date Dec 15, 2023
	 * @param 조회 조건.
	 * @return 카드목록.
	 */
	public List<CardModel> rtrvCardList(Map<String, Object> param) {
		return selectList(PATH + "rtrvCardList", param);
	}

	/**
	 * 진단검사결과목록 조회 쿼리.
	 * 
	 * @author khgkjg12 강현구A
	 * @date Dec 18, 2023
	 * @param param { rcpn_no, cndt_dy, keyword }
	 * @return 진단검사결과 목록.
	 * @see MSC_070100Service#rtrvMslRsltList(Map)
	 */
	public List<MslRsltModel> rtrvMslRsltList(Map<String, Object> param) {
		return selectList(PATH + "rtrvMslRsltList", param);
	}

	/**
	 * 기능검사결과목록 조회 쿼리.
	 * 
	 * @author khgkjg12 강현구A
	 * @date Dec 18, 2023
	 * @param param {rcpn_no, cndt_dy, keyword}
	 * @return 기능검사결과목록
	 * @see MSC_070100Service#rtrvMsfRsltList(Map)
	 */
	public List<MsfRsltModel> rtrvMsfRsltList(Map<String, Object> param) {
		return selectList(PATH + "rtrvMsfRsltList", param);
	}
	

	/**
	 * 영상검사결과목록 조회 쿼리.
	 * 
	 * @author khgkjg12 강현구A
	 * @date Dec 18, 2023
	 * @param param {rcpn_no, cndt_dy, keyword}
	 * @return 영상검사결과목록
	 * @see MSC_070100Service#rtrvMsrRsltList(Map)
	 */
	public List<MsrRsltModel> rtrvMsrRsltList(Map<String, Object> param) {
		return selectList(PATH + "rtrvMsrRsltList", param);
	}
	
	/**
	 * 내시경검사결과목록 조회 쿼리.
	 * @author khgkjg12 강현구A
	 * @date Dec 18, 2023
	 * @param param {rcpn_no, cndt_dy, keyword}
	 * @return 내시경검사결과목록
	 * @see MSC_070100Service#rtrvMseRsltList(Map)
	 */
	public List<MseRsltModel> rtrvMseRsltList(Map<String, Object> param){
		return selectList(PATH+"rtrvMseRsltList", param);
	}
	

	
	/**
	 * 누적검사결과목록 조회 쿼리.
	 * @author khgkjg12 강현구A
	 * @date Dec 18, 2023
	 * @param param {pid, from, to, keyword}
	 * @return 누적검사결과목록
	 * @see MSC_070100Service#rtrvAccRsltList(Map)
	 */
	public List<AccRsltModel> rtrvAccRsltList(Map<String, Object> param){
		return selectList(PATH+"rtrvAccRsltList", param);
	}
}
