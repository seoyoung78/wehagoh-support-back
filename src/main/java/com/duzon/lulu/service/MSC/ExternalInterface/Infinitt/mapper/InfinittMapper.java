package com.duzon.lulu.service.MSC.ExternalInterface.Infinitt.mapper;

import com.duzon.lulu.service.MSC.ExternalInterface.Infinitt.model.LogOrderInfo;
import com.duzon.lulu.service.MSC.ExternalInterface.Infinitt.model.OrderInfo;
import com.duzon.lulu.service.MSC.ExternalInterface.Infinitt.model.StudyInfo;
import com.duzon.lulu.service.MSC.ExternalInterface.Infinitt.model.UserInfo;
import com.duzon.lulu.service.MSC.ExternalInterface.Infinitt.model.Order;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface InfinittMapper {

    List<OrderInfo> getOrderInfoList(HashMap<String, String> param);

    List<UserInfo> getUserInfoList(HashMap<String, String> param);

    Integer updateOrderInfo(List<LogOrderInfo> param)throws Exception;

    List<StudyInfo> getStudyInfo(HashMap<String, String> param);

    int updateSave(Order param);

    int updateInterpret(Order param) throws Exception;

    int updateConduct(Order param)throws Exception;

    int insertHistory(Order param)throws Exception;

    int insertResult(Order param) throws Exception; // 진료 검사결과 insert

    Integer selectExmnRsltSqno(Order param);

    HashMap<String, Object> selectNotiData(Order param);
}
