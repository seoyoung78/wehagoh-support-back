package com.duzon.lulu.service.MSC.ExternalInterface.Saerom.service;

import java.util.HashMap;

import com.duzon.common.model.LuluResult;

public interface ISaeromService {

    Object getExmprsList(HashMap<String, String> Param) throws Exception;
    Object getLmerstList(HashMap<String, String> Param) throws Exception;
    Object setLmerstData(HashMap<String, String> Param) throws Exception;
    /**
     * 새롬 검사결과 조회.
     * @author khgkjg12 강현구A
     * @date Apr 16, 2024
     * @param param pid, rcpn_no
     * @return 검사코드, 검사결과값.
     */
    LuluResult rtrvRptgList(HashMap<String, Object> param);
}
