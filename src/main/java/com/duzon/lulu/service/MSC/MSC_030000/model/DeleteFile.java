package com.duzon.lulu.service.MSC.MSC_030000.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DeleteFile {
    private List<String> fileList;
    private String pid;
    private String prsc_date;
    private String prsc_sqno;
    private String iptn_rslt;
    private String type;
}
