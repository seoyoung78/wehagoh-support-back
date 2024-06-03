package com.duzon.lulu.service.MSC.MSC_010000.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MSC_010100ReqParamModel {
    private String pid; // 환자 등록번호
    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
    private Date prsc_date; // 검사일자
}
