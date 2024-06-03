package com.duzon.lulu.service.MSC.common.service.impl;

import com.duzon.common.model.LuluResult;
import com.duzon.common.util.ValidationUtil;
import com.duzon.lulu.service.MSC.common.mapper.ExamMapper;
import com.duzon.lulu.service.MSC.common.model.Exam.*;
import com.duzon.lulu.service.MSC.common.service.IExamService;
import com.duzon.lulu.service.MSC.util.HealthSessionContext;
import com.duzon.lulu.service.MSC.util.Notification.NotificationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.*;

@Service
public class ExamServiceImpl implements IExamService {
    @Autowired
    ExamMapper examMapper;

    @Autowired
    private HealthSessionContext healthSessionContext;

    @Autowired
    private NotificationUtil notificationUtil;

    @Override
    public LuluResult selectReceptionList(HashMap<String, Object> param) {
        // 필수 파라미터 체크
        String[] requirementKeys = { "exmn_hope_date", "hope_exrm_cd", "prsc_clsf_cd" };
        LuluResult result = ValidationUtil.checkContainsKeysValue(requirementKeys, param);
        if (result.getResultCode() != 200) {
            return result;
        }
        healthSessionContext.setContext();
        healthSessionContext.setSessionForHashMap(param);

        result.setResultData(examMapper.selectReceptionList(param));

        return result;
    }

    @Override
    public LuluResult selectPrscList(HashMap<String, Object> param) {
        String[] keys = { "prsc_date", "pid", "rcpn_sqno", "hope_exrm_cd", "prsc_clsf_cd" };
        LuluResult result = ValidationUtil.checkContainsKeysValue(keys, param);
        if (result.getResultCode() != 200) {
            return result;
        }
        healthSessionContext.setContext();
        healthSessionContext.setSessionForHashMap(param);
        result.setResultData(examMapper.selectPrscList(param));

        return result;
    }

    @Override
    public LuluResult selectResultList(HashMap<String, Object> param) {
        // 필수 파라미터 체크
        String[] requirementKeys = { "exmn_hope_from_date", "exmn_hope_to_date", "hope_exrm_cd", "prsc_clsf_cd" };
        LuluResult result = ValidationUtil.checkContainsKeysValue(requirementKeys, param);
        if (result.getResultCode() != 200) {
            return result;
        }

        healthSessionContext.setContext();
        healthSessionContext.setSessionForHashMap(param);

        result.setResultData(examMapper.selectResultList(param));
        return result;
    }

    private LuluResult processType(HashMap<String, Object> param) {
        LuluResult result = new LuluResult();
        result.setResultCode(200);
        String type = (String) param.get("type");

        healthSessionContext.setContext();
        healthSessionContext.setSessionForHashMap(param);

        switch (type) {
            case "Exmn" :
                examMapper.updateExmn(param);
                break;
            case "ExmnCancel" :
                String check = examMapper.selectExamCheck(param);
                if (check.equals("E") || check.equals("F")) {
                    examMapper.updateExmnCancel(param);
                } else {
                    result.setResultCode(401);
                    switch (check) {
                        case "N":
                        case "O" :
                            result.setResultMsg("보고완료된 검사는 취소 불가합니다.");
                            break;
                        default:
                            result.setResultMsg("판독중인 검사는 검사취소가 불가합니다.");
                            break;
                    }
                }
                break;
            case "Receipt" :
                examMapper.updateReceipt(param);
                break;
            case "ReceiptCancel" :
                examMapper.updateReceiptCancel(param);
                break;
            case "Conduct" :
                examMapper.updateConduct(param);
                break;
            case "ConductCancel" :
                validateAndConductCancelUpdate(result, param);
                break;
            case "Save" :
                examMapper.updateSave(param);
                break;
            case "SaveCancel" :
                examMapper.saveCancel(param);
                break;
            case "Interpret" :
                examMapper.updateInterpret(param);
                break;
            case "InterpretCancel" :
                examMapper.updateInterpretCancel(param);
                examMapper.deleteResult(param);
                break;
            case "Report" :
                examMapper.updateReport(param);
                break;
            case "ReportCancel" :
                examMapper.updateReportCancel(param);
                examMapper.deleteResult(param);
                break;
            case "DcRequest" :
                examMapper.updateDcRqstY(param);
                break;
            case "WrcnWrtn" :
                examMapper.updateWrcnWrtnY(param);
                break;
            default:
                result.setResultCode(400);
                result.setResultMsg("파라미터 [type] 체크 필요");
                break;
        }

        return result;
    }

    // 단건 처방진행 상태 업데이트
    @Transactional
    @Override
    public LuluResult updatePrscStatItem(HashMap<String, Object> param) {
        // 필수 파라미터 체크
        String[] requirementKeys = { "pid", "prsc_date", "prsc_sqno", "type" };
        LuluResult result = ValidationUtil.checkContainsKeysValue(requirementKeys, param);
        if(result.getResultCode() != 200) {
            return result;
        }

        result = processType(param);

        return result;
    }

