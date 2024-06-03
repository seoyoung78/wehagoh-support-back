package com.duzon.lulu.service.MSC.ExternalInterface.Saerom.mapper;

import com.duzon.lulu.service.MSC.ExternalInterface.Saerom.model.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Mapper
public interface SaeromMapper {
    //  B: 검사대기, C: 접수, E: 검사중, M: 중간보고 ,N: 최종보고 => 결과값 입력하고 저장누르면 중간보고 상태
    // B->C->E->M->N
    List<ExmprsData> getExmprsList(HashMap param);
    List<LmerstData> getLmerstList(HashMap param);
    int setLmerstData(Object param);
    int setCrexmprsntStateUpdate(CrexmprsntData sPagetCreatinineDataram);
    CrexmprsntData getCrexmprsntData(HashMap sParam);
    int getCreatinineData(HashMap param);

    int crtnExmnRsltHstr(HashMap param);
    //새롬 검사결과조회.
    ArrayList<HashMap<String, Object>> rtrvRptgList(HashMap<String, Object> param);
}
