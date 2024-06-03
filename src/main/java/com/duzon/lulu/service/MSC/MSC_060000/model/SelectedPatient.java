package com.duzon.lulu.service.MSC.MSC_060000.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class SelectedPatient {
    @NotNull
    private String pid;
}