    // 리스트 처방진행 상태 업데이트
    @Transactional
    @Override
    public LuluResult updatePrscStat(PrscStatParam param) throws Exception {
        LuluResult result = new LuluResult();
        result.setResultCode(200);

        if (param.getDetailsList() == null || param.getDetailsList().isEmpty()) {
            result.setResultCode(400);
            result.setResultMsg("파라미터 [detailsList] 체크 필요");
            return result;
        } else if (param.getType() == null || param.getType().equals("")) {
            result.setResultCode(400);
            result.setResultMsg("파라미터 [type] 체크 필요");
            return result;
        } else {
            for (HashMap<String, Object> details : param.getDetailsList()) {
                if (!details.containsKey("pid") || !details.containsKey("prsc_date") || !details.containsKey("prsc_sqno")) {
                    result.setResultCode(400);
                    result.setResultMsg("파라미터 [detailsList] 속성(pid, prsc_date, prsc_sqno) 체크 필요");
                    return result;
                }
                details.put("type", param.getType());
                result = processType(details);
            }

            if (result.getResultCode() == 200 && param.getType().equals("DcRequest")) {
                result.setResultData(sendNotiList(param));
            }
        }

        return  result;
    }

    @Override
    public LuluResult selectExamCheck(PrscStatParam param) {
        LuluResult result = new LuluResult();
        result.setResultCode(200);

        if (param.getDetailsList() == null || param.getDetailsList().isEmpty()) {
            result.setResultCode(400);
            result.setResultMsg("파라미터 [detailsList] 체크 필요");
            return result;
        } else {
            ArrayList<HashMap<String, Object>> returnData = new ArrayList();
            for (HashMap<String, Object> details : param.getDetailsList()) {
                healthSessionContext.setContext();
                healthSessionContext.setSessionForHashMap(details);
                returnData.add(new HashMap<String, Object>(){{
                    put("prsc_sqno", details.get("prsc_sqno"));
                    put("prsc_prgr_stat_cd", examMapper.selectExamCheck(details));
                }});
            }
            result.setResultData(returnData);
        }

        return  result;
    }

    @Override
    public LuluResult requestPrsc(PrscStatParam param) throws Exception {
        LuluResult result = new LuluResult();
        result.setResultCode(200);

        if (param.getDetailsList() == null || param.getDetailsList().isEmpty()) {
            result.setResultCode(400);
            result.setResultMsg("파라미터 [detailsList] 체크 필요");
            return result;
        } else {
            param.setType("PrscRequest");
            return sendNotiList(param);
        }
    }

    @Override
    public LuluResult dcPrsc(PrscStatParam param) throws Exception {
        LuluResult result = new LuluResult();
        result.setResultCode(200);

        if (param.getDetailsList() == null || param.getDetailsList().isEmpty()) {
            result.setResultCode(400);
            result.setResultMsg("파라미터 [detailsList] 체크 필요");
            return result;
        } else {
            for (HashMap<String, Object> details : param.getDetailsList()) {
                if (!details.containsKey("pid") || !details.containsKey("prsc_date") || !details.containsKey("prsc_sqno")) {
                    result.setResultCode(400);
                    result.setResultMsg("파라미터 [detailsList] 속성(pid, prsc_date, prsc_sqno) 체크 필요");
                    return result;
                }
                details.put("type", "DcRequest");
                result = processType(details);
            }

            if (result.getResultCode() == 200) {
                result.setResultData(sendNotiList(param));
            }
        }

        return  result;
    }

    @Override
    public LuluResult validateAndConductCancelUpdate(LuluResult result, HashMap<String, Object> param) {
        String check1 = examMapper.selectExamCheck(param);
        if (check1.equals("E") || check1.equals("F")) {
            examMapper.updateConductCancel(param);
        } else {
            result.setResultCode(401);
            result.setResultData(check1);
            switch (check1) {
                case "N":
                case "O" :
                    result.setResultMsg("보고완료된 검사는 취소 불가합니다.");
                    break;
                default:
                    result.setResultMsg("판독중인 검사는 검사취소가 불가합니다.");
                    break;
            }
        }
        return result;
    }

