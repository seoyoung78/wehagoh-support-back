package com.duzon.lulu.service.MSC.MSC_080000.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MSC_080100ReqParamModel {
    private List<String> hope_exrm_cd;
    private String pid;
    private String prsc_date_from;
    private String prsc_date_to;
}