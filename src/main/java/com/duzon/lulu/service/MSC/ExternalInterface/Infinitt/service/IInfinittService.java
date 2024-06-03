package com.duzon.lulu.service.MSC.ExternalInterface.Infinitt.service;


import com.duzon.lulu.service.MSC.ExternalInterface.Infinitt.model.Order;

import java.util.HashMap;


public interface IInfinittService {

    Object getOrderInfo(HashMap<String, String> param) throws Exception;
    Object getUerInfo(HashMap<String, String> param) throws Exception;
    Object updateOrderInfo(HashMap<String, String> param) throws Exception;
    Object getStudyInfo(HashMap<String, String> param) throws Exception;
    Object saveReport(HashMap<String, String> param) throws Exception;
    Integer updateSave(Order param) throws Exception;
    int updateInterpret(Order param) throws Exception;
    Object sendIan(HashMap<String, String> param) throws Exception;
}
