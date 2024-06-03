package com.duzon.lulu.service.MSC.MSC_050000.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ObsrOpnnDetail {
    private String pid;
    private Integer ends_rslt_rcrd_sqno;
    private Integer ends_obsr_opnn_sqno;
    private String obsr_opnn_site_1;
    private String obsr_opnn_site_2;
    private String obsr_opnn_cnts;
    private String obsr_opnn;
    private String tisu_exmn_yn;
    private String tisu_exmn_noit;
    private String tisu_exmn_rslt_1;
    private String tisu_exmn_rslt_2;
    private String dgsg_no;

    @Override
    public String toString() {
        return "ObsrOpnn{" +
                "pid='" + pid + '\'' +
                ", ends_rslt_rcrd_sqno=" + ends_rslt_rcrd_sqno +
                ", ends_obsr_opnn_sqno=" + ends_obsr_opnn_sqno +
                ", obsr_opnn_site_1='" + obsr_opnn_site_1 + '\'' +
                ", obsr_opnn_site_2='" + obsr_opnn_site_2 + '\'' +
                ", obsr_opnn_cnts='" + obsr_opnn_cnts + '\'' +
                ", obsr_opnn='" + obsr_opnn + '\'' +
                ", tisu_exmn_yn='" + tisu_exmn_yn + '\'' +
                ", tisu_exmn_noit='" + tisu_exmn_noit + '\'' +
                ", tisu_exmn_rslt_1='" + tisu_exmn_rslt_1 + '\'' +
                ", tisu_exmn_rslt_2='" + tisu_exmn_rslt_2 + '\'' +
                ", dgsg_no='" + dgsg_no + '\'' +
                '}';
    }
}
