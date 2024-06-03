package com.duzon.lulu.service.MSC.common.service;

import com.duzon.common.model.LuluResult;

import java.util.HashMap;

public interface IMobileService {

    LuluResult exrmList(HashMap<String, Object> Param) throws Exception;

    LuluResult exrmRsltList(HashMap<String, Object> param);
}
