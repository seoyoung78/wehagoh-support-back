package com.duzon.lulu.service.MSC.MSC_060000.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
public class SelectedPatients {
    @NotNull
    private List<String> patientList;
}
