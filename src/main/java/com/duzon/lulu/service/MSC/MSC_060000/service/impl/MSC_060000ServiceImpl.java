package com.duzon.lulu.service.MSC.MSC_060000.service.impl;

import com.duzon.common.model.LuluResult;
import com.duzon.common.util.ValidationUtil;
import com.duzon.lulu.service.MSC.MSC_060000.model.*;
import com.duzon.lulu.service.MSC.MSC_060000.service.IMSC_060000Service;
import com.duzon.lulu.service.MSC.MSC_060000.mapper.MSC_060000Mapper;
import com.duzon.lulu.service.MSC.common.mapper.CommonMapper;
import com.duzon.lulu.service.MSC.common.model.Common.PatientInfo;
import com.duzon.lulu.service.MSC.common.model.Exam.DetailQueryKey;
import com.duzon.lulu.service.MSC.common.model.Exam.PrscStatParam;
import com.duzon.lulu.service.MSC.common.service.IExamService;
import com.duzon.lulu.service.MSC.util.HealthSessionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class MSC_060000ServiceImpl implements IMSC_060000Service {
    @Autowired
    private MSC_060000Mapper MSC060000Mapper;
    @Autowired
    private CommonMapper commonMapper;
    @Autowired
    private IExamService iExamService;
    @Autowired
    private HealthSessionContext healthSessionContext;

    private static final String EXAM_CLASSIFICATION_CODE = "P";
    private static final String TRTM_START = "trtmStart";
    private static final String TRTM_START_CANCEL = "trtmStartCancel";
    private static final String TRTM_END = "trtmEnd";
    private static final String TRTM_END_CANCEL = "trtmEndCancel";
    private static final String COMPLETE_REPORT = "completeReport";
    private static final String COMPLETE_REPORT_CANCEL = "completeReportCancel";

    // 필수 파라미터 체크
    private LuluResult checkParam(HashMap param) {
        String[] requirementKeys = { "pid", "prsc_date", "prsc_sqno" };
        LuluResult result = ValidationUtil.checkContainsKeysValue(requirementKeys, param);
        return result;
    }

    @Override
    public LuluResult selectDefault() {
        LuluResult result = new LuluResult();
        HashMap<String, Object> param = new HashMap<>();

        healthSessionContext.setContext();
        healthSessionContext.setSessionForHashMap(param);

        result.setResultCode(200);
        result.setResultData(MSC060000Mapper.selectComCodeList(param));

        return result;
    }

    @Override
    public LuluResult selectReceptionList(HashMap param) {
        String[] requirementKeys = { "mdtr_hope_date", "hope_trrm_dept_sqno", "prsc_clsf_cd" };
        LuluResult result = ValidationUtil.checkContainsKeysValue(requirementKeys, param);
        if (result.getResultCode() != 200) {
            return result;
        }

        healthSessionContext.setContext();
        healthSessionContext.setSessionForHashMap(param);

        List<Mdtr> mdtrList = MSC060000Mapper.selectReceptionList(param);

        // 보고 완료 상태값 체크
        // -> 보고 완료 상태에 같은 조건이라는 기준 하에 mdtr_rslt_rptg_yn 가 Y인 것과 아닌 것 2개 이상의 행이 존재하면 Y인 행만 남긴다
        Map<List<String>, Mdtr> itemMap = new LinkedHashMap<>();
        for (Mdtr item : mdtrList) {
            List<String> key = Arrays.asList(item.getPid(), item.getRcpn_sqno(), item.getPrsc_date(), item.getHope_exrm_cd());

            if (!itemMap.containsKey(key) || "Y".equals(item.getMdtr_rslt_rptg_yn()) && item.getPrsc_prgr_stat_cd().equals(itemMap.get(key).getPrsc_prgr_stat_cd())) {
                itemMap.put(key, item);
            }
        }

        List<Mdtr> uniqueItems = new ArrayList<>(itemMap.values());

        result.setResultData(uniqueItems);

        return result;
    }

    @Override
    public LuluResult selectPatientInfo(HashMap<String, Object> param) {
        healthSessionContext.setContext();
        // 필수 파라미터 체크
        String[] requirementKeys = { "rcpn_sqno" };
        LuluResult result = ValidationUtil.checkContainsKeysValue(requirementKeys, param);
        if(result.getResultCode() != 200) {
            return result;
        }

        healthSessionContext.setSessionForHashMap(param);

        PatientInfo examReception = MSC060000Mapper.selectPatientInfo(param);
        result.setResultData(examReception);

        return result;
    }

    @Override
    public LuluResult selectPrscList(HashMap<String, Object> param) {
        String[] keys = { "prsc_date", "pid", "rcpn_sqno", "hope_exrm_cd", "prsc_clsf_cd" };
        LuluResult result = ValidationUtil.checkContainsKeysValue(keys, param);
        if (result.getResultCode() != 200) {
            return result;
        }

        Map<String, Object> resultData = new HashMap<>(); // 리턴 결과

        healthSessionContext.setContext();
        healthSessionContext.setSessionForHashMap(param);

        result.setResultData(MSC060000Mapper.selectPrscList(param));

        return result;
    }

    @Transactional(rollbackFor = {RuntimeException.class, Exception.class})
    @Override
    public LuluResult reception(PrscStatParam param) {
        LuluResult result = new LuluResult();
        List<HashMap<String, Object>> list = param.getDetailsList();
        String type = param.getType();

        try {
            boolean isReportCancelSucceed = false;
            healthSessionContext.setContext();

            for (int i = 0; i < list.size(); i++) {
                HashMap<String, Object> details = list.get(i);

                // 필수 파라미터 체크
                result = checkParam(details);

                if (result.getResultCode() == 200) {
                    healthSessionContext.setSessionForHashMap(details);

                    switch (type) {
                        case TRTM_START:
                            handleTrtmStart(details);
                            break;
                        case TRTM_START_CANCEL:
                            handleTrtmStartCancel(details);
                            break;
                        case TRTM_END:
                            handleTrtmEnd(details);
                            break;
                        case TRTM_END_CANCEL:
                            details.put("hstr_stat_cd", "3");
                            handleTrtmEndCancel(details);
                            break;
                        case COMPLETE_REPORT:
                            details.put("hstr_stat_cd", "1");
                            handleCompleteReport(details);
                            break;
                        case COMPLETE_REPORT_CANCEL:
                            boolean cancelResult = handleCompleteReportCancel(details);

                            if (!isReportCancelSucceed) {
                                if (cancelResult) {
                                    isReportCancelSucceed = true;
                                } else if (i == list.size() -1) {
                                    result.setResultCode(401);
                                }
                            }
                            break;
                        default:
                            result.setResultCode(400);
                            result.setResultMsg("파라미터 [type] 체크 필요");
                            break;
                    }
                }
            }

//            if (result.getResultCode() == 200 && (COMPLETE_REPORT.equals(type) || COMPLETE_REPORT_CANCEL.equals(type))) {
//                list = list.stream()
//                        .filter(detail -> "Y".equals(detail.get("exmn_rslt_rptg_yn")))
//                        .collect(Collectors.toList());
//                System.out.println(list.size());
//                if (list.size() > 0) {
//                    param.setDetailsList(list);
//                    param.setExrmClsfCd(EXAM_CLASSIFICATION_CODE);
//                    return iExamService.sendNotiList(param);
//                }
//            }
            return result;
        } catch (Exception e) {
            throw new RuntimeException("RuntimeException rollback : "+e);
        }
    }

    private void handleTrtmStart(HashMap<String, Object> params) {
        // 처치시작 : 치료대기(B) -> 진행중(C)
        int updateStat = MSC060000Mapper.updateTrtmStart(params);

        if (updateStat > 0) {

            int affectedRows = MSC060000Mapper.upsertResultStart(params);
            if (affectedRows > 0) {
                MSC060000Mapper.insertResultHistory(params);
            }
        }
    }

    private void handleTrtmStartCancel(HashMap<String, Object> params) {
        // 처치취소 : 진행중(C) -> 치료대기(B)
        int updateStat = MSC060000Mapper.updateTrtmStartCancel(params);

        if (updateStat > 0) {
            int affectedRows = MSC060000Mapper.updateResultStartCancel(params);
            if (affectedRows > 0) {
                MSC060000Mapper.insertResultHistory(params);
            }

        }
    }

    private void handleTrtmEnd(HashMap<String, Object> params) {
        // 처치종료 : 진행중(C) -> 치료완료(E)
        int updateStat = MSC060000Mapper.updateTrtmEnd(params);

        if (updateStat > 0) {
            int affectedRows = MSC060000Mapper.updateResultEnd(params);
            if (affectedRows > 0) {
                MSC060000Mapper.insertResultHistory(params);
            }
        }
    }

    private void handleTrtmEndCancel(HashMap<String, Object> params) {
        // 처치종료 취소 : 치료완료(E) -> 진행중(C)
        // 소견/메모/차트 그래픽 null
        int updateStat = MSC060000Mapper.updateTrtmEndCancel(params);

        if (updateStat > 0) {
            int affectedRows = MSC060000Mapper.updateResultCancel(params);
            if (affectedRows > 0) {
                MSC060000Mapper.insertResultHistory(params);
            }
        }
    }

    private void handleCompleteReport(HashMap<String, Object> params) {
        // 완료보고 : 치료완료(E) -> 보고완료(N)
        int updateStat = MSC060000Mapper.updateCompleteReport(params);

        if (updateStat > 0) {
            int affectedRows = MSC060000Mapper.updateResultCompleteReport(params);
            if (affectedRows > 0) {
                MSC060000Mapper.insertResultHistory(params);
            }
        }
    }

    private Boolean handleCompleteReportCancel(HashMap<String, Object> params) {
        String currentStatCd = MSC060000Mapper.selectStatCd(params);
        int updateStat = 0;

        if (currentStatCd.equals("N")) {
            updateStat = MSC060000Mapper.updateCompleteReportCancel(params);

            if (updateStat > 0) {
                int affectedRows = MSC060000Mapper.updateResultCompleteReportCancel(params);
                if (affectedRows > 0) {
                    MSC060000Mapper.insertResultHistory(params);
                }
                return true;
            }
        }

        return false;
    }


    @Transactional
    @Override
    public LuluResult save(SaveRequired param) {
        LuluResult result = new LuluResult();
        healthSessionContext.setContext();
        param.setPortal_id(healthSessionContext.getPortal_id());

        int affectedRows = MSC060000Mapper.updateResultInput(param);
        if (affectedRows > 0) {
            HashMap<String, Object> keyMap = param.convertKeysToMap();
            MSC060000Mapper.insertResultHistory(keyMap);
        }

        return result;
    }

    @Transactional
    @Override
    public LuluResult updateMdtrHopeDate(List<HashMap<String, Object>> param) {
        healthSessionContext.setContext();

        for(HashMap<String, Object> exmn: param) {
            healthSessionContext.setSessionForHashMap(exmn);
            MSC060000Mapper.updateMdtrHopeDate(exmn);
        }
        LuluResult result = new LuluResult();
        result.setResultCode(0);
        return result;
    }

    @Transactional
    @Override
    public LuluResult updateDcRqstY(PrscStatParam param) throws Exception {
        LuluResult result = new LuluResult();
        result.setResultCode(200);
        healthSessionContext.setContext();

        if (param.getDetailsList() == null || param.getDetailsList().isEmpty()) {
            result.setResultCode(400);
            result.setResultMsg("파라미터 [detailsList] 체크 필요");
        } else {
            for (HashMap<String, Object> details : param.getDetailsList()) {
                healthSessionContext.setSessionForHashMap(details);
                MSC060000Mapper.updateDcRqstY(details);
            }
            result.setResultData(iExamService.sendNotiList(param));
        }
        return result;
    }

    @Transactional
    @Override
    public LuluResult updateTrtmDt(TrtrDtUpdate param) {
        LuluResult result = new LuluResult();
        healthSessionContext.setContext();
        param.setPortal_id(healthSessionContext.getPortal_id());

        int affectedRows = MSC060000Mapper.updateTrtmDt(param);
        if (affectedRows > 0) {
            HashMap<String, Object> keyMap = param.convertKeysToMap();
            MSC060000Mapper.insertResultHistory(keyMap);
        }
        return result;
    }

    @Override
    public LuluResult selectRecord(HashMap<String, Object> param) {
        LuluResult result = new LuluResult();

        healthSessionContext.setContext();
        healthSessionContext.setSessionForHashMap(param);

        List<Record> recordList = MSC060000Mapper.selectRecord(param);

        result.setResultCode(200);
        result.setResultData(recordList);

        return result;
    }

    @Override
    public LuluResult selectHistory(DetailQueryKey param) {
        LuluResult result = new LuluResult();
        result.setResultCode(200);
        result.setResultData(MSC060000Mapper.selectHistory(param));
        return result;
    }
}