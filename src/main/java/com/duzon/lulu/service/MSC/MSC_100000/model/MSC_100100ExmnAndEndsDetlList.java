package com.duzon.lulu.service.MSC.MSC_100000.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
/* 검사소견/내시경상세 List 형태로 변환 */
public class MSC_100100ExmnAndEndsDetlList {
    private long exmn_opnn_sqno;
    private String exmn_dvcd;
    private String ends_exmn_dvcd;
    private String exmn_opnn_titl;
    private String exmn_opnn_cnts;
    private List<MSC_100100EndsDetlModel> ends_detl_list;
}

