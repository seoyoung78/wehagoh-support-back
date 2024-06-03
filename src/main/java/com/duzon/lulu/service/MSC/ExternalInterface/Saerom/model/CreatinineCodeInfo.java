package com.duzon.lulu.service.MSC.ExternalInterface.Saerom.model;

public class CreatinineCodeInfo {

    String pid;
    String sex_cd;
    String age_cd;
    private String exmn_cd;
    private String spcm_no;
    private String exmn_rslt_valu;

    public CreatinineCodeInfo(String pid, String sex_cd, String age_cd, String exmn_cd, String spcm_no, String exmn_rslt_valu) {
        this.pid = pid;
        this.sex_cd = sex_cd;
        this.age_cd = age_cd;
        this.exmn_cd = exmn_cd;
        this.spcm_no = spcm_no;
        this.exmn_rslt_valu = exmn_rslt_valu;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getSex_cd() {
        return sex_cd;
    }

    public void setSex_cd(String sex_cd) {
        this.sex_cd = sex_cd;
    }

    public String getAge_cd() {
        return age_cd;
    }

    public void setAge_cd(String age_cd) {
        this.age_cd = age_cd;
    }

    public String getExmn_cd() {
        return exmn_cd;
    }

    public void setExmn_cd(String exmn_cd) {
        this.exmn_cd = exmn_cd;
    }

    public String getSpcm_no() {
        return spcm_no;
    }

    public void setSpcm_no(String spcm_no) {
        this.spcm_no = spcm_no;
    }

    public String getExmn_rslt_valu() {
        return exmn_rslt_valu;
    }

    public void setExmn_rslt_valu(String exmn_rslt_valu) {
        this.exmn_rslt_valu = exmn_rslt_valu;
    }
}
