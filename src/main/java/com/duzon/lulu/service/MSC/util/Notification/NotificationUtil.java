package com.duzon.lulu.service.MSC.util.Notification;

import com.duzon.common.model.LuluResult;
import com.duzon.common.model.NotiMessage;
import com.duzon.common.util.StringUtil;
import com.duzon.lulu.bean.GlobalProperties;
import com.duzon.lulu.common.service.InnerGatewayService;
import com.duzon.lulu.service.MSC.common.model.Exam.*;
import com.duzon.lulu.service.MSC.util.HealthSessionContext;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class NotificationUtil {

    @Autowired
    private HealthSessionContext healthSessionContext;

    @Autowired
    private GlobalProperties globalProperties;

    @Autowired
    private InnerGatewayService innerGatewayService;

    /*
        DC 요청 알림
     */
    private void setPrscDc (Notification noti) {
        String msg = noti.getReq_msg() + "D/C요청을 다음과 같이 요청하였습니다.";
        noti.setReq_title("D/C요청 알림");
        noti.setReq_msg(msg);
        noti.setReq_url("clinicdiagnose/#/MED010100");
        noti.setMsgCd("CP0001");
    }

    /*
        처방요청 알림
     */
    private void setPrscRequest(Notification noti) {
        String msg = noti.getReq_msg() + "처방요청을 다음과 같이 요청하였습니다.";
        noti.setReq_title("처방요청 알림");
        noti.setReq_msg(msg);
        noti.setReq_url("clinicdiagnose/#/MED010100");
        noti.setMsgCd("CP0002");
    }

    /*
        최종보고 알림
     */
    private void setFinal (Notification noti) {
        String msg = noti.getReq_msg() + "최종보고가 수신되었습니다.";
        noti.setReq_title("최종보고 알림");
        noti.setReq_msg(msg);
        noti.setReq_url("clinicsupport/#/MSC_010100");
        noti.setMsgCd("CP0003");
    }

    /*
        보고취소 알림
     */
    private void setFinalCancel (Notification noti) {
        String msg = noti.getReq_msg() + "최종보고가 취소되었습니다.";
        noti.setReq_title("최종보고 취소 알림");
        noti.setReq_msg(msg);
        noti.setReq_url(null);
        noti.setMsgCd("CP0004");
    }

    /*
        검사상태값 변경 알림
     */
    private Notification setExmnProgress (Progress param) {
        Notification noti = new Notification();

        String exrmClsfCd = param.getExrmClsfCd().toUpperCase();
        String exmnClsfNm = getClsfCodeName(exrmClsfCd);

        noti.setMsgCd("CP0005");
        noti.setExmnDvsn(exmnClsfNm);
        noti.setExrm_dept_sqno(param.getDeptSqno());
        noti.setAuthorization(param.getAuthorization());
        noti.setTransactionId(param.getTransactionId());

        return noti;
    }

    /*
        영상검사 결과 수진 알림(외부)
     */
    private void  setPacsAlert (Notification noti) {
        noti.setMsgCd("CP0006");
    }

    /*
        서식 발급 알림
     */
    private Notification setIssue (IssueRequest param) {
        Notification noti = new Notification();

        String exrmClsfCd = param.getExrmClsfCd().toUpperCase();
        String exmnClsfNm = getClsfCodeName(exrmClsfCd);
//        String reqMsg = exmnClsfNm + "이상 검사결과가 수신되었습니다.[" + ;

        noti.setExmnDvsn(exmnClsfNm);
        noti.setMsgCd("CP0007");
        noti.setEmrFormNm(param.getEmrformsm());
        noti.setAuthorization(param.getAuthorization());
        noti.setTransactionId(param.getTransactionId());
        noti.setReq_user_name(param.getPt_nm());

        return  noti;
    }

    /*
        CVR 보고 알림
     */
    private Notification getCvrNoti (CvrRequest param) {
        Notification noti = new Notification();
        String exrmClsfCd = param.getExrmClsfCd().toUpperCase();
        String exmnClsfNm = getClsfCodeName(exrmClsfCd);
        String reqMsg = param.getDate() + exmnClsfNm + "이상 검사결과가 수신되었습니다.";

        noti.setExmnDvsn(exmnClsfNm);
        noti.setReq_memo(null);
        noti.setReq_title("이상 검사결과 알림");
        noti.setReq_msg(reqMsg);
        noti.setReq_url("clinicsupport/#/MSC_010100");
        noti.setContent_detail_type("05");
        noti.setMsgCd("CP0008");
        noti.setAuthorization(param.getAuthorization());
        noti.setTransactionId(param.getTransactionId());
        noti.setReq_user_name(param.getPt_nm());

        return noti;
    }

    private Notification setType(PrscStatParam param) {
        String type = param.getType();

        Notification noti = new Notification();
        noti.setContent_detail_type(type);

        String exrmClsfCd = param.getExrmClsfCd().toUpperCase();
        String exmnClsfNm = getClsfCodeName(exrmClsfCd);

        noti.setExmnDvsn(exmnClsfNm);
        noti.setReq_msg(param.getDate() + exmnClsfNm);
        noti.setReq_memo(null);

        switch (type) {
            case "01":
                noti.setReq_memo(param.getDcResn());
                setPrscDc(noti);
                break;
            case "02":
                setPrscRequest(noti);
                break;
            case "03":
                setFinal(noti);
                break;
            case "04":
                setFinalCancel(noti);
                break;
            case "06":
                setPacsAlert(noti);
                break;
            default:
                throw new NullPointerException();
        }
        return noti;
    }

    // 메신저 알림
    @Transactional(propagation = Propagation.NESTED)
    public LuluResult sendMobileNoti(Notification noti, String user) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        HashMap<String, Object> header = new HashMap<>();
        header.put("Authorization", StringUtil.fixNull(noti.getAuthorization()));
        header.put("transaction-id", StringUtil.fixNull(noti.getTransactionId(), UUID.randomUUID().toString().replace("-", "")));

        HashMap<String, Object> body = new HashMap<>();
        body.put("nahago_user_list", user);
        body.put("chat_type", "1");
        body.put("content_type", "138");

        body.put("service_data", mapper.writeValueAsString(Arrays.asList(noti)));
        body.put("target_company_no", noti.getCompany_no());

        return innerGatewayService.sendPost("communication/we-talk/nahago-talk-send", body, header);
    }

    // 종 알림
    @Transactional(propagation = Propagation.NESTED)
    public LuluResult sendWebNoti(Notification param, String user) throws JsonProcessingException {
        NotiMessage msg = new NotiMessage();
        msg.setTypeCreate();
        msg.setMessageCode(param.getMsgCd());


        if (param.getMsgCd().equals("CP0005")) {
            msg.setTargetUser(param.getCompany_no(), -1);
            msg.setTargetUser(param.getCompany_no(), param.getUser_no());
            msg.addAdditionalKeyData("exrm_dept_sqno", param.getExrm_dept_sqno());
        } else if (param.getMsgCd().equals("CP0006")) {
            msg.setTargetUser(param.getCompany_no(), -1);
        } else if (param.getMsgCd().equals("CP0007")) {
            msg.setTargetUser(param.getCompany_no(), -1);
            msg.setTargetUser(param.getCompany_no(), param.getUser_no());
            msg.addMessageContentsData("userNm", param.getUser_name());
            msg.addMessageContentsData("emrformsm", param.getEmrFormNm());
            msg.addMessageContentsData("pt_nm", param.getReq_user_name());
        } else {
            msg.addMessageContentsData("userNm", param.getUser_name());
            msg.setLinkPageUrl(param.getReq_url());
            msg.addMessageContentsData("exmn_dvsn", param.getExmnDvsn());
            msg.addMessageContentsData("pt_nm", param.getReq_user_name());
            msg.setTargetUser(param.getCompany_no(), Long.parseLong(user));
        }

       return innerGatewayService.sendPost(msg.getApiUrl(), msg.getApiData());
    }

    @Transactional
    public LuluResult sendNoti(PrscStatParam param) throws JsonProcessingException {
        LuluResult result = new LuluResult();
        result.setResultCode(200);
        List<Object> resultDataList = new ArrayList<Object>();

        Notification noti = setType(param);

        noti.setAuthorization(param.getAuthorization());
        noti.setTransactionId(param.getTransactionId());

        if (param.getRequestCompany() != null) {
            noti.setUser_name(param.getRequestUser());
            noti.setCompany_name(param.getRequestCompany());
            noti.setCompany_no(param.getCompany_no());
        } else {
            healthSessionContext.setContext();
            healthSessionContext.setSessionForModel(noti);
        }

        List<Object> resultData = new ArrayList<>();
        Long companyNo = param.getCompany_no();

        try {
            if (param.getType().equals("05") || param.getType().equals("06") || param.getType().equals("07")) {
                return sendWebNoti(noti, "");
            } else if (param.getType().equals("01")) {
                // DC요청
                noti.setReq_user_name(param.getPtNm());
                Map<String, List<HashMap<String, Object>>> prscDrGroups = processDetails(param.getDetailsList(), "prsc_dr_sqno");
                sendNotificationsForList(prscDrGroups, noti, companyNo, resultData, resultDataList);
            } else {
                noti.setReq_user_name(param.getPtNm());
                Map<String, List<HashMap<String, Object>>> mdcrDrGroups = processDetails(param.getDetailsList(), "mdcr_dr_id");

                if (param.getType().equals("02")) {
                    // 처방요청
                    sendNotificationsForList(mdcrDrGroups, noti, companyNo, resultData, resultDataList);
                } else {
                    Map<String, List<HashMap<String, Object>>> prscDrGroups = processDetails(param.getDetailsList(), "prsc_dr_sqno");

                    for (String user : mdcrDrGroups.keySet()) {
                        // 진료의 알림 발송
                        prscDrGroups.remove(user);
                        addPrscRequestCode(mdcrDrGroups.get(user), noti);
                        sendNotification(user, noti, companyNo, resultData, resultDataList);
                    }

                    if (prscDrGroups.size() > 0) {
                        // 처방의 알림 발송
                        sendNotificationsForList(prscDrGroups, noti, companyNo, resultData, resultDataList);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.setResultCode(400);
            result.setResultMsg("알림 전송 오류");
        }

        result.setResultData(resultDataList);
        return result;
    }

    @Transactional
    public LuluResult sendCvrNoti(CvrRequest param) throws JsonProcessingException {
        LuluResult result = new LuluResult();
        result.setResultCode(200);
        List<Object> resultDataList = new ArrayList<Object>();

        Notification noti = getCvrNoti(param);

        healthSessionContext.setContext();
        healthSessionContext.setSessionForModel(noti);

        List<Object> resultData = new ArrayList<>();
        Long companyNo = param.getCompany_no();

        try {
            for (CvrDetail details : param.getDetailsList()) {
                noti.setReq_code_name(details.getPrsc_nm());
                noti.setReq_result(details.getResult());
                noti.setReq_result_date(details.getResult_date());

                if (param.getExrmClsfCd().toUpperCase().equals("L")) {
                    noti.setReq_normal_range(details.getRange());
                }

                String mdcrDrSqno = details.getMdcr_dr_id(); // 진료의 유저 시퀀스
                String prscDrSqno = details.getPrsc_dr_sqno(); // 처방의 유저 시퀀스
                sendNotification(mdcrDrSqno, noti, companyNo, resultData, resultDataList);

                if (!mdcrDrSqno.equals(prscDrSqno)) {
                    sendNotification(prscDrSqno, noti, companyNo, resultData, resultDataList);
                }
            }
            result.setResultData(resultDataList);
        } catch (Exception e) {
            e.printStackTrace();
            result.setResultCode(400);
            result.setResultMsg("알림 전송 오류");
        }
        return result;
    }

    @Transactional
    public LuluResult sendIssueNoti(IssueRequest param) throws JsonProcessingException {
        Notification noti = setIssue(param);
        healthSessionContext.setContext();
        healthSessionContext.setSessionForModel(noti);
        return sendWebNoti(noti,  "");
    }

    @Transactional
    public LuluResult sendProgressNoti(Progress param) throws JsonProcessingException {
        Notification noti = setExmnProgress(param);
        healthSessionContext.setContext();
        healthSessionContext.setSessionForModel(noti);
        return sendWebNoti(noti,  "");
    }

    private void sendNotificationsForList(Map<String, List<HashMap<String, Object>>> groups, Notification noti, Long companyNo, List<Object> resultData, List<Object> resultDataList) throws Exception {
        for (String key : groups.keySet()) {
            addPrscRequestCode(groups.get(key), noti);
            sendNotification(key, noti, companyNo, resultData, resultDataList);
        }
    }

    private void sendNotification(String user, Notification noti, Long companyNo, List<Object> resultData, List<Object> resultDataList) throws Exception {
        HashMap<String, Object> resultMap = new HashMap<>();
        List<HashMap<String, Object>> userList = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();

        userList.add(new HashMap<String, Object>(){{
            put("user_no", user);
            put("company_no", companyNo);
        }});

        String sendUser = mapper.writeValueAsString(userList);

        // 웹 알림
        resultData.add(sendWebNoti(noti,  user));
        // 나하고 알림
        resultData.add(sendMobileNoti(noti, sendUser));

        resultMap.put(user, resultData);
        resultDataList.add(resultMap);
    }

    private void addPrscRequestCode(List<HashMap<String, Object>> notiList, Notification noti){
        StringBuilder reqCode = new StringBuilder();
        String reqName = "";
        for (HashMap<String, Object> details : notiList) {
            reqCode.append(details.get("prsc_cd")).append(", ");
            reqName = details.get("prsc_nm").toString();
        }
        if (notiList.size() > 1) reqName = null;
        noti.setReq_code(reqCode.toString().trim().substring(0, reqCode.lastIndexOf(",")));
        noti.setReq_code_name(reqName);
    }

    private Map<String, List<HashMap<String, Object>>> processDetails(List<HashMap<String, Object>> detailsList, String detailKey) {
        return detailsList.stream()
                .collect(Collectors.groupingBy(detail -> detail.get(detailKey).toString(),
                        HashMap::new, Collectors.toList()));
    }

    private String getClsfCodeName(String exrmClsfCd) {
        String exmnClsfNm = "";
        switch (exrmClsfCd) {
            case "L":
                exmnClsfNm = " 진단검사 ";
                break;
            case "F":
                exmnClsfNm = " 기능검사 ";
                break;
            case "R" :
                exmnClsfNm = " 영상검사 ";
                break;
            case "E" :
                exmnClsfNm = " 내시경검사 ";
                break;
            case "P" :
                exmnClsfNm = " 물리치료 ";
                break;
            default:
                break;
        }
        return exmnClsfNm;
    }
}
