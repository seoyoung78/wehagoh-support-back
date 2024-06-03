package com.duzon.lulu.service.MSC.common.service;

import com.duzon.common.model.LuluResult;
import com.duzon.lulu.service.MSC.common.model.Exam.*;

import java.util.*;

public interface IExamService {
    LuluResult selectReceptionList(HashMap<String, Object> param);

    LuluResult selectResultList(HashMap<String, Object> param);

    LuluResult selectPrscList(HashMap<String, Object> param);

    LuluResult updatePrscStat(PrscStatParam param) throws Exception;

    LuluResult updatePrscStatItem(HashMap<String, Object> param);
    LuluResult validateAndConductCancelUpdate(LuluResult result, HashMap<String, Object> param);

    LuluResult selectExamCheck(PrscStatParam param);

    LuluResult requestPrsc(PrscStatParam param) throws Exception;

    LuluResult dcPrsc(PrscStatParam param) throws Exception;

    LuluResult sendNoti(HashMap<String, Object> param) throws Exception;

    LuluResult sendNotiList(PrscStatParam param) throws Exception;

    LuluResult sendPrgrProgressNoti(Progress param) throws Exception;
    LuluResult sendPacsAlertNoti(PrscStatParam param) throws Exception;
    LuluResult sendCvrRequestNoti(CvrRequest param) throws Exception;
    LuluResult sendIssueNoti(IssueRequest param) throws Exception;
}
