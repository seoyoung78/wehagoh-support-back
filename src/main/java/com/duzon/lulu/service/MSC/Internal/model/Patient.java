package com.duzon.lulu.service.MSC.Internal.model;

import lombok.Data;

@Data
public class Patient {
    private Integer exrm_dept_sqno;
    private String patient_id;
    private String patient_name;
    private String patient_real_name;
    private String status;
}
