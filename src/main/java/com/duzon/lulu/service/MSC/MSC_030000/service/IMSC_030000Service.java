package com.duzon.lulu.service.MSC.MSC_030000.service;

import com.duzon.common.model.LuluResult;
import com.duzon.lulu.service.MSC.MSC_030000.model.UpdateRequest;
import com.duzon.lulu.service.MSC.common.model.Exam.DetailQueryKey;
import com.duzon.lulu.service.MSC.common.model.Exam.PrscStatParam;

import java.util.HashMap;

public interface IMSC_030000Service {
    LuluResult detail(DetailQueryKey param);
    LuluResult reception(PrscStatParam param);

    LuluResult statusAndDetailUpdateService(UpdateRequest param);

    LuluResult selectHistory(DetailQueryKey param);

}
