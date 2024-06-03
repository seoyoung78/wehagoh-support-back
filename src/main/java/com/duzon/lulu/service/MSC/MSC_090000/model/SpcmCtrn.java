package com.duzon.lulu.service.MSC.MSC_090000.model;

import com.duzon.lulu.service.MSC.util.HealthSessionContext;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class SpcmCtrn extends HealthSessionContext {
    private String spcm_cd;             // 검체코드
    private String spcm_use_yn;         // 검체코드 사용여부
    private String spcm_labl_nm;        // 검체라벨명
    private String spcm_nm;             // 검체명
    private String spcm_ctnr_cd;        // 검체 용기
    private String spcm_expl;           // 검체설명
    private String spcm_state;
    private String ctnr_cd;             // 용기코드
    private String ctnr_use_yn;         // 용기코드 사용여부
    private String ctnr_labl_nm;        // 용기라벨명
    private String ctnr_nm;             // 용기명
    private String ctnr_colr;           // 용기 색상
    private String ctnr_state;
}
