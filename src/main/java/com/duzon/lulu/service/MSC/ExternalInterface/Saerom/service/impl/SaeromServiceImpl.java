package com.duzon.lulu.service.MSC.ExternalInterface.Saerom.service.impl;

import com.duzon.common.model.LuluResult;
import com.duzon.lulu.common.service.InnerGatewayService;
import com.duzon.lulu.service.MSC.ExternalInterface.Saerom.model.LmerstData;
import com.duzon.lulu.service.MSC.MSC_020000.mapper.MSC_020300Mapper;
import com.duzon.lulu.service.MSC.ExternalInterface.Saerom.mapper.SaeromMapper;
import com.duzon.lulu.service.MSC.ExternalInterface.Saerom.model.CrexmprsntData;
import com.duzon.lulu.service.MSC.ExternalInterface.Saerom.model.ExmprsData;
import com.duzon.lulu.service.MSC.ExternalInterface.Saerom.service.ISaeromService;
import com.duzon.lulu.service.MSC.util.CALC.CalculatorInfoFactory;
import com.duzon.lulu.service.MSC.MSC_020000.service.impl.MSC_020300Service;
import com.duzon.lulu.service.MSC.util.HealthSessionContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Service
@Slf4j
public class SaeromServiceImpl implements ISaeromService {

    @Autowired
    private HealthSessionContext healthSessionContext;

    @Autowired
    private SaeromMapper saeromMapper;

    @Autowired
    private MSC_020300Mapper msc_020300Mapper;

    @Autowired
    private MSC_020300Service mssc020300Service;
    // 검사결과 업데이트는 해당 서비스를 통해 로직을 태우면 된다. 검사결과업데이트에서 exmn_cd는 list로 한번 감아서 요청보내기, iptnRslt 서비스 호출할때 트랜잭션 어노테이션 집어넣기
    // msc0203000 서비스 런타임에러발생이 될때 서비스에서 try-catch하지말고 컨트롤러에서 하기-> @Transaction이 try-catch로 하게 되면 런타임익셉션을 잡지못한다. try-catch를 없애야 트랜잭션이 정상작동
    @Autowired
    private InnerGatewayService innerGatewayService;

    @Autowired
    CalculatorInfoFactory calculatorInfoFactory;



    /** 접수 정보 조회 */
    @Override
    public Object getExmprsList(HashMap<String, String> param){
        try {
            healthSessionContext.setContext();
            healthSessionContext.setSessionForHashMap(param);

            // validation check
            if (!isEmptyCheck(param.get("exmn_date_from")) || !isEmptyCheck(param.get("exmn_date_to")))
                return returnResponse("파라미터 필수 값 누락", HttpServletResponse.SC_NO_CONTENT, false);

            List<ExmprsData> result = saeromMapper.getExmprsList(setParamData(getMap(param, convertStringToList(param.get("exmn_cd")))));

            return (result.size() > 0) ? returnResponse("SUCCESS", HttpServletResponse.SC_OK, result) : returnResponse("조회 결과 없음", HttpServletResponse.SC_NO_CONTENT, false);
        } catch (Exception e) {
            log.info("error : " + e.getMessage());
            return returnResponse("error : "+ e.getMessage(), HttpServletResponse.SC_INTERNAL_SERVER_ERROR, false);
        }
    }

    private List<String> convertStringToList(String prscCd ) {
        if (prscCd == null) return new ArrayList<>();
        String[] strPrscCd = prscCd.split(",");
        List<String> prscCdList = new ArrayList<>(Arrays.asList(strPrscCd));
        return prscCdList;
    }

    private HashMap getMap(Map<String, String> param, List<String> prscCdList) {
        HashMap parameters = new HashMap<>();
        parameters.put("cno", param.get("cno"));
        parameters.put("pid",param.get("pid"));
        parameters.put("exmn_date_from",param.get("exmn_date_from"));
        parameters.put("exmn_date_to",param.get("exmn_date_to"));
        parameters.put("exmn_cd", prscCdList);
        parameters.put("spcm_no",param.get("spcm_no"));
        return parameters;
    }

    /** 검사 결과 조회 2 */
    @Override
    public Object getLmerstList(HashMap<String, String> param)  {
        try {
            healthSessionContext.setContext();
            healthSessionContext.setSessionForHashMap(param);
            // validation check
            if (!isEmptyCheck(param.get("spcm_no")) || !isEmptyCheck(param.get("exmn_cd")))
                return returnResponse("파라미터 필수 값 누락", HttpServletResponse.SC_NO_CONTENT, false);

            List<LmerstData> result = saeromMapper.getLmerstList(setParamData(getMapByLmerstList(param, convertStringToList(param.get("exmn_cd")))));
            return result.size() > 0 ? returnResponse("SUCCESS", HttpServletResponse.SC_OK, result) : returnResponse("조회 결과 없음", HttpServletResponse.SC_NO_CONTENT, false);
        } catch (Exception e) {
            return returnResponse(e.getMessage(), HttpServletResponse.SC_INTERNAL_SERVER_ERROR, false);
        }
    }

    private HashMap getMapByLmerstList(Map<String, String> param, List<String> exmnCdList) {
        HashMap parameters = new HashMap<>();
        parameters.put("cno", param.get("cno"));
        parameters.put("spcm_no",param.get("spcm_no"));
        parameters.put("exmn_cd", exmnCdList);
        return parameters;
    }


    /** 검사결과 업데이트 **/
    @Override
    public Object setLmerstData(HashMap param){
        LuluResult result = new LuluResult();
        try {
            if (!isEmptyCheck(param.get("spcm_no")) || !isEmptyCheck(param.get("exmn_cd"))|| !isEmptyCheck(param.get("exmn_eqpm_cd"))|| !isEmptyCheck(param.get("exmn_rslt_valu")))
                return returnResponse("파라미터 필수 값 누락", HttpServletResponse.SC_NO_CONTENT, false);
//            String exmn_value = calculatorInfoFactory.getResultValue(param);
//            param.put("exmn_rslt_valu", exmn_value);
            List<HashMap<String, Object>> resParam = new ArrayList<>();
            resParam.add(param);
            result = mssc020300Service.iptnRslt(resParam);
            result.setResultData(resParam.size());
            result.setResultMsg("SUCCESS");
            return result;
        } catch (Exception e) {
            result.setResultData(0);
            result.setResultMsg("FAIL");
            return result;
        }
    }





    private HashMap setParamData(HashMap param) {
        healthSessionContext.setContext();
        healthSessionContext.setSessionForHashMap(param);
        return param;
    }

    private Object returnResponse(String message, int code, Object data) {
        LuluResult result = new LuluResult();
        result.setResultMsg(message);
        result.setResultCode(code);
        result.setResultData(data);
        return result;
    }

    private boolean isEmptyCheck(Object param) {
        if (ObjectUtils.isEmpty(param)) return false;
        return true;
    }

	@Override
	public LuluResult rtrvRptgList(HashMap<String, Object> param) {
		LuluResult result = new LuluResult();
		if (param.get("pid") == null || param.get("rcpn_no") == null) {
			result.setResultCode(400);
			return result;
		}
		try {
			result.setResultData(saeromMapper.rtrvRptgList(param));
			result.setResultCode(200);
		} catch (Exception e) {
			result.setResultCode(500);
		}
		return result;
	}
}
