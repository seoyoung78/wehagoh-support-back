package com.duzon.lulu.service.MSC.ExternalInterface.Irm.service;

import com.duzon.common.model.LuluResult;
import com.duzon.lulu.service.MSC.ExternalInterface.Irm.model.Order;

import java.util.*;

public interface IIrmService {
    Object updateSave(Order param) throws Exception;
    Object updateInterpret(Order param) throws Exception;
    LuluResult sendIan(HashMap<String, Object> param) throws Exception;
    LuluResult saveReport(HashMap<String, Object> param) throws Exception;
}
