package com.duzon.lulu.service.MSC.ExternalInterface.Infinitt.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
public class LogOrderInfo {

    @JsonProperty("ISR_PLACER_ORDER_NO")
    String ISR_PLACER_ORDER_NO;

    @JsonProperty("ISR_FILLER_ORDER_NO")
    String ISR_FILLER_ORDER_NO;

    @JsonProperty("STUDY_INSTANCE_UID")
    String STUDY_INSTANCE_UID;

    @JsonProperty("ACCESSION_NO")
    String ACCESSION_NO;

    @JsonProperty("SCHEDULED_PROC_STATUS")
    String SCHEDULED_PROC_STATUS;

    @JsonProperty("STATUS")
    String STATUS;

    @JsonProperty("PATIENT_ID")
    String patient_id;

    String co_code;
    String prsc_date;
    String prsc_sqno;
}
