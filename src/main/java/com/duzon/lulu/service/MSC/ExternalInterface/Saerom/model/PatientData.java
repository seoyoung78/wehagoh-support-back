package com.duzon.lulu.service.MSC.ExternalInterface.Saerom.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class PatientData extends ExmprsData{
    String pid;
    String pt_nm;
    String sex_cd;
    String age_cd;
}
