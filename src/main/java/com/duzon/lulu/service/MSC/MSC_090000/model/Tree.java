package com.duzon.lulu.service.MSC.MSC_090000.model;

import lombok.Data;

@Data
public class Tree {
    private String code;           // 트리 노드 코드
    private String text;           // 트리 노드 라벨 텍스트
    private String parent_code;         // 트리 부모 노드 코드
    private String search_path_text;    // OBTComplete 검색용
    private String prsc_clsf_cd;        // 처방분류코드
    private String prsc_clsf_nm;        // 처방분류이름
    private String exmn_rslt_tycd;      // 검사결과유형코드(수치: 'W' / 텍스트: 'T')
    private String lwrn_yn;      // 검사결과만 사용하는 코드여부
}
