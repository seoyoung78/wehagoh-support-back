package com.duzon.lulu.service.MSC.MSC_050000.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;
import java.math.BigDecimal;

@Getter
@Setter
public class ResultRecord {
    @NotNull
    @Size(max = 20)
    private String pid;
    @NotNull
    private Long ends_rcrd_sqno;
    private Long ends_rslt_rcrd_sqno;
    @Size(max = 20)
    private String frst_rgst_usid;
    private String frst_rgst_dt;
    @Size(max = 20)
    private String last_updt_usid;
    private String last_updt_dt;
    @Size(max = 200)
    private String exmn_nm;
    @Size(max = 20)
    private String hstr_stat_cd;
    @Size(max = 50)
    private String dgsg_no;
    private String sign_yn;
    @Pattern(regexp = "^$|^\\d{4}-\\d{2}-\\d{2}$")
    private String exmn_date;
    @Pattern(regexp = "^$|^\\d{4}-\\d{2}-\\d{2}$")
    private String dtrm_date;
    @Size(max = 20)
    private String ends_dr_nm;
    @Size(max = 1)
    private String gscn_pt_yn;
    @Size(max = 200)
    private String stmc_bctr_exmn_rslt;
    @Size(max = 200)
    private String clo_cd;
    @Size(max = 200)
    private String trtm_mdtr_cd;
    @Size(max = 1000)
    private String trtm_mdtr_cnts;
    @Size(max = 200)
    private String exsn_prcd_actg_mthd;
    @Size(max = 1)
    private String cmpc_yn;
    @Size(max = 1000)
    private String cmpc_cnts;
    @Size(max = 200)
    private String cmpc_trtm_mthd;
    @Size(max = 200)
    private String cmpc_prgr;
    private String cncr_mdex_advc_matr;
    @Size(max = 200)
    private String advc_matr;
    private String advc_matr_cnts;
    @Size(max = 200)
    private String etnl_obsr_opnn;
    @Size(max = 200)
    private String dre_opnn;
    @Min(0)
    @Max(99999)
    private BigDecimal obsr_inrt_time_ms;
    @Min(0)
    @Max(99999)
    private BigDecimal obsr_exmn_end_time_ms;
    @Min(0)
    @Max(99999)
    private BigDecimal obsr_recl_time_ms;
    @Size(max = 200)
    private String bpre_dgre_LC;
    @Size(max = 200)
    private String bpre_dgre_TC;
    @Size(max = 200)
    private String bpre_dgre_RC;
    @Size(max = 200)
    private String rslt_opnn_1;
    @Size(max = 200)
    private String rslt_opnn_2;
    @Size(max = 200)
    private String rslt_opnn_3;
    @Size(max = 1)
    private String plyp_exsn_prcd_actg_yn;
    @Size(max = 1000)
    private String stmc_bctr_exmn_rslt_nm;
    @Size(max = 1000)
    private String clo_cd_nm;
    @Size(max = 1000)
    private String trtm_mdtr_cd_nm;
    @Size(max = 1000)
    private String exsn_prcd_actg_mthd_nm;
    @Size(max = 1000)
    private String cmpc_cnts_nm;
    @Size(max = 1000)
    private String cmpc_trtm_mthd_nm;
    @Size(max = 1000)
    private String cmpc_prgr_nm;
    @Size(max = 1000)
    private String advc_matr_nm;
    @Size(max = 1000)
    private String etnl_obsr_opnn_nm;
    @Size(max = 1000)
    private String dre_opnn_nm;
    @Size(max = 1000)
    private String bpre_dgre_lc_nm;
    @Size(max = 1000)
    private String bpre_dgre_tc_nm;
    @Size(max = 1000)
    private String bpre_dgre_rc_nm;
    @Size(max = 1000)
    private String rslt_opnn_nm_1;
    @Size(max = 1000)
    private String rslt_opnn_nm_2;
    @Size(max = 1000)
    private String rslt_opnn_nm_3;
}
