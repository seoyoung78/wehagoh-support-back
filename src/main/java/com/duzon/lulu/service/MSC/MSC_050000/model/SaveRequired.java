package com.duzon.lulu.service.MSC.MSC_050000.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
public class SaveRequired  {
    @NotNull
    @Valid RequiredFields requiredFields;
    @NotNull
    @Valid BasicInfo basicInfo;
    @NotNull
    @Valid ResultRecord resultRecord;
    @NotNull
    List<@Valid ObsrOpnn> obsrOpnnList;
}