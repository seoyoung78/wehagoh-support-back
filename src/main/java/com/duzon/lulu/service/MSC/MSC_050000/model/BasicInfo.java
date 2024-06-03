package com.duzon.lulu.service.MSC.MSC_050000.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class BasicInfo {
    @NotNull
    @Size(max = 20)
    private String pid;
    @NotNull
    private Long ends_rcrd_sqno;
    private Long ends_fndt_info_sqno;
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
    @Size(max = 200)
    private String pt_clsf;
    @Size(max = 20)
    private String fndt_info_rcrd_usid;
    private String fndt_info_rcrd_sign_lctn;
    @Size(max = 200)
    private String teth_stat;
    @Size(max = 200)
    private String bpre_cd;
    @Size(max = 1)
    private String ctdn_acpn_yn;
    @Size(max = 200)
    private String etbd_stat;
    @Size(max = 200)
    private String npo_en;
    @Size(max = 200)
    private String main_hods;
    @Size(max = 1000)
    private String main_hods_cnts;
    @Size(max = 200)
    private String alrg_en;
    @Size(max = 200)
    private String idct_1;
    @Size(max = 200)
    private String idct_2;
    @Size(max = 200)
    private String mdcn_tkng;
    @Size(max = 200)
    private String mdcn_tkng_optn_1;
    @Size(max = 1000)
    private String mdcn_tkng_cnts;
    @Size(max = 200)
    private String atsm_cd;
    @Size(max = 200)
    private String past_sdef;
    @Size(max = 1)
    private String loca_orcv_anst_use_yn;
    @Size(max = 1)
    private String atcl_tkng_stop_yn;
    @Digits(integer=5, fraction=0)
    private String atcl_tkng_stop_nody;
    @Size(max = 200)
    private String ends_clsf_cd;
    @Size(max = 1)
    private String slpn_ends_yn;
    @Size(max = 200)
    private String slpn_drvt_mdcn_cd;
    @Digits(integer=5, fraction=0)
    private String slpn_drvt_mdcn_dosg;
    @Size(max = 1000)
    private String slpn_drvt_mdcn_cnts;
    @Digits(integer=5, fraction=0)
    private String exmn_o2sa_mnvl;
    @Digits(integer=5, fraction=0)
    private String exmn_o2sa_mxvl;
    @Digits(integer=5, fraction=0)
    private String rcvr_o2sa_mnvl;
    @Digits(integer=5, fraction=0)
    private String rcvr_o2sa_mxvl;
    @Digits(integer=5, fraction=0)
    private String oxyg_supl;
    @Digits(integer=5, fraction=0)
    private String exmn_bf_sytc_bp;
    @Digits(integer=5, fraction=0)
    private String exmn_bf_datc_bp;
    @Digits(integer=5, fraction=0)
    private String exmn_bf_plst;
    @Digits(integer=5, fraction=0)
    private String exmn_bf_rspr_cnt;
    @Digits(integer=5, fraction=0)
    private String exmn_af_sytc_bp;
    @Digits(integer=5, fraction=0)
    private String exmn_af_datc_bp;
    @Digits(integer=5, fraction=0)
    private String exmn_af_plst;
    @Digits(integer=5, fraction=0)
    private String exmn_af_rspr_cnt;
    @Size(max = 200)
    private String sedt_evlt_cd;
    @Size(max = 1000)
    private String sedt_evlt_cnts;
    @Size(max = 200)
    private String sedt_rctn_cd;
    @Size(max = 200)
    private String pt_symp_cd;
    @Size(max = 1000)
    private String pt_symp_cnts;
    @Size(max = 200)
    private String slpn_evlt_cd;
    @Size(max = 1000)
    private String slpn_evlt_cnts;
    @Size(max = 200)
    private String chot_bass_cd;
    @Size(max = 1000)
    private String ref_matr;
    @Size(max = 1000)
    private String pt_clsf_nm;
    @Size(max = 1000)
    private String teth_stat_nm;
    @Size(max = 1000)
    private String bpre_cd_nm;
    @Size(max = 1000)
    private String etbd_stat_nm;
    @Size(max = 1000)
    private String npo_en_nm;
    @Size(max = 1000)
    private String main_hods_nm;
    @Size(max = 1000)
    private String alrg_en_nm;
    @Size(max = 1000)
    private String idct_nm_1;
    @Size(max = 1000)
    private String idct_nm_2;
    @Size(max = 1000)
    private String mdcn_tkng_nm;
    @Size(max = 1000)
    private String mdcn_tkng_optn_nm_1;
    @Size(max = 1000)
    private String atsm_cd_nm;
    @Size(max = 1000)
    private String past_sdef_nm;
    @Size(max = 1000)
    private String ends_clsf_cd_nm;
    @Size(max = 1000)
    private String slpn_drvt_mdcn_cd_nm;
    @Size(max = 1000)
    private String sedt_evlt_cd_nm;
    @Size(max = 1000)
    private String sedt_rctn_cd_nm;
    @Size(max = 1000)
    private String pt_symp_cd_nm;
    @Size(max = 1000)
    private String slpn_evlt_cd_nm;
    @Size(max = 1000)
    private String chot_bass_cd_nm;

}
