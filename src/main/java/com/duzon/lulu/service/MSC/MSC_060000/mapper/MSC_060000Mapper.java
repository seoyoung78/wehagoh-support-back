package com.duzon.lulu.service.MSC.MSC_060000.mapper;

import com.duzon.lulu.service.MSC.MSC_060000.model.*;
import com.duzon.lulu.service.MSC.common.model.Common.PatientInfo;
import com.duzon.lulu.service.MSC.common.model.Exam.DetailQueryKey;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface MSC_060000Mapper {
    List<CommonCode> selectComCodeList(HashMap param);
    List<Mdtr> selectReceptionList(HashMap param);
    PatientInfo selectPatientInfo(HashMap param);
    List<Details> selectPrscList(HashMap<String, Object> param);
    String selectStatCd(HashMap<String, Object> param);
    int updateTrtmStart(HashMap<String, Object> param);
    int updateTrtmStartCancel(HashMap<String, Object> param);
    int updateTrtmEnd(HashMap<String, Object> param);
    int upsertResultStart(HashMap<String, Object> param);
    int insertResultHistory(HashMap<String, Object> param);
    Boolean selectExistsResult(HashMap<String, Object> param);
    int updateResultStartCancel(HashMap<String, Object> param);
    int updateResultEnd(HashMap<String, Object> param);
    int updateTrtmEndCancel(HashMap<String, Object> param);
    int updateResultCancel(HashMap<String, Object> param);
    int updateCompleteReport(HashMap<String, Object> param);
    int updateCompleteReportCancel(HashMap<String, Object> param);
    int updateResultCompleteReport(HashMap<String, Object> param);
    int updateResultCompleteReportCancel(HashMap<String, Object> param);
    int updateResultInput(SaveRequired param);

    int updateMdtrHopeDate(HashMap<String, Object> param);

    int updateDcRqstY(HashMap<String, Object> param);
    int updateTrtmDt(TrtrDtUpdate param);
    List<Record> selectRecord(HashMap<String, Object> param);
    List<History> selectHistory(DetailQueryKey param);
}
