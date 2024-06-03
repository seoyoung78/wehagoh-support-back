package com.duzon.lulu.service.MSC.common.service;

import com.duzon.common.model.LuluResult;
import com.duzon.lulu.service.MSC.common.controller.CommonController;
import com.duzon.lulu.service.MSC.common.model.Common.*;
import com.duzon.lulu.service.MSC.common.service.impl.CommonServiceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface ICommonService {
    List<CommonCode> selectCommonCode(HashMap<String, Object> param);
    List<HashMap<String, Object>> selectDeptCode(HashMap<String, Object> param);

    List<SearchPatientResult> searchPatient(SearchPatientParam param);

	/**
	 * 미시행미완료 목록 조회
	 * 
	 * @author khgkjg12 강현구A
	 * @param param HashMap
	 * 
	 *              <pre>
	 *              1. 필수키
	 *              	- hope_exrm_dept_sqno_list : 희망검사실 목록.
	 *              	- prsc_clsf_cd : 처방분류코드.
	 *              2. 옵션키
	 *              	- uncmpl_yn : 미완료 여부.
	 *              	- endo_flag : 내시경 검사 여부.
	 *              </pre>
	 * 
	 * @return 미시행 또는 미완료 목록
	 * @throws Exception 필수값 없음.
	 * @see CommonController#rtrvUnactgUncmplList(HashMap)
	 * @see CommonServiceImpl#rtrvUnactgUncmplList(HashMap)
	 */
	List<UnactgUncmplModel> rtrvUnactgUncmplList(HashMap<String, Object> param) throws Exception;

	List<GetPrscListResult> getPrscList(GetPrscListParam param);

    LuluResult selectPatientInfo(HashMap<String, Object> param);
    LuluResult selectOnlyPatient(HashMap<String, Object> param);

    List<Pacs> selectPacsList(HashMap<String, Object> param);

    Hospital selectHspInfo(HashMap<String, Object> param);
    LuluResult selectHsptSetting(HashMap<String, Object> param);

    LuluResult updateExmnHopeDate(List<HashMap<String, Object>> param);

    List<DrModel> rtrvDrList();
    
    /**
     * 환자 기본정보 조회.
     * @author khgkjg12 강현구A
     * @date Dec 13, 2023
     * @param param
     * @return 환자기본정보.
     */
    PtBascInfoModel rtrvPtBascInfo(Map<String, Object> param);

	LuluResult selectUserAuth();
}
