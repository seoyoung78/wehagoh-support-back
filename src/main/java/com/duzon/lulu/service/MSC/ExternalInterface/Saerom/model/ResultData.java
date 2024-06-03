package com.duzon.lulu.service.MSC.ExternalInterface.Saerom.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class ResultData extends ExmprsData{

    String pid;
    String prsc_date;
    String spcm_no_1;
}
