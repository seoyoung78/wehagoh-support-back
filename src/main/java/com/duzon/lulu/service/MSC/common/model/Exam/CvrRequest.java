package com.duzon.lulu.service.MSC.common.model.Exam;

import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
public class CvrRequest {
    @NotNull
    @Size(min = 1)
    private List<@Valid CvrDetail> detailsList;
    @NotNull
    private String exrmClsfCd;
    @NotNull
    private String date;
    @NotNull
    private String pt_nm;
    private String authorization;
    private String transactionId;
    private Long company_no;
}
