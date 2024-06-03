package com.duzon.lulu.service.MSC.ExternalInterface.Infinitt.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class StudyInfo {

    @JsonProperty("STUDY_KEY")
    String STUDY_KEY;

    @JsonProperty("PATIENT_ID")
    String PATIENT_ID;

    @JsonProperty("ACCESSION_NO")
    String ACCESSION_NO;

    @JsonProperty("STUDY_STAT")
    String STUDY_STAT;

    @JsonProperty("STUDY_INSTANCE_UID")
    String STUDY_INSTANCE_UID;

    @JsonProperty("STUDY_DTTM")
    String STUDY_DTTM;

    @JsonProperty("REQUEST_CODE")
    String REQUEST_CODE;

    @JsonProperty("REQUEST_NAME")
    String REQUEST_NAME;

    @JsonProperty("PERFORM_MODALITY")
    String PERFORM_MODALITY;

    @JsonProperty("PERFORM_STATION")
    String PERFORM_STATION;

    @JsonProperty("SOURCE_AETITLE")
    String SOURCE_AETITLE;

    @JsonProperty("SCHEDULED_LOCATION")
    String SCHEDULED_LOCATION;

    @JsonProperty("PERFORM_DEPARTMENT")
    String PERFORM_DEPARTMENT;

    @JsonProperty("SCHEDULED_DTTM")
    String SCHEDULED_DTTM;

    @JsonProperty("PATIENT_LOCATION")
    String PATIENT_LOCATION;

    @JsonProperty("REQUEST_DTTM")
    String REQUEST_DTTM;

    @JsonProperty("ADMISSION_ID")
    String ADMISSION_ID;

    @JsonProperty("REQUEST_DOCTOR")
    String REQUEST_DOCTOR;

}
