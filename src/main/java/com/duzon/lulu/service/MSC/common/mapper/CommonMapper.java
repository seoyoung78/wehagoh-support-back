package com.duzon.lulu.service.MSC.common.mapper;

import com.duzon.lulu.service.MSC.common.model.Common.*;

import org.apache.ibatis.annotations.Mapper;

import java.util.*;

@Mapper
public interface CommonMapper {
    // 공통코드 조회
    List<CommonCode> selectCommonCode(HashMap<String, Object> param);

    // 부서코드 조회
    List<HashMap<String, Object>> selectDeptCode(HashMap<String, Object> param);

    List<SearchPatientResult> searchPatient(SearchPatientParam param);
    
	// 미시행미완료 목록 조회
	List<UnactgUncmplModel> rtrvUnactgUncmplList(HashMap<String, Object> param);

	// 처방 목록 조회
	List<GetPrscListResult> getPrscList(GetPrscListParam param);

	PatientInfo selectPatientInfo(HashMap<String, Object> param);
	List<PatientInfo> selectOnlyPatient(HashMap<String, Object> param);

	// PACS 목록 조회
	List<Pacs> selectPacsList(HashMap<String, Object> param);

	// 병원 정보 조회
	Hospital selectHspInfo(HashMap<String, Object> param);

	// 병원 정보 설정 조회
	List<HsptSetting> selectHsptSetting(HashMap<String, Object> param);

	// 검사 희망일 변경
	int updateExmnHopeDate(HashMap<String, Object> param);

	//의사 목록 조회
	List<DrModel> rtrvDrList(HashMap<String, Object> param);
	
	/**
	 * 환자기본정보 조회.
	 * @author khgkjg12 강현구A
	 * @date Dec 13, 2023
	 * @param param pid;
	 * @return 환자기본정보.
	 */
	PtBascInfoModel rtrvPtBascInfo(Map<String, Object> param);

	// 의무기록지 사용자 권한 목록 조회
	List<Authority> selectUserAuth(HashMap<String, Object> param);
}
