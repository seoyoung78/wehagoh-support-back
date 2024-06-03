package com.duzon.lulu.service.MSC.MSC_030000.service.impl;

import com.duzon.common.model.LuluResult;
import com.duzon.common.util.ValidationUtil;
import com.duzon.lulu.service.MSC.MSC_030000.model.*;
import com.duzon.lulu.service.MSC.MSC_030000.mapper.MSC_030000Mapper;
import com.duzon.lulu.service.MSC.MSC_030000.service.IMSC_030000Service;
import com.duzon.lulu.service.MSC.common.model.Exam.DetailQueryKey;
import com.duzon.lulu.service.MSC.common.model.Exam.PrscStatParam;
import com.duzon.lulu.service.MSC.common.service.IExamService;
import com.duzon.lulu.service.MSC.util.HealthSessionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import java.util.*;

@Service
public class MSC_030000ServiceImpl implements IMSC_030000Service {
    @Autowired
    private MSC_030000Mapper msc_030000Mapper;

    @Autowired
    private IExamService iExamService;

    @Autowired
    private HealthSessionContext healthSessionContext;

    private static final String EXAM_TYPE = "type";
    private static final String CONDUCT = "Conduct"; // 검사
    private static final String CONDUCT_CANCEL = "ConductCancel"; // 검사취소
    private static final String REPORT = "Report";
    private static final String REPORT_CANCEL = "ReportCancel";
    private static final String SAVE = "Save";
    private static final String SAVE_CANCEL = "SaveCancel";

    @Override
    public LuluResult detail(DetailQueryKey param) {
        LuluResult result = new LuluResult();

        Map<String, Object> resultData = new HashMap<>(); // 리턴 결과
        List<File> fileList = new ArrayList<>(); // 파일 리스트

        // 기능 검사결과 조회
        ExamResult examResult = msc_030000Mapper.selectResult(param);

        // 결과 있을 경우 파일ID 조회
        if (!Objects.isNull(examResult)) {
            List<File> resultFileList = msc_030000Mapper.selectFileList(param);

            if (!CollectionUtils.isEmpty(resultFileList)) {
                fileList = resultFileList;
            }
        }

        resultData.put("examResult", examResult);
        resultData.put("fileList", fileList);

        result.setResultCode(200);
        result.setResultData(resultData);

        return result;
    }

    @Transactional(rollbackFor = {RuntimeException.class, Exception.class})
    @Override
    public LuluResult reception(PrscStatParam param) {
        try {
            if (CONDUCT_CANCEL.equals(param.getType())) {
                healthSessionContext.setContext();
                // 검사 취소 : 처방진행상태코드 단건 업데이트
                return handleConductCancel(param);
            } else {
                // 1. 검사처방 테이블의 처방진행상태코드 update
                LuluResult updatePrscStatResult = iExamService.updatePrscStat((param));
                if (updatePrscStatResult.getResultCode() != 200) {
                    return updatePrscStatResult;
                }
                if (CONDUCT.equals(param.getType())) {
                    // 2. 검사 : 기능 검사 결과 테이블 upsert
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
    public LuluResult statusAndDetailUpdateService(UpdateRequest param) {
        try {
            HashMap<String, Object> keyMap = param.convertKeysToMap();

            // 검사처방 테이블의 처방진행상태코드 update 및 param 참조 전달하여 세션값 추가
            LuluResult updatePrscStatResult = iExamService.updatePrscStatItem((keyMap));

            if (updatePrscStatResult.getResultCode() != 200) {
                return updatePrscStatResult;
            } else {
                insertUploadedFiles(param, keyMap); // uploadList 파라미터 체크 후 필요 시 업데이트
                updateFilesAsDeleted(param); // deleteList 파라미터 체크 후 필요 시 업데이트

                String type = param.getType();
                int affectedRows = 0;

                switch (type) {
                    case SAVE:
                        affectedRows = msc_030000Mapper.updateSave(keyMap);
                        break;
                    case SAVE_CANCEL:
                        affectedRows = msc_030000Mapper.saveCancel(keyMap);
                        break;
                    case REPORT :
                        affectedRows = handleUpdateReport(keyMap);
                        break;
                    case REPORT_CANCEL:
                        affectedRows = msc_030000Mapper.updateReportCancel(keyMap);
                        break;
                    default:
                        break;
                }

                if (affectedRows > 0) {
                    keyMap.put("hstr_stat_cd", param.getHstr_stat_cd());
                    msc_030000Mapper.insertResultHistory(keyMap);
                }
            }

            return updatePrscStatResult;
        } catch (Exception e) {
            throw new RuntimeException("RuntimeException rollback : "+e);
        }
    }

    @Override
    public LuluResult selectHistory(DetailQueryKey param) {
        LuluResult result = new LuluResult();
        result.setResultCode(200);
        result.setResultData(msc_030000Mapper.selectHistory(param));
        return result;
    }

    // 필수 파라미터 체크
    private LuluResult checkParam(HashMap param) {
        String[] requirementKeys = { "pid", "prsc_date", "prsc_sqno" };
        LuluResult result = ValidationUtil.checkContainsKeysValue(requirementKeys, param);
        return result;
    }

    private void handleConduct(PrscStatParam param) {
        for (HashMap details : param.getDetailsList()) {
            details.put(EXAM_TYPE, param.getType());
            int affectedRows = msc_030000Mapper.upsertResult(details);
            if (affectedRows > 0) {
                msc_030000Mapper.insertResultHistory(details);
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
        int affectedRows = msc_030000Mapper.updateResultDelYn(detail);
        if (affectedRows > 0) {
            msc_030000Mapper.insertResultHistory(detail);
        }
    }

    private int handleUpdateReport(HashMap<String, Object> param) throws Exception {
        int affectedRows = msc_030000Mapper.updateReport(param);
        if (affectedRows > 0) {
            // 최종보고 시 mscemnrsn(진료지원 검사결과) 테이블 insert
            msc_030000Mapper.insertResult(param);
        }
//        iExamService.sendNoti(param);
        return affectedRows;
    }

    private void updateFilesAsDeleted(UpdateRequest param) {
        List<String> deleteList = param.getDeleteList();
        if (deleteList != null && !deleteList.isEmpty()) {
            msc_030000Mapper.updateFileToDeleted(param);
        }
    }

    private void insertUploadedFiles(UpdateRequest param, HashMap<String, Object> keyMap) {
        List<HashMap<String, Object>> uploadList = param.getUploadList();
        if (uploadList != null && !uploadList.isEmpty()) {
            for (HashMap<String, Object> file : uploadList) {
                msc_030000Mapper.insertFileItem(keyMap, file);
            }
        }
    }
}
