package com.duzon.lulu.service.MSC.MSC_010000.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MSC_010100GridResModel {
    private String pid; // 환자등록번호
    private String prsc_clsf_cd; // 처방구분코드
    private String prsc_date; // 처방일자
    private String pt_nm; // 이름 (우측상단 환자정보에도 같이 사용함)
    private String age_cd; // 성별/나이 (우측상단 환자정보에도 같이 사용함)
    private String recep_dt; // 접수시간(진단, 기능, 영상, 내시경 통일)
    private String mdcr_user_nm; // 진료의 (우측상단 환자정보에도 같이 사용함)
    private String state_cd; // 상태코드
}
