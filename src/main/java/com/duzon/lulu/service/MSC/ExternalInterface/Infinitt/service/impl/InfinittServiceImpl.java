package com.duzon.lulu.service.MSC.ExternalInterface.Infinitt.service.impl;

import com.duzon.common.model.LuluResult;
import com.duzon.common.util.ValidationUtil;
import com.duzon.lulu.service.MSC.ExternalInterface.Infinitt.mapper.InfinittMapper;
import com.duzon.lulu.service.MSC.ExternalInterface.Infinitt.model.LogOrderInfo;
import com.duzon.lulu.service.MSC.ExternalInterface.Infinitt.service.IInfinittService;
import com.duzon.lulu.service.MSC.ExternalInterface.Infinitt.model.Order;
import com.duzon.lulu.service.MSC.common.service.IExamService;
import com.duzon.lulu.service.MSC.util.HealthSessionContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;


@Service
@Slf4j
public class InfinittServiceImpl implements IInfinittService {


    @Autowired
    InfinittMapper mapper;

    @Autowired
    IExamService iExamService;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private HealthSessionContext healthSessionContext;

    @Override
    public Object getOrderInfo(HashMap<String, String> param) throws Exception {
        try {
            String[] requirementKeys = { "cno", "organization_id", "co_code" };
            LuluResult result = ValidationUtil.checkContainsKeysValue(requirementKeys, param);
            if (result.getResultCode() != 200) return result;

            healthSessionContext.setContext();
            String cryptKey = healthSessionContext.getCrypt_key_data(Long.parseLong(request.getHeader("Company-No")));
            param.put("cryptKey", cryptKey);
            return returnResponse("Success", HttpServletResponse.SC_OK, mapper.getOrderInfoList(param));
        } catch (Exception e) {
            log.info("error : " + e.getMessage());
            return returnResponse("Error", HttpServletResponse.SC_INTERNAL_SERVER_ERROR, null);
        }
    }

    @Override
    public Object getUerInfo(HashMap<String, String> param)  {
        try {
            String[] requirementKeys = { "cno", "organization_id", "co_code" };
            LuluResult result = ValidationUtil.checkContainsKeysValue(requirementKeys, param);
            if (result.getResultCode() != 200) return result;

            return returnResponse("Success", HttpServletResponse.SC_OK, mapper.getUserInfoList(param));
        } catch (Exception e) {
            log.info("error : " + e.getMessage());
            return returnResponse("Error", HttpServletResponse.SC_INTERNAL_SERVER_ERROR, null);
        }
    }

