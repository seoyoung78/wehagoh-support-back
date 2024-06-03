package com.duzon.lulu.service.MSC.MSC_090000.model;

import lombok.Data;

@Data
public class Prescription {
    private String slip_cd; // Slip코드
    private String slip_nm; // Slip명

    private String prsc_cd; // 처방코드
    private String prsc_nm; // 처방명

    private String exmn_rslt_yn; // 검사결과 사용여부

    private String search_path_text; // AutoComplete에 보여질 텍스트
    private String set_yn; // SET 유무

    // 조회조건
    private String search_type; // 검색 타입(1: 처방&수가, 2: 신규 처방)
    private String keyword;     // 자동완성 검색 키워드
}
