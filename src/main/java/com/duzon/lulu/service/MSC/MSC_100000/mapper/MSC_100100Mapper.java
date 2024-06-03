package com.duzon.lulu.service.MSC.MSC_100000.mapper;

import com.duzon.lulu.service.MSC.MSC_100000.model.MSC_100100OpnnEndsModel;
import com.duzon.lulu.service.MSC.MSC_100000.model.MSC_100100ExmnDvcdModel;
import com.duzon.lulu.service.MSC.MSC_100000.model.MSC_100100OpnnModel;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface MSC_100100Mapper {
    List<MSC_100100ExmnDvcdModel> getExmnDvcd(Map<String, Object> param);
    List<MSC_100100OpnnModel> getOpnnList(Map<String, Object> param);
    List<MSC_100100OpnnModel> searchOpnn(Map<String, Object> param);
    MSC_100100OpnnModel getOpnn(Map<String, Object> param);
    List<MSC_100100OpnnEndsModel> getEnds(Map<String, Object> param);

    int saveExmnOpnn(Map<String, Object> param);
    int saveEndsOpnn(Map<String, Object> param);
    int editExmnOpnn(Map<String, Object> param);
    int editEndsOpnn(Map<String, Object> param);
    int deleteExmnOpnn(Map<String, Object> param);
    int deleteEndsOpnn(Map<String, Object> param);
    int deleteEndsOpnnByDetlSqno(Map<String, Object> param);
}
