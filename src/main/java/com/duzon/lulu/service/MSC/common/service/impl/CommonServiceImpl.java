package com.duzon.lulu.service.MSC.common.service.impl;

import com.duzon.common.model.LuluResult;
import com.duzon.common.util.ValidationUtil;
import com.duzon.lulu.service.MSC.common.mapper.CommonMapper;
import com.duzon.lulu.service.MSC.common.model.Common.*;
import com.duzon.lulu.service.MSC.common.service.ICommonService;
import com.duzon.lulu.service.MSC.util.HealthSessionContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class CommonServiceImpl implements ICommonService {
    @Autowired
    CommonMapper commonMapper;
    @Autowired
    private HealthSessionContext healthSessionContext;
    
    @Override
    public List<CommonCode> selectCommonCode(HashMap<String, Object> param) {
        healthSessionContext.setContext();
        healthSessionContext.setSessionForHashMap(param);
        return commonMapper.selectCommonCode(param);
    }

    @Override
    public List<HashMap<String, Object>> selectDeptCode(HashMap<String, Object> param) {
        healthSessionContext.setContext();
        healthSessionContext.setSessionForHashMap(param);
        return commonMapper.selectDeptCode(param);
    }

    @Override
    public List<SearchPatientResult> searchPatient(SearchPatientParam param) {
        healthSessionContext.setContext();
        healthSessionContext.setSessionForModel(param);
        return commonMapper.searchPatient(param);
    }

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
	 *              	- uncmpl_yn : 미완료 여부. 'Y'이면 미완료. 아니거나 없으면 미시행.
	 *              	- endo_flag : 내시경 검사 여부. 'Y'이면 내시경. 기능 검사 처방 ( prsc_clsf_cd = 'C3') 이외의 검사는 무시.
	 *              </pre>
	 * 
	 * @return 미시행 또는 미완료 목록
	 * @throws Exception 필수값 없음.
	 * 
	 * @see ICommonService#rtrvUnactgUncmplList(HashMap)
	 */
	@Override
	public List<UnactgUncmplModel> rtrvUnactgUncmplList(HashMap<String, Object> param) throws Exception {
		LuluResult luluResult = ValidationUtil
				.checkContainsKeysValue(new String[] { "hope_exrm_dept_sqno_list", "prsc_clsf_cd" }, param);
		if (luluResult.getResultCode() != 200)
			throw new Exception(luluResult.getResultMsg());
		healthSessionContext.setContext();
		healthSessionContext.setSessionForHashMap(param);
		return commonMapper.rtrvUnactgUncmplList(param);
	}

	@Override
	public List<GetPrscListResult> getPrscList(GetPrscListParam param) {
		healthSessionContext.setContext();
		healthSessionContext.setSessionForModel(param);
		return commonMapper.getPrscList(param);
	}

    @Override
    public LuluResult selectPatientInfo(HashMap<String, Object> param) {
        healthSessionContext.setContext();
        // 필수 파라미터 체크
        String[] requirementKeys = { "rcpn_sqno" };
        LuluResult checkParamResult = ValidationUtil.checkContainsKeysValue(requirementKeys, param);
        if(checkParamResult.getResultCode() != 200) {
            return checkParamResult;
        }

        LuluResult result = new LuluResult();

        healthSessionContext.setSessionForHashMap(param);
        result.setResultData(commonMapper.selectPatientInfo(param));
        result.setResultCode(200);;

        return result;
    }

    @Override
    public LuluResult selectOnlyPatient(HashMap<String, Object> param) {
        // 필수 파라미터 체크
        String[] requirementKeys = { "pid" };
        LuluResult result = ValidationUtil.checkContainsKeysValue(requirementKeys, param);
        if(result.getResultCode() != 200) {
            return result;
        }

        healthSessionContext.setContext();
        healthSessionContext.setSessionForHashMap(param);

        result.setResultData(commonMapper.selectOnlyPatient(param));

        return result;
    }

    @Override
    public List<Pacs> selectPacsList(HashMap<String, Object> param) {
        healthSessionContext.setContext();
        healthSessionContext.setSessionForHashMap(param);
        return commonMapper.selectPacsList(param);
    }

    @Override
    public Hospital selectHspInfo(HashMap<String, Object> param) {
        healthSessionContext.setContext();
        healthSessionContext.setSessionForHashMap(param);
        return commonMapper.selectHspInfo(param);
    }

    @Override
    public LuluResult selectHsptSetting(HashMap<String, Object> param) {
        String[] requirementKeys = { "cdList", "date" };
        LuluResult result = ValidationUtil.checkContainsKeysValue(requirementKeys, param);
        if (result.getResultCode() != 200) {
            return result;
        }
        healthSessionContext.setContext();
        healthSessionContext.setSessionForHashMap(param);

        result.setResultData(this.commonMapper.selectHsptSetting(param));

        return result;
    }

    @Transactional
    @Override
    public LuluResult updateExmnHopeDate(List<HashMap<String, Object>> param) {
        healthSessionContext.setContext();
        for(HashMap<String, Object> exmn: param) {
            healthSessionContext.setSessionForHashMap(exmn);
            commonMapper.updateExmnHopeDate(exmn);
        }
        LuluResult result = new LuluResult();
        result.setResultCode(0);
        return result;
    }

	@Override
	public List<DrModel> rtrvDrList() {
		HashMap<String, Object> param = new HashMap<>();
		healthSessionContext.setContext();
		healthSessionContext.setSessionForHashMap(param);
		return commonMapper.rtrvDrList(param);
	}

	@Override
	public PtBascInfoModel rtrvPtBascInfo(Map<String, Object> param) {
		healthSessionContext.setContext();
		healthSessionContext.setSessionForHashMap(param);
		return commonMapper.rtrvPtBascInfo(param);
	}

    @Override
    public LuluResult selectUserAuth() {
        HashMap<String, Object> param = new HashMap<>();
        healthSessionContext.setContext();
        healthSessionContext.setSessionForHashMap(param);

        LuluResult result = new LuluResult();
        result.setResultCode(200);
        result.setResultData(commonMapper.selectUserAuth(param));

        return result;
    }

}
