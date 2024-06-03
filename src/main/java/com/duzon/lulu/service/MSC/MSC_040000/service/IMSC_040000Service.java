package com.duzon.lulu.service.MSC.MSC_040000.service;

import com.duzon.common.model.LuluResult;
import com.duzon.lulu.service.MSC.common.model.Exam.PrscStatParam;

import java.util.*;

public interface IMSC_040000Service {
    Integer insertHistory(HashMap<String, Object> param);
    LuluResult saveConduct(PrscStatParam param);
    LuluResult selectRadiology(HashMap<String, Object> param);
    LuluResult save(HashMap<String, Object> param);
    LuluResult interpret(HashMap<String, Object> param) throws Exception;
    LuluResult selectHistory(HashMap<String, Object> param);
}
