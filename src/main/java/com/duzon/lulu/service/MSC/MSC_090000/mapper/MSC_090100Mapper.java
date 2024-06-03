package com.duzon.lulu.service.MSC.MSC_090000.mapper;

import com.duzon.lulu.service.MSC.MSC_090000.model.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.*;

@Mapper
public interface MSC_090100Mapper {
    // 트리
    List<Tree> selectTreeList(HashMap<String, Object> param);

    // 검체종류/검체용량 조회
    List<CommonData> selectCommonData(HashMap<String, Object> param);

    // 처방 리스트()
    List<Prescription> selectPrscList(HashMap<String, Object> param);

    // 자동완성(검색)
    List<Tree> selectSearchList(HashMap<String, Object> param);

    Form selectForm(HashMap<String, Object> param);
    // 검사 참고치 조회
    List<RefCvr> selectRefCvrList(HashMap<String, Object> param);
    List<Unit> selectUnitList(HashMap<String, Object> param);
    List<Group> selectSetList(HashMap<String, Object> param);
    List<Group> selectExmnList(HashMap<String, Object> param);

    int saveForm(Form param);
    // 진담검사 마스터 저장
    int saveC1Form(Form param);
    // 진단검사 마스터 이력 저장
    int insertC1FormHistory(Form form);

    int saveSet(Group param);
    int saveSpcm(RefValue param);
    int saveRefCvr(RefCvr param);
    void insertRefCvrHistory(RefCvr param);
    int saveUnit(Unit param);;
    void insertUnitHistory(Unit param);
    // 검사 참고치 삭제(모두 삭제)
    void deleteRefCvr(Form param);
    void deleteUnit(Form param);

    // 처방 코드 존재 유무 확인
    Boolean selectExsistPrsc(Form param);
    // 하위 검사SET 존재 유무 확인
    Boolean selectExsistSet(Form param);
}
