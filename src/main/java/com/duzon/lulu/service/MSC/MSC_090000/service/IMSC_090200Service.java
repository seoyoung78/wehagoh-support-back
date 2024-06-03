package com.duzon.lulu.service.MSC.MSC_090000.service;

import com.duzon.common.model.LuluResult;
import com.duzon.lulu.service.MSC.MSC_090000.model.*;

import java.util.*;

public interface IMSC_090200Service {
    List<SpcmCtrn> selectSpcmSetList(HashMap<String, Object> param);
    List<CommonData> selectCtnrList();
    LuluResult saveSpcmSet(SpcmCtrn param);
    LuluResult saveCtnr(SpcmCtrn param);
    LuluResult saveCtnrList(Ctrn param);
}
