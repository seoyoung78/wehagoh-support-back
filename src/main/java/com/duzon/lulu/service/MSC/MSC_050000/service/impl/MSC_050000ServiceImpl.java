package com.duzon.lulu.service.MSC.MSC_050000.service.impl;

import com.duzon.common.model.LuluResult;
import com.duzon.common.util.ValidationUtil;
import com.duzon.lulu.service.MSC.MSC_050000.mapper.MSC_050000Mapper;
import com.duzon.lulu.service.MSC.MSC_050000.model.*;
import com.duzon.lulu.service.MSC.MSC_050000.service.IMSC_050000Service;
import com.duzon.lulu.service.MSC.common.model.Exam.DetailQueryKey;
import com.duzon.lulu.service.MSC.common.model.Exam.PrscStatParam;
import com.duzon.lulu.service.MSC.common.service.IExamService;
import com.duzon.lulu.service.MSC.util.HealthSessionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class MSC_050000ServiceImpl implements IMSC_050000Service {
    @Autowired
    private MSC_050000Mapper msc_050000Mapper;

    @Autowired
    private IExamService iExamService;

    @Autowired
    private HealthSessionContext healthSessionContext;

    private static final String CONDUCT = "Conduct"; // 검사
    private static final String CONDUCT_CANCEL = "ConductCancel"; // 검사취소
    private static final String REPORT = "Report";
    private static final String REPORT_CANCEL = "ReportCancel";
    private static final String SAVE_CANCEL = "SaveCancel";
    private static final String TAB_BASIC = "basic";
    private static final String TAB_RECORD = "record";

    @Override
    public LuluResult common() {
        LuluResult result = new LuluResult();
        HashMap param = new HashMap();
        Map<String, Object> resultData = new HashMap<>(); // 리턴 결과

        List<CommonCode> basicCodeList = msc_050000Mapper.selectBasicCodeList(param);
        resultData.put("basicCodeList", basicCodeList);

        List<CommonCode> resultCodeList = msc_050000Mapper.selectResultCodeList(param);
        resultData.put("resultCodeList", resultCodeList);

        result.setResultData(resultData);
        return result;
    }

    @Override
    public LuluResult detail(DetailQueryKey param) {
        LuluResult result = new LuluResult();
        Detail detail = new Detail();

        BasicInfoDetail basicInfo = msc_050000Mapper.selectBasicInfo(param);
        ResultRecordDetail resultRecord = msc_050000Mapper.selectResultRecord(param);
        List<ObsrOpnnDetail> obsrOpnnList = msc_050000Mapper.selectObsrOpnnList(resultRecord);

        detail.setBasicInfo(basicInfo);
        detail.setResultRecord(resultRecord);
        detail.setObsrOpnn(obsrOpnnList);

        result.setResultData(detail);
        result.setResultCode(200);
        return result;
    }

    @Override
    public LuluResult detailFetcher(TabDetailRequest param) {
        LuluResult result = new LuluResult();
        Detail detail = new Detail();
        Optional<String> optionalTabIndex = Optional.ofNullable(param.getTabIndex());

        ExmnInfo exmnInfo = msc_050000Mapper.selectExmnInfo(param);
        String siteCode = msc_050000Mapper.selectSiteCd(param);
        exmnInfo.setMdtr_site_cd(siteCode);
        detail.setExmnInfo(exmnInfo);

        switch (optionalTabIndex.orElse("-1")) {
            case "0":
                loadBasicInfo(param, detail);
                break;
            case "1":
                loadResultRecordAndOpinions(param, detail);
                break;
            default:
                loadAllInfo(param, detail);
                break;
        }

        result.setResultData(detail);
        result.setResultCode(200);
        return result;
    }

    @Override
    public LuluResult selectHistory(HistoryQueryKey param) {
        LuluResult result = new LuluResult();
        Map<String, Object> resultData = new HashMap<>(); // 리턴 결과
        String tabId = param.getTabId();

        if (TAB_BASIC.equals(tabId)) {
            List<BasicInfo> basicInfos = msc_050000Mapper.selectBasicHistory(param);
            resultData.put("historyList", basicInfos);
        }

        if (TAB_RECORD.equals(tabId)) {
            List<ResultRecord> resultRecords = msc_050000Mapper.selectRecordHistory(param);
            List<ObsrOpnn> obsrOpnns = msc_050000Mapper.selectObsrOpnnHistory(param);
            resultData.put("historyList", resultRecords);
            resultData.put("obsrOpnnList", obsrOpnns);
        }

        result.setResultData(resultData);
        return result;
    }

    @Transactional(rollbackFor = {RuntimeException.class, Exception.class})
    @Override
    public LuluResult reception(PrscStatParam param) {
        try {
            if (CONDUCT_CANCEL.equals((param.getType()))) {
                healthSessionContext.setContext();
                // 검사 취소 : 처방진행상태코드 단건 업데이트
                return handleConductCancel(param);
            } else {
                // 1. 검사처방 테이블의 처방진행상태코드 update
                LuluResult updatePrscStatResult = iExamService.updatePrscStat((param));
                if (updatePrscStatResult.getResultCode() != 200) {
                    return updatePrscStatResult;
                }
                if (CONDUCT.equals((param.getType()))) {
                    // 2. 검사 : 내시경 검사 결과 테이블 upsert
                    handleConduct(param);
                }
                return updatePrscStatResult;
            }
        } catch (Exception e) {
            throw new RuntimeException("RuntimeException rollback : "+e);
        }
    }

    @Transactional(rollbackFor = {RuntimeException.class, Exception.class})
    @Override
    public LuluResult save(SaveRequired param) {
        LuluResult result = new LuluResult();
        RequiredFields requiredFields = param.getRequiredFields();
        BasicInfo basicInfo = param.getBasicInfo();
        ResultRecord resultRecord = param.getResultRecord();
        List<ObsrOpnn> obsrOpnnList = param.getObsrOpnnList();

        HashMap<String, Object> recordKeyMap = prepareKeyMap(requiredFields, basicInfo.getEnds_rcrd_sqno(), resultRecord.getEnds_rcrd_sqno());

        try {
            // 1. 검사처방 테이블의 처방진행상태코드 update
            LuluResult updatePrscStatResult = iExamService.updatePrscStatItem(recordKeyMap);

            if (updatePrscStatResult.getResultCode() != 200) {
                return updatePrscStatResult;
            } else {
                // 2. 내시경 검사결과 테이블 update
                int affectedResultRows = msc_050000Mapper.updateSave(recordKeyMap);
                if (affectedResultRows > 0) {
                    msc_050000Mapper.insertResultHistory(recordKeyMap);
                    ResultRecord updateRecordModel = msc_050000Mapper.selectIptnInfo(recordKeyMap);
                    resultRecord.setDtrm_date(updateRecordModel.getDtrm_date());
                    resultRecord.setEnds_dr_nm(updateRecordModel.getEnds_dr_nm());
                }

                // 3. [기초정보]
                msc_050000Mapper.updateBasicLastFlag(recordKeyMap); // 3-1. 마지막 여부 update
                msc_050000Mapper.insertBasicInfo(recordKeyMap, basicInfo); // 3-2. 테이블 insert

                // 4. [결과기록]
                msc_050000Mapper.updateRecordLastFlag(recordKeyMap); // 4-2. 마지막 여부 update
                msc_050000Mapper.insertRecordInfo(recordKeyMap, resultRecord); // 4-3. 테이블 insert

                Long generatedKey = resultRecord.getEnds_rslt_rcrd_sqno();

                if (generatedKey == null) {
                    throw new IllegalStateException("내시경 결과 기록 일련번호 키 생성 실패");
                }

                // 5. 관찰소견 테이블 upsert
                for (ObsrOpnn obsrOpnn:obsrOpnnList) {
                    obsrOpnn.setEnds_rslt_rcrd_sqno(generatedKey);
                    obsrOpnn.setDgsg_no(resultRecord.getDgsg_no());
                    msc_050000Mapper.insertObsrOpnn(recordKeyMap, obsrOpnn);
                }

                result.setResultCode(200);
                return result;
            }
        } catch (Exception e) {
            throw new RuntimeException("RuntimeException rollback : "+e);
        }
    }

    @Transactional(rollbackFor = {RuntimeException.class, Exception.class})
    @Override
    public LuluResult result(UpdateRequest param) {
        try {
            // 1. 검사처방 테이블의 처방진행상태코드 update
            HashMap<String, Object> keyMap = prepareKeyMap(param, param.getBasic_ends_rcrd_sqno(), param.getRecord_ends_rcrd_sqno());
            LuluResult updatePrscStatResult = iExamService.updatePrscStatItem(keyMap);
            String portalId = (String) keyMap.getOrDefault("portal_id", "");
            param.setPortal_id(portalId);

            if (updatePrscStatResult.getResultCode() != 200) {
                return updatePrscStatResult;
            } else {
                String type = param.getType();
                int affectedRows  = 0;

                // 2. 내시경 검사결과 테이블 update
                switch (type) {
                    case SAVE_CANCEL :
                        affectedRows = handleSaveCancel(param, keyMap);
                        break;
                    case REPORT :
                        affectedRows = handleReport(param);
                        break;
                    case REPORT_CANCEL :
                        affectedRows  = msc_050000Mapper.updateReportCancel(param);
                        break;
                    default:
                        break;
                }

                if (affectedRows  > 0) {
                    msc_050000Mapper.insertResultHistory(keyMap);
                }

                return updatePrscStatResult;
            }
        } catch (Exception e) {
            throw new RuntimeException("RuntimeException rollback : "+e);
        }
    }

    // 필수 파라미터 체크
    private LuluResult checkParam(HashMap param) {
        String[] requirementKeys = { "pid", "prsc_date", "prsc_sqno" };
        LuluResult result = ValidationUtil.checkContainsKeysValue(requirementKeys, param);
        return result;
    }

    private void handleConduct(PrscStatParam param) {
        for (HashMap details : param.getDetailsList()) {
            int affectedRows = msc_050000Mapper.upsertResult(details);
            if (affectedRows > 0) {
                msc_050000Mapper.insertResultHistory(details);
            }
        }
    }

    private LuluResult handleConductCancel(PrscStatParam param) {
        LuluResult finalResult = new LuluResult();
        Boolean isUpdateSuccessful = false;
        List<HashMap<String, Object>> detailsList = param.getDetailsList();

        for (int i = 0; i < detailsList.size(); i++) {
            LuluResult updateResult = updateDetailIfValid(detailsList.get(i));
            isUpdateSuccessful = updateResult.getResultCode() == 200;

            if (!isUpdateSuccessful && i == detailsList.size() - 1) {
                return updateResult;
            }
        }

        finalResult.setResultCode(200);
        return finalResult;
    }

    private LuluResult updateDetailIfValid(HashMap<String, Object> detail) {
        detail.put("portal_id", healthSessionContext.getPortal_id());
        LuluResult validationResult = checkParam(detail);
        if (validationResult.getResultCode() == 200) {
            iExamService.validateAndConductCancelUpdate(validationResult, detail);
            if (validationResult.getResultCode() == 200) {
                performUpdate(detail);
            }
        }
        return validationResult;
    }

    private void performUpdate(HashMap<String, Object> detail) {
        int affectedRows = msc_050000Mapper.updateResultStatusToYByDel(detail);
        if (affectedRows > 0) {
            msc_050000Mapper.insertResultHistory(detail);
        }
    }

    // 판독취소 데이터 초기화
    private int handleSaveCancel(UpdateRequest param, HashMap<String, Object> keyMap) {
        // 내시경 검사결과
        int affectedRows = msc_050000Mapper.saveCancel(param);

        // [기초정보]
        msc_050000Mapper.updateBasicLastFlag(keyMap); // 1. 마지막 여부 변경
        msc_050000Mapper.initialBasicInfo(param); // 2. null 초기화

        // [결과기록]
        msc_050000Mapper.updateRecordLastFlag(keyMap); // 1. 마지막 여부 변경
        msc_050000Mapper.initialRecordInfo(param); // 2. null 초기화

        return affectedRows;
    }

    private int handleReport(UpdateRequest param) throws Exception {
        int affectedRows  = msc_050000Mapper.updateReport(param);
//        iExamService.sendNoti(param);
        if (affectedRows  > 0) {
            // mscemnrsn 테이블 insert
            msc_050000Mapper.insertResult(param);
        }
        return affectedRows;
    }

    public HashMap<String, Object> prepareKeyMap(ConvertibleToMap convertible, long basicEndsRcrdSqno, long recordEndsRcrdSqno) {
        HashMap<String, Object> keyMap = convertible.convertKeysToMap();
        keyMap.put("basic_ends_rcrd_sqno", basicEndsRcrdSqno);
        keyMap.put("record_ends_rcrd_sqno", recordEndsRcrdSqno);
        return keyMap;
    }

    private void loadBasicInfo(TabDetailRequest param, Detail detail) {
        BasicInfoDetail basicInfo = msc_050000Mapper.selectBasicInfo(param);
        detail.setBasicInfo(basicInfo);
    }

    private void loadResultRecordAndOpinions(TabDetailRequest param, Detail detail) {
        ResultRecordDetail resultRecord = msc_050000Mapper.selectResultRecord(param);
        List<ObsrOpnnDetail> obsrOpnnList = msc_050000Mapper.selectObsrOpnnList(resultRecord);
        detail.setResultRecord(resultRecord);
        detail.setObsrOpnn(obsrOpnnList);
    }

    private void loadAllInfo(TabDetailRequest param, Detail detail) {
        loadBasicInfo(param, detail);
        loadResultRecordAndOpinions(param, detail);
    }
}
