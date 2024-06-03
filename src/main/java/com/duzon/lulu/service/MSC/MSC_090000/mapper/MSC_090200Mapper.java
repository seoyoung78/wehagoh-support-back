package com.duzon.lulu.service.MSC.MSC_090000.mapper;

import com.duzon.lulu.service.MSC.MSC_090000.model.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.*;

@Mapper
public interface MSC_090200Mapper {
    List<SpcmCtrn> selectSpcmSetList(HashMap<String, Object> param);
    List<CommonData> selectCtnrList(HashMap<String, Object> param);
    Integer insertSpcmHistory(SpcmCtrn param);
    Integer saveSpcmSet(SpcmCtrn param);
    Integer insertCtnrHistory(SpcmCtrn param);
    Integer saveCtnr(SpcmCtrn param);
}
