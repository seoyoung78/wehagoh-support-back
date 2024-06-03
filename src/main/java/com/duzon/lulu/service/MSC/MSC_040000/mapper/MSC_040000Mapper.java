package com.duzon.lulu.service.MSC.MSC_040000.mapper;

import com.duzon.lulu.service.MSC.MSC_040000.model.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.*;

@Mapper
public interface MSC_040000Mapper {
    Radiology selectRadiology(HashMap<String, Object> param);

    Integer insertHistory(HashMap<String, Object> param);

    Integer insertConduct(HashMap<String, Object> param);

    Integer updateSave(HashMap<String, Object> param);

    Integer updateSaveCancel(HashMap<String, Object> param);

    Integer updateInterpret(HashMap<String, Object> param);

    Integer updateInterpretCancel(HashMap<String, Object> param);

    Integer insertResult(HashMap<String, Object> param);
    List<History> selectHistory(HashMap<String, Object> param);
}
