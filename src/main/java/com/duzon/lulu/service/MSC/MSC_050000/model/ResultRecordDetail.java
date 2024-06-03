package com.duzon.lulu.service.MSC.MSC_050000.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResultRecordDetail {
    private String pid;
    private Integer ends_rcrd_sqno;
    private Integer ends_rslt_rcrd_sqno;
    private String frst_rgst_usid;
    private String frst_rgst_dt;
    private String last_updt_usid;
    private String last_updt_dt;
    private String exmn_nm;
    private String hstr_stat_cd;
    private String dgsg_no;
    private String sign_yn;

    private String exmn_date;
    private String dtrm_date;
    private String ends_dr_nm;
    private String gscn_pt_yn;
    private String stmc_bctr_exmn_rslt;
    private String clo_cd;
    private String trtm_mdtr_cd;
    private String trtm_mdtr_cnts;
    private String exsn_prcd_actg_mthd;
    private String cmpc_yn;
    private String cmpc_cnts;
    private String cmpc_trtm_mthd;
    private String cmpc_prgr;
    private String cncr_mdex_advc_matr;
    private String advc_matr;
    private String advc_matr_cnts;
    private String etnl_obsr_opnn;
    private String dre_opnn;
    private Integer obsr_inrt_time_ms;
    private Integer obsr_exmn_end_time_ms;
    private Integer obsr_recl_time_ms;
    private String bpre_dgre_LC;
    private String bpre_dgre_TC;
    private String bpre_dgre_RC;
    private String rslt_opnn_1;
    private String rslt_opnn_2;
    private String rslt_opnn_3;
    private String plyp_exsn_prcd_actg_yn;

    @Override
    public String toString() {
        return "ResultRecord{" +
                "pid='" + pid + '\'' +
                ", ends_rcrd_sqno=" + ends_rcrd_sqno +
                ", ends_rslt_rcrd_sqno=" + ends_rslt_rcrd_sqno +
                ", frst_rgst_usid='" + frst_rgst_usid + '\'' +
                ", frst_rgst_dt='" + frst_rgst_dt + '\'' +
                ", last_updt_usid='" + last_updt_usid + '\'' +
                ", last_updt_dt='" + last_updt_dt + '\'' +
                ", exmn_nm='" + exmn_nm + '\'' +
                ", hstr_stat_cd='" + hstr_stat_cd + '\'' +
                ", dgsg_no='" + dgsg_no + '\'' +
                ", sign_yn='" + sign_yn + '\'' +
                ", exmn_date='" + exmn_date + '\'' +
                ", dtrm_date='" + dtrm_date + '\'' +
                ", ends_dr_nm='" + ends_dr_nm + '\'' +
                ", gscn_pt_yn='" + gscn_pt_yn + '\'' +
                ", stmc_bctr_exmn_rslt='" + stmc_bctr_exmn_rslt + '\'' +
                ", clo_cd='" + clo_cd + '\'' +
                ", trtm_mdtr_cd='" + trtm_mdtr_cd + '\'' +
                ", trtm_mdtr_cnts='" + trtm_mdtr_cnts + '\'' +
                ", exsn_prcd_actg_mthd='" + exsn_prcd_actg_mthd + '\'' +
                ", cmpc_yn='" + cmpc_yn + '\'' +
                ", cmpc_cnts='" + cmpc_cnts + '\'' +
                ", cmpc_trtm_mthd='" + cmpc_trtm_mthd + '\'' +
                ", cmpc_prgr='" + cmpc_prgr + '\'' +
                ", cncr_mdex_advc_matr='" + cncr_mdex_advc_matr + '\'' +
                ", advc_matr='" + advc_matr + '\'' +
                ", advc_matr_cnts='" + advc_matr_cnts + '\'' +
                ", etnl_obsr_opnn='" + etnl_obsr_opnn + '\'' +
                ", dre_opnn='" + dre_opnn + '\'' +
                ", obsr_inrt_time_ms=" + obsr_inrt_time_ms +
                ", obsr_exmn_end_time_ms=" + obsr_exmn_end_time_ms +
                ", obsr_recl_time_ms=" + obsr_recl_time_ms +
                ", bpre_dgre_LC='" + bpre_dgre_LC + '\'' +
                ", bpre_dgre_TC='" + bpre_dgre_TC + '\'' +
                ", bpre_dgre_RC='" + bpre_dgre_RC + '\'' +
                ", rslt_opnn_1='" + rslt_opnn_1 + '\'' +
                ", rslt_opnn_2='" + rslt_opnn_2 + '\'' +
                ", rslt_opnn_3='" + rslt_opnn_3 + '\'' +
                ", plyp_exsn_prcd_actg_yn='" + plyp_exsn_prcd_actg_yn + '\'' +
                '}';
    }
}
