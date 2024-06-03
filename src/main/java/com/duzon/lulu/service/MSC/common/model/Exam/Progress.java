package com.duzon.lulu.service.MSC.common.model.Exam;

import lombok.Data;

@Data
public class Progress {
    private String type;
    private String exrmClsfCd;
    private String authorization;
    private String transactionId;
    private Integer deptSqno;
}
