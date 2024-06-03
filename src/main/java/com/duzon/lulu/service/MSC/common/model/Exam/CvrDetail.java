package com.duzon.lulu.service.MSC.common.model.Exam;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class CvrDetail {
    @NotEmpty
    private String prsc_dr_sqno; // 처방 의사 유저 시퀀스
    @NotEmpty
    private String mdcr_dr_id; // 진료 의사 유저 시퀀스
    private String prsc_nm;
    private String result;
    @NotEmpty
    private String result_date;
    private String range;
}
