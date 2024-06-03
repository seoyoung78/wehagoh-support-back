package com.duzon.lulu.service.MSC.MSC_060000.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
public class PatientQueryParams {
    @NotNull
    private String from;
    @NotNull
    private String to;
    @NotNull
    private List<Long> deptList;
    private String pid;
}
