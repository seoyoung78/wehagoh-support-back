package com.duzon.lulu.service.MSC.MSC_050000.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Detail {
    private BasicInfoDetail basicInfo; // 기초정보
    private ResultRecordDetail resultRecord; // 결과기록
    private List<ObsrOpnnDetail> obsrOpnn; // 관찰소견
    private ExmnInfo exmnInfo; // 검사 관련 정보
}
