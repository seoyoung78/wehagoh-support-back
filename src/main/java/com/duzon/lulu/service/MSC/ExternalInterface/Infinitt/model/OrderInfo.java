package com.duzon.lulu.service.MSC.ExternalInterface.Infinitt.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class OrderInfo {
    @JsonProperty("CHARACTER_SET")
    String CHARACTER_SET; // 언어설정

    @JsonProperty("SCHEDULED_AETITLE")
    String SCHEDULED_AETITLE; // 예정장비

    @JsonProperty("SCHEDULED_DTTM")
    String SCHEDULED_DTTM; // 촬영예정일시

    @JsonProperty("SCHEDULED_MODALITY")
    String SCHEDULED_MODALITY; // 예비장비 종류

    @JsonProperty("SCHEDULED_LOCATION")
    String SCHEDULED_LOCATION; // 촬영예정장소

    @JsonProperty("SCHEDULED_PROC_ID")
    String SCHEDULED_PROC_ID; // 검사처방의 prsc_cd

    @JsonProperty("SCHEDULED_PROC_DESC")
    String SCHEDULED_PROC_DESC; // 검사처방의 prsc-=_nm

    @JsonProperty("SCHEDULED_PROC_STATUS")
    String SCHEDULED_PROC_STATUS; // 검사 버튼 클릭시 NW, 검사취소 버튼 클릭시 CA

    @JsonProperty("CONTRAST_AGENT")
    String CONTRAST_AGENT; // 조영제(조영여부)

    @JsonProperty("REQUESTED_PROC_ID")
    String REQUESTED_PROC_ID; // 검사번호, prsc_cd

    @JsonProperty("REQUESTED_PROC_DESC")
    String REQUESTED_PROC_DESC; // 검사설명

    @JsonProperty("REQUESTED_PROC_CODES")
    String REQUESTED_PROC_CODES; // 검사코드

    @JsonProperty("REQUESTED_PROC_COMMENTS")
    String REQUESTED_PROC_COMMENTS; // 검사 기타정보(주석)

    @JsonProperty("STUDY_INSTANCE_UID")
    String STUDY_INSTANCE_UID; // 검사 고유 번호

    @JsonProperty("PROC_PLACER_ORDER_NO")
    String PROC_PLACER_ORDER_NO; // 검사처방번호

    @JsonProperty("PROC_FILLER_ORDER_NO")
    String PROC_FILLER_ORDER_NO; // 검사접수번호

    @JsonProperty("ACCESSION_NO")
    String ACCESSION_NO; // 접수번호(검사식별번호), pacs_no

    @JsonProperty("ATTEND_DOCTOR")
    String ATTEND_DOCTOR;

    @JsonProperty("PERFORM_DOCTOR")
    String PERFORM_DOCTOR;

    @JsonProperty("CONSULT_DOCTOR")
    String CONSULT_DOCTOR;

    @JsonProperty("REQUEST_DOCTOR")
    String REQUEST_DOCTOR;

    @JsonProperty("REFER_DOCTOR")
    String REFER_DOCTOR;

    @JsonProperty("REQUEST_DEPARTMENT")
    String REQUEST_DEPARTMENT;

    @JsonProperty("IMAGING_REQUEST_DTTM")
    String IMAGING_REQUEST_DTTM;

    @JsonProperty("ISR_PLACER_ORDER_NO")
    String ISR_PLACER_ORDER_NO;

    @JsonProperty("ISR_FILLER_ORDER_NO")
    String ISR_FILLER_ORDER_NO;

    @JsonProperty("PATIENT_NAME")
    String PATIENT_NAME; // pt_nm

    @JsonProperty("PATIENT_ID")
    String PATIENT_ID; // pid

    @JsonProperty("OTHER_PATIENT_NAME")
    String OTHER_PATIENT_NAME; // 영문이름 hooptbaim의 pt_frst_engl_nm + pt_last_engl_nm

    @JsonProperty("OTHER_PATIENT_ID")
    String OTHER_PATIENT_ID; // 주민등록번호, pt_frrn + pt_srrn

    @JsonProperty("PATIENT_BIRTH_DATE")
    String PATIENT_BIRTH_DATE; // 생년월일 dobr

    @JsonProperty("PATIENT_SEX")
    String PATIENT_SEX; // 성별 sex_cd, 공통코드: CZ1001

    @JsonProperty("PATIENT_WEIGHT")
    String PATIENT_WEIGHT; // 체중, medbdyasn(신체사정)의 wght

    @JsonProperty("PATIENT_SIZE")
    String PATIENT_SIZE; // 신장(키), medbdyasn(신체사정)의 hght

    @JsonProperty("PATIENT_STATE")
    String PATIENT_STATE; // 환자 상태

    @JsonProperty("TEL_NO")
    String TEL_NO; // 전화번호

    @JsonProperty("EMAIL")
    String EMAIL; // 이메일

    @JsonProperty("BLOOD_TYPE")
    String BLOOD_TYPE; // 혈액형

    @JsonProperty("CURRENT_DEPARTMENT")
    String CURRENT_DEPARTMENT; // 진료과

    @JsonProperty("CURRENT_DOCTOR_ID")
    String CURRENT_DOCTOR_ID; // 진료의 id

    @JsonProperty("CURRENT_DOCTOR_NAME")
    String CURRENT_DOCTOR_NAME; // 진료의 명

    @JsonProperty("PREGNANCY_STATUS")
    String PREGNANCY_STATUS; // 임신 여부

    @JsonProperty("DIAGNOSIS")
    String DIAGNOSIS; // 진단명 dgns_nm

    @JsonProperty("REGISTER_DTTM")
    String REGISTER_DTTM; // 예약일시
}