    @Override
    public LuluResult sendNoti(HashMap<String, Object> param) throws Exception {
        String[] requirementKeys = { "type", "exrmClsfCd", "date", "pt_nm", "authorization" };
        LuluResult result = ValidationUtil.checkContainsKeysValue(requirementKeys, param);
        if (result.getResultCode() != 200 ) return result;

        PrscStatParam notiParam = new PrscStatParam();
        switch (param.get("type").toString()) {
            case "Interpret":
            case "Report":
            case "completeReport":
                notiParam.setType("03");
                break;
            case "InterpretCancel":
            case "ReportCancel":
            case "completeReportCancel":
                notiParam.setType("04");
                break;
            case "ExmnProgress" :
                notiParam.setType("05");
                break;
            case "PacsAlert" :
                notiParam.setType("06");
                break;
            case "Issuse" :
                notiParam.setType("07");
                break;
        }
        if (notiParam.getType() != null) {
            List notiDetailsList = new ArrayList();
            notiDetailsList.add(param);
            notiParam.setDetailsList(notiDetailsList);
            notiParam.setExrmClsfCd(param.get("exrmClsfCd").toString());
            notiParam.setDate(param.get("date").toString());
            notiParam.setPtNm(param.get("pt_nm").toString());
            notiParam.setAuthorization(param.get("authorization").toString());
            if (param.get("reqCompany") != null) {
                notiParam.setRequestCompany(param.get("reqCompany").toString());
                notiParam.setRequestUser(param.get("reqUser").toString());
                notiParam.setCompany_no(Long.parseLong(param.get("cno").toString()));
            }
            result = notificationUtil.sendNoti(notiParam);
        } else {
            result.setResultCode(400);
            result.setResultMsg("알림 보낼 수 없는 요청");
        }

        return result;
    }

    @Override
    public LuluResult sendNotiList(PrscStatParam param) throws Exception {
        LuluResult result = new LuluResult();

        switch (param.getType()) {
            case "DcRequest" :
                param.setType("01");
                break;
            case "PrscRequest" :
                param.setType("02");
                break;
            case "Interpret" :
            case "Report" :
            case "completeReport":
                param.setType("03");
                break;
            case "InterpretCancel" :
            case "ReportCancel" :
            case "completeReportCancel":
                param.setType("04");
                break;
            case "ExmnProgress" :
                param.setType("05");
                break;
            case "PacsAlert" :
                param.setType("06");
                break;
            case "Issuse" :
                param.setType("07");
                break;
            default:
                result.setResultCode(400);
                result.setResultMsg("알림 보낼 수 없는 요청");
                return result;
        }

        if (param.getDetailsList().size() == 0) {
            result.setResultCode(400);
            result.setResultMsg("파라미터 [detailsList] 체크 필요");
            return result;
        } else {
            for (HashMap<String, Object> details : param.getDetailsList()) {
            	String paramType = param.getType();
            	if(paramType.charAt(0)=='0' && paramType.charAt(1) <= '4') {
            		 if(paramType.charAt(1) != '1'&& !StringUtils.hasText((String) details.get("mdcr_dr_id"))){//DC요청이 아닌경우 진료의 일련 검사.
                         result.setResultCode(400);
                         result.setResultMsg("파라미터 [detailsList] 속성 mdcr_dr_id 체크 필요");
                         return result;
            		 }
            		 if(paramType.charAt(1) != '2'&& !StringUtils.hasText((String)details.get("prsc_dr_sqno"))){//처방 요청이 아닌경우 처방의 일련 검사.
                         result.setResultCode(400);
                         result.setResultMsg("파라미터 [detailsList] 속성 prsc_dr_sqno 체크 필요");
                         return result;
            		 }
            	}
                if (!details.containsKey("prsc_cd") || !details.containsKey("prsc_nm")
                        || details.get("prsc_cd") == null || details.get("prsc_nm") == null) {
                    result.setResultCode(400);
                    result.setResultMsg("파라미터 [detailsList] 속성(prsc_cd, prsc_nm) 체크 필요");
                    return result;
                }
            }
        }

        if (param.getPtNm() == null) param.setPtNm(param.getPt_nm());
        result = notificationUtil.sendNoti(param);

        return result;
    }

    @Override
    public LuluResult sendPrgrProgressNoti(Progress param) throws Exception {
        param.setType("05");
        return notificationUtil.sendProgressNoti(param);
    }

    @Override
    public LuluResult sendPacsAlertNoti(PrscStatParam param) throws  Exception {
        param.setType("06");
        return notificationUtil.sendNoti(param);
    }

    @Override
    public LuluResult sendCvrRequestNoti(CvrRequest param) throws Exception {
        return notificationUtil.sendCvrNoti(param);
    }

    @Override
    public LuluResult sendIssueNoti(IssueRequest param) throws Exception {
        HashMap<String, Object> hashMapparam = new HashMap<>();

        healthSessionContext.setContext();
        healthSessionContext.setSessionForHashMap(hashMapparam);

        hashMapparam.put("mdfr_clsf_sqno", param.getMdfr_clsf_sqno());

        param.setEmrformsm(this.examMapper.selectErmNm(hashMapparam));

        return notificationUtil.sendIssueNoti(param);
    }
}
