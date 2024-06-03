package com.duzon.lulu.service.MSC.common.controller;

import com.duzon.common.model.LuluResult;
import com.duzon.lulu.annotation.HealthRestController;
import com.duzon.lulu.service.MSC.common.model.Common.*;
import com.duzon.lulu.service.MSC.common.service.ICommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/*
    진료지원 공통 코드
 */
@HealthRestController("/common")
public class CommonController {
    @Autowired
    ICommonService iCommonService;

    @PostMapping("/selectCommonCode")
    public Object selectCommonCode(@RequestBody HashMap<String, Object> param) throws Exception {
        return this.iCommonService.selectCommonCode(param);
    }

    @PostMapping("/selectDeptCode")
    public Object selectDeptCode(@RequestBody HashMap<String, Object> param) throws Exception { return this.iCommonService.selectDeptCode(param); }

    @PostMapping("/searchPatient")
    public Object searchPatient(@RequestBody SearchPatientParam param) throws Exception { return iCommonService.searchPatient(param); }

	/**
	 * 미시행미완료 조회.
	 * 
	 * @author khgkjg12 강현구A
	 * 
	 * @param param HashMap {@link com.duzon.lulu.service.MSC.common.service.impl.CommonServiceImpl#rtrvUnactgUncmplList(HashMap)}
	 * @return 미시행or미완료 목록
	 */
	@PostMapping("/rtrvUnactgUncmplList")
	public LuluResult rtrvUnactgUncmplList(@RequestBody HashMap<String, Object> param) {
		LuluResult luluResult = new LuluResult();
		try {
			luluResult.setResultData(iCommonService.rtrvUnactgUncmplList(param));
			luluResult.setResultCode(200);
		} catch (RuntimeException e) {
			e.printStackTrace();
			luluResult.setResultCode(500);
		} catch (Exception e) {
			luluResult.setResultMsg(e.getMessage());
			luluResult.setResultCode(400);
		}
		return luluResult;
	}
	/**
	 * 미시행미완료 조회. 이전버전 호환 API. 완벽히 새 API로 교체된 후 제거 예정.
	 * @author khgkjg12 강현구A
	 * @see #rtrvUnactgUncmplList(HashMap)
	 */
	@PostMapping("/searchUndoExm")
	public LuluResult searchUndoExm(@RequestBody HashMap<String, Object> param) {
		LuluResult luluResult = new LuluResult();
		try {
			HashMap<String, Object> convertedParam = new HashMap<>();
			convertedParam.put("hope_exrm_dept_sqno_list", param.get("in_hope_exrm_cd_list"));
			convertedParam.put("prsc_clsf_cd", param.get("in_prsc_clsf_cd"));
			convertedParam.put("uncmpl_yn", "E".equals(param.get("in_stat"))? "Y" : "N");
			convertedParam.put("endo_yn", param.get("endo_flag"));
			luluResult.setResultData(iCommonService.rtrvUnactgUncmplList(convertedParam));
			luluResult.setResultMsg("SUCCESS");
			luluResult.setResultCode(200);
		} catch (RuntimeException e) {
			e.printStackTrace();
			luluResult.setResultCode(500);
		} catch (Exception e) {
			luluResult.setResultMsg(e.getMessage());
			luluResult.setResultCode(400);
		}
		return luluResult;
	}

	@PostMapping("/getPrscList")
	public Object getPrscList(@RequestBody GetPrscListParam param) {
		return iCommonService.getPrscList(param);
	}

    @PostMapping("/selectPatientInfo")
    public Object selectPatientInfo(@RequestBody HashMap<String, Object> param) throws Exception { return this.iCommonService.selectPatientInfo(param); }

    @PostMapping("/selectOnlyPatient")
    public Object selectOnlyPatient(@RequestBody HashMap<String, Object> param) throws Exception { return this.iCommonService.selectOnlyPatient(param); }

    @PostMapping("/selectPacsList")
    public Object selectPacsList(@RequestBody HashMap<String, Object> param) throws Exception { return this.iCommonService.selectPacsList(param); }

    @PostMapping("/selectHspInfo")
    public Object selectHspInfo(@RequestBody HashMap<String, Object> param) throws Exception { return this.iCommonService.selectHspInfo(param); }

    @PostMapping("/selectHsptSetting")
    public Object selectHsptSetting(@RequestBody HashMap<String, Object> param) throws Exception { return this.iCommonService.selectHsptSetting(param); }

    @PostMapping("/updateExmnHopeDate")
    public Object updateExmnHopeDate(@RequestBody List<HashMap<String, Object>> param) throws Exception { return this.iCommonService.updateExmnHopeDate(param); }

    /**
     * 의사목록조회.
     * @author khgkjg12 강현구A
     * @date Dec 13, 2023
     * @return
     */
    @PostMapping("/rtrvDrList")
    public LuluResult rtrvDrList() {
    	LuluResult luluResult = new LuluResult();
    	try {
    		luluResult.setResultData(iCommonService.rtrvDrList());
    		luluResult.setResultCode(200);
    	}catch(Exception e) {
    		e.printStackTrace();
    		luluResult.setResultCode(400);
    	}
    	return luluResult;
    }
    /**
     * 환자기본정보 조회
     * @author khgkjg12 강현구A
     * @date Dec 13, 2023
     * @return
     */
    @PostMapping("/rtrvPtBascInfo")
    public LuluResult rtrvPtBascInfo(@RequestBody HashMap<String, Object> param) {
    	LuluResult luluResult = new LuluResult();
    	try {
    		luluResult.setResultData(iCommonService.rtrvPtBascInfo(param));
    		luluResult.setResultCode(200);
    	}catch(Exception e) {
    		e.printStackTrace();
    		luluResult.setResultCode(400);
    	}
    	return luluResult;
    }

	@PostMapping("/selectUserAuth")
	public LuluResult selectUserAuth() throws Exception { return this.iCommonService.selectUserAuth(); }
}
