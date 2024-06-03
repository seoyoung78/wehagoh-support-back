package com.duzon.lulu.service.MSC.MSC_090000.service;

import com.duzon.common.model.LuluResult;
import com.duzon.lulu.service.MSC.MSC_090000.model.*;

import java.util.*;

public interface IMSC_090100Service {
    // Slip 트리 조회
    List<Tree> selectTreeList();

    // 검사정보설정에 필요한 공통코드(검체종류, 검체용기)
    List<CommonData> selectCommonData();

    // 처방 리스트 조회
    List<Prescription> selectPrscList(HashMap<String, Object> param);

    // 자동완성(검색)
    List<Tree> selectSearchList(HashMap<String, Object> param);

    // 처방 상세 정보 조회(Form)
    Form selectForm(HashMap<String, Object> param);

    // 검사 SET정보 - 검사 코드 조회(AutoComplete)
    List<Group> selectExmnList(HashMap<String, Object> param);

    // 저장(Insert, Update)
    LuluResult save(Form param);

    // 신규 처방 코드 삭제
    LuluResult delete(Form param);

    // 검체 상/하한치 리스트 조회
    List<RefCvr> selectRefCvrList(HashMap<String, Object> param);
    LuluResult saveRefCvr(RefCvrParam param);
    List<Unit> selectUnitList(HashMap<String, Object> param);
    LuluResult saveUnit(UnitParam param);
}
