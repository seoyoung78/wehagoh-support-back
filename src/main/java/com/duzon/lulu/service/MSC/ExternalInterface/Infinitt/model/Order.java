package com.duzon.lulu.service.MSC.ExternalInterface.Infinitt.model;

import lombok.Data;

@Data
public class Order {
    private String cno;
    private String organizaion_id;
    private String accession_no;
    private String prsc_prgr_stat_cd;
    private String study_dttm;              // 입력일시
    private String finding;                 // 판독소견
    private String author_name;             // 판독의사명
    private String document_dttm;           // 판독일시
    private String dicom_sr_list;             // srlink 목록
    private String creator_id;
    private String create_dttm;
    private String approver_id;
    private String approve_dttm;

    private String authorization;
    private String type;
    private String co_code;
    private String dc_yn;
    private String stat_cd;
    private String hstr_stat_cd;
    private String pid;
    private String prsc_date;
    private Integer prsc_sqno;
    private String exmn_hope_date;
    private Integer exmnRsltSqno;
}
