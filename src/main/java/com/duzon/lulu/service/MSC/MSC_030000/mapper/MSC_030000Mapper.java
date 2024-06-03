package com.duzon.lulu.service.MSC.MSC_030000.mapper;
import com.duzon.lulu.service.MSC.MSC_030000.model.File;
import com.duzon.lulu.service.MSC.MSC_030000.model.ExamResult;
import com.duzon.lulu.service.MSC.MSC_030000.model.History;
import com.duzon.lulu.service.MSC.MSC_030000.model.UpdateRequest;
import com.duzon.lulu.service.MSC.common.model.Exam.DetailQueryKey;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface MSC_030000Mapper {
    ExamResult selectResult(DetailQueryKey param);

    List<File> selectFileList(DetailQueryKey param);

    int upsertResult(HashMap param);

    int insertResultHistory(HashMap param);

    int updateResultDelYn(HashMap param);
    int updateSave(HashMap<String, Object> param);
    int updateReport(HashMap<String, Object> param);
    int saveCancel(HashMap<String, Object> param);
    int insertResult(HashMap<String, Object> param);
    int updateReportCancel(HashMap<String, Object> param);
    int insertFileItem(@Param("required") HashMap<String, Object> param, @Param("file") HashMap<String, Object> file);
    int updateFileToDeleted(UpdateRequest param);

    List<History> selectHistory(DetailQueryKey param);
}