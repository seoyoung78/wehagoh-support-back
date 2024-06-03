package com.duzon.lulu.service.MSC.MSC_060000.service;

import com.duzon.common.model.LuluResult;
import com.duzon.lulu.service.MSC.MSC_060000.model.SaveRequired;
import com.duzon.lulu.service.MSC.MSC_060000.model.TrtrDtUpdate;
import com.duzon.lulu.service.MSC.common.model.Exam.DetailQueryKey;
import com.duzon.lulu.service.MSC.common.model.Exam.PrscStatParam;

import java.util.HashMap;
import java.util.List;

public interface IMSC_060000Service {
    LuluResult selectDefault();

    LuluResult selectReceptionList(HashMap<String, Object> param);

    LuluResult selectPatientInfo(HashMap<String, Object> param);

    LuluResult selectPrscList(HashMap<String, Object> param);

    LuluResult reception(PrscStatParam param);

    LuluResult save(SaveRequired param);

    LuluResult updateMdtrHopeDate(List<HashMap<String, Object>> param);
    LuluResult updateDcRqstY(PrscStatParam param) throws Exception;
    LuluResult updateTrtmDt(TrtrDtUpdate param);
    LuluResult selectRecord(HashMap<String, Object> param);
    LuluResult selectHistory(DetailQueryKey param);
}
