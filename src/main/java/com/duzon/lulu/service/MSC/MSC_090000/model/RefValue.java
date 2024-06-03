package com.duzon.lulu.service.MSC.MSC_090000.model;

import com.duzon.lulu.service.MSC.util.HealthSessionContext;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class RefValue extends HealthSessionContext {
    private String prsc_cd;        // 검사코드(처방코드)
    private String spcm_cd;        // 검체코드(검체종류)
    private String vol_cd;         // 구분(공통, 남자, 여자, 신생아)
    private String rfvl_lwlm_valu; // 참고치하한값
    private String rfvl_uplm_valu; // 참고치상한값
    private String use_yn;         // 사용여부
}