    @Override
    public Object updateOrderInfo(HashMap<String, String> param)  {
        try {
            String conv = param.get("body");
            HashMap<String, Object> deconv = (HashMap<String, Object>) convertStringToMap(conv).get("data");
            List<HashMap<String,String>> deconvSplit = (List<HashMap<String,String>>)deconv.get("info");
            ObjectMapper oMapper = new ObjectMapper();
            List<LogOrderInfo> resultConv = oMapper.convertValue(deconvSplit, oMapper.getTypeFactory().constructCollectionType(List.class, LogOrderInfo.class));

            String[] requirementKeys = { "cno", "organization_id", "info" };
            LuluResult result = ValidationUtil.checkContainsKeysValue(requirementKeys, deconv);
            if (result.getResultCode() != 200) return result;

            return returnResponse("Success", HttpServletResponse.SC_OK, mapper.updateOrderInfo(resultConv));
        } catch (Exception e) {
            return returnResponse("Error : " + e.getMessage(), HttpServletResponse.SC_INTERNAL_SERVER_ERROR, null);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object getStudyInfo(HashMap<String, String> param) {// 필수 파라미터 체크
        String[] requirementKeys = { "cno", "organization_id", "ACCESSION_NO", "STUDY_DTTM", "pid", "STUDY_INSTANCE_UID", "STUDY_STAT" };
        LuluResult result = ValidationUtil.checkContainsKeysValue(requirementKeys, param);
        if (result.getResultCode() != 200) return result;

        try {
            Order order = new Order();
            String[] data = param.get("STUDY_INSTANCE_UID").split("-");
            order.setPrsc_date(data[0]);
            order.setPrsc_sqno(Integer.parseInt(data[1]));
            order.setCno(param.get("cno"));
            order.setAccession_no(param.get("ACCESSION_NO"));
            order.setStudy_dttm(param.get("STUDY_DTTM"));
            order.setPid(param.get("pid"));
            order.setAuthorization(param.get("authorization"));
            order.setCo_code(param.get("co_code"));
            order.setType("PacsAlert");
            // 1. 검사상태에 따른 조건 NW(검사중), CA(취소)
            if (param.get("STUDY_STAT").equals("NW")) {
                int uresult = updateSave(order);
                if (uresult>0){
                    result.setResultCode(200);
                    result.setResultMsg("Success");
                    result.setResultData(uresult);
                    sendPacsNoti(order);
                }else {
                    result.setResultCode(500);
                    result.setResultMsg("Update fail");
                    result.setResultData(uresult);
                }

            }else {
                result.setResultCode(500);
                result.setResultMsg("Update fail: CA");
                result.setResultData(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (e.getMessage().equals("알림톡")) {
                result.setResultCode(400);
                result.setResultMsg("알림톡 전송 오류");
            } else {
                result.setResultCode(401);
                result.setResultMsg("인증 오류");
            }
        }
        return result;
//        return mapper.getStudyInfo(param);
    }

    @Transactional
    @Override
    public Object saveReport(HashMap<String, String> param) throws Exception {
        String[] requirementKeys = { "cno", "organization_id", "co_code", "study_instance_uid", "pacs_no", "prsc_prgr_stat_cd", "create_rptr_id", "create_rptg_dt", "approve_rptr_id", "approve_rptg_dt", "revice_rptr_id", "revice_rptg_dt","iptn_rslt","pid" };
        LuluResult result = ValidationUtil.checkContainsKeysValue(requirementKeys, param);
        if (result.getResultCode() != 200) return result;
        int historyResult = 0;

        try {

            Order order = new Order();
            // getHistoryApprove2Object
            order = getHistoryApprove2Object(param, order);
            int interpretResult = updateInterpret(order);
            if(interpretResult > 0){
                historyResult += mapper.insertHistory(getHistoryCreateObject(param, order)); //getHistoryCreateObject(param, order)
                historyResult += mapper.insertHistory(getHistoryApproveObject(param, order));
                historyResult += mapper.insertHistory(getHistoryApprove2Object(param, order));
            }


            if(historyResult == 3 ) {
                sendPacsNoti(order);
                result.setResultCode(200);
                result.setResultMsg("Success");
                return result;
            }

        } catch (Exception e) {
            if (e.getMessage().equals("알림톡")) {
                result.setResultCode(400);
                result.setResultMsg("알림톡 전송 오류");
            } else {
                result.setResultCode(401);
                result.setResultMsg("인증 오류");
            }
        }
        result.setResultCode(500);
        result.setResultMsg("서버 에러");
        return result;
    }

    private Order getHistoryCreateObject(HashMap<String, String> param, Order order) {
        order.setDocument_dttm(param.get("create_rptg_dt"));
        order.setAuthor_name(param.get("create_rptr_id"));
        return order;
    }

    private Order getHistoryApproveObject(HashMap<String, String> param, Order order) {
        order.setDocument_dttm(param.get("approve_rptg_dt"));
        order.setAuthor_name(param.get("approve_rptr_id"));
        return order;
    }

    private Order getHistoryApprove2Object(HashMap<String, String> param, Order order) {
        String[] data = param.get("study_instance_uid").split("-");
        order.setPrsc_date(data[0]);
        order.setCo_code(param.get("co_code"));
        order.setPrsc_sqno(Integer.parseInt(data[1]));
        order.setCno(param.get("cno"));
        order.setAccession_no(param.get("pacs_no"));
        order.setPrsc_prgr_stat_cd(param.get("prsc_prgr_stat_cd"));
        order.setAuthorization(param.get("authorization"));
        order.setDocument_dttm(param.get("revice_rptg_dt"));
        order.setAuthor_name(param.get("revice_rptr_id"));
        order.setFinding(param.get("iptn_rslt"));
        order.setPid(param.get("pid"));
        order.setType("Interpret");

        return order;
    }


    @Override
    @Transactional
    public Integer updateSave(Order param) throws Exception {
        int result = mapper.updateSave(param);
        if (result > 0) {
            mapper.updateConduct(param);

            if (param.getPrsc_prgr_stat_cd().equals("E")) {
                param.setHstr_stat_cd("1");
            }else{
                param.setHstr_stat_cd("2");
            }
            return mapper.insertHistory(param);
        }
        return 0;
    }

    @Override
    @Transactional
    public int updateInterpret(Order param) throws Exception {

        // reviseid, reveise_dttm, 판독결과문을 업데이트
        int result = mapper.updateInterpret(param);
        if (result > 0) {
            // 영상검사결과(create, approve, revise 모두 추가해야된다.)
            mapper.updateConduct(param);
            // 검사결과 일련번호 select 후에 setExmnRsltSqno
            param.setExmnRsltSqno(mapper.selectExmnRsltSqno(param));
            param.setHstr_stat_cd("2");// 이력 1: 추가 2:수정 3: 삭제
            // 최종보고 (상태값이 0일때, insertResult)
            return mapper.insertResult(param);
        }
        return 0;
    }
    // B: 검사대기, C: 접수, E: 검사중, M: 판독중 ,N: 최종보고
    @Override
    public Object sendIan(HashMap<String, String> param) throws Exception {
        String[] requirementKeys = { "cno", "organization_id", "accession_no", "study_dttm", "pid" };
        LuluResult result = ValidationUtil.checkContainsKeysValue(requirementKeys, param);
        if (result.getResultCode() != 200) return result;

        try {
            Order order = new Order();
            order.setCno(param.get("cno").toString());
            order.setAccession_no(param.get("accession_no").toString());
            order.setStudy_dttm(param.get("study_dttm"));
            order.setPid(param.get("pid").toString());
            order.setAuthorization(param.get("authorization").toString());

            result.setResultCode(200);

        } catch (Exception e) {

        }
        return null;
    }

    @Transactional(propagation = Propagation.NESTED)
    private LuluResult sendPacsNoti(Order param) throws Exception {
        HashMap<String, Object> notiData = mapper.selectNotiData(param);
        notiData.put("authorization", param.getAuthorization());
        notiData.put("exrmClsfCd", "R");
        notiData.put("reqCompany", "INFINITT");
        notiData.put("reqUser", "INFINITT");
        notiData.put("cno", param.getCno());
        notiData.put("type", param.getType());
        return iExamService.sendNoti(notiData);
    }

    private HashMap<String, Object> convertStringToMap(String conv) throws Exception{
        ObjectMapper mapper = new ObjectMapper();
        HashMap<String, Object> result = mapper.readValue(conv, HashMap.class);
        return result;
    }


    private Object returnResponse(String message, int code, Object data) {
        LuluResult result = new LuluResult();
        result.setResultMsg(message);
        result.setResultCode(code);
        result.setResultData(data);
        return result;
    }


}
