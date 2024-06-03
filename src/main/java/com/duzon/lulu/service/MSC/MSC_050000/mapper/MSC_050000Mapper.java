package com.duzon.lulu.service.MSC.MSC_050000.mapper;

import com.duzon.lulu.service.MSC.MSC_050000.model.*;
import com.duzon.lulu.service.MSC.common.model.Exam.DetailQueryKey;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface MSC_050000Mapper {
    List<CommonCode> selectBasicCodeList(HashMap<String, Object> param);

    List<CommonCode> selectResultCodeList(HashMap<String, Object> param);

    ExmnInfo selectExmnInfo(TabDetailRequest param);

    String selectSiteCd(TabDetailRequest param);

    BasicInfoDetail selectBasicInfo(DetailQueryKey param);

    List<BasicInfo> selectBasicHistory(HistoryQueryKey param);
    List<ResultRecord> selectRecordHistory(HistoryQueryKey param);
    List<ObsrOpnn> selectObsrOpnnHistory(HistoryQueryKey param);

    ResultRecordDetail selectResultRecord(DetailQueryKey param);

    List<ObsrOpnnDetail> selectObsrOpnnList(ResultRecordDetail resultRecord);
    
    int updateBasicLastFlag(HashMap<String, Object> param);
    
    int updateRecordLastFlag(HashMap<String, Object> param);

    int upsertResult(HashMap<String, Object> param);

    int insertResultHistory(HashMap<String, Object> param);
    ResultRecord selectIptnInfo(HashMap<String, Object> param);

    int insertBasicInfo(@Param("required") HashMap<String, Object> baseParam, @Param("basic") BasicInfo basicInfo);

    int insertRecordInfo(@Param("required") HashMap<String, Object> baseParam, @Param("record") ResultRecord resultRecord);
    int insertObsrOpnn(@Param("required") HashMap<String, Object> baseParam, @Param("opinion") ObsrOpnn obsrOpnn);

    int updateResultStatusToYByDel(HashMap<String, Object> param);

    int updateSave(HashMap<String, Object> param);

    int saveCancel(UpdateRequest param);

    int updateReport(UpdateRequest param);

    int insertResult(UpdateRequest param);

    int updateReportCancel(UpdateRequest param);

    int initialBasicInfo(UpdateRequest param);

    int initialRecordInfo(UpdateRequest param);

    // [미사용] ==========================================================================================

    int insertBasicHistory(HashMap<String, Object> param);
    int insertRecordHistory(HashMap<String, Object> param);
    int insertObsrOpnnHistory(HashMap<String, Object> param);
}
