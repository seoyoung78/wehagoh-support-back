package com.duzon.lulu.service.MSC.MSC_050000.service;

import com.duzon.common.model.LuluResult;
import com.duzon.lulu.service.MSC.MSC_050000.model.HistoryQueryKey;
import com.duzon.lulu.service.MSC.MSC_050000.model.SaveRequired;
import com.duzon.lulu.service.MSC.MSC_050000.model.TabDetailRequest;
import com.duzon.lulu.service.MSC.MSC_050000.model.UpdateRequest;
import com.duzon.lulu.service.MSC.common.model.Exam.DetailQueryKey;
import com.duzon.lulu.service.MSC.common.model.Exam.PrscStatParam;

import java.util.HashMap;

public interface IMSC_050000Service {
    LuluResult common();

    LuluResult detail(DetailQueryKey param);

    LuluResult detailFetcher(TabDetailRequest param);

    LuluResult reception(PrscStatParam param);

    LuluResult save(SaveRequired param);

    LuluResult result(UpdateRequest param);
    LuluResult selectHistory(HistoryQueryKey param);
}
