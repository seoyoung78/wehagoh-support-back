package com.duzon.lulu.service.MSC.MSC_040000.model;

import lombok.Data;

@Data
public class Radiology {
    private String pid;
    private String prsc_date;
    private String prsc_sqno;
    private String del_yn;
    private String iptn_rslt;
    private String pacs_link_path;
    private String pacs_co_cd;
    private String pacs_no;
    private String cndt_dt;
    private String sign_lctn;
}
