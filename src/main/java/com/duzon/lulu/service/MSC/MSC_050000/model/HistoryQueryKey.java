package com.duzon.lulu.service.MSC.MSC_050000.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class HistoryQueryKey {
    @NotNull
    private String pid;
    @NotNull
    private String prsc_date;
    @NotNull
    private String prsc_sqno;
    @NotNull
    private String tabId;

}
