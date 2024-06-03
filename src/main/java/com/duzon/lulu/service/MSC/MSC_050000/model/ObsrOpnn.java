package com.duzon.lulu.service.MSC.MSC_050000.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class ObsrOpnn {
    @Size(max = 20)
    private String pid;
    private Long ends_rslt_rcrd_sqno;
    private Long ends_obsr_opnn_sqno;
    @Size(max = 200)
    private String obsr_opnn_site_1;
    @Size(max = 200)
    private String obsr_opnn_site_2;
    private String obsr_opnn_cnts;
    @Size(max = 20)
    private String obsr_opnn;
    @Size(max = 1)
    private String tisu_exmn_yn;
    @Size(max = 200)
    private String tisu_exmn_noit;
    @Size(max = 200)
    private String tisu_exmn_rslt_1;
    @Size(max = 200)
    private String tisu_exmn_rslt_2;
    @Size(max = 1000)
    private String obsr_opnn_site_nm_1;
    @Size(max = 1000)
    private String obsr_opnn_site_nm_2;
    @Size(max = 1000)
    private String obsr_opnn_nm;
    @Size(max = 1000)
    private String tisu_exmn_rslt_nm_1;
    @Size(max = 1000)
    private String tisu_exmn_rslt_nm_2;
    @Size(max = 50)
    private String dgsg_no;
}
