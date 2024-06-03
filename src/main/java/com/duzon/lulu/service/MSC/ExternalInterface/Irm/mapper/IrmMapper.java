package com.duzon.lulu.service.MSC.ExternalInterface.Irm.mapper;

import com.duzon.lulu.service.MSC.ExternalInterface.Irm.model.Order;
import org.apache.ibatis.annotations.Mapper;

import java.util.*;

@Mapper
public interface IrmMapper {
    int updateSave(Order param) throws Exception; // 영상알림 업데이트
    int updateInterpret(Order param) throws Exception; // 임시저장, 최종판독 저장
    int insertHistory(Order param) throws Exception; // 영상결과 이력 추가
    int updateConduct(Order param) throws Exception; // 영상/기능 검사결과 insert
    int insertResult(Order param) throws Exception; // 진료 검사결과 insert
    void deleteResult(Order param) ;
    Integer selectExmnRsltSqno(Order param); // 진료_검사결과에 해당 검사결과 존재 확인 - 검사결과일련번호반환
    HashMap<String, Object> selectNotiData(Order param);
}
