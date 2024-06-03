package com.duzon.lulu.service.MSC.MSC_080000.service;

import com.duzon.lulu.service.MSC.MSC_080000.model.MSC_080100ReqParamModel;
import com.duzon.lulu.service.MSC.MSC_080000.model.MSC_080100ResModel;

import java.util.List;

public interface IMSC_080100Service {
    List<MSC_080100ResModel> getExmPatient(MSC_080100ReqParamModel param);
}
