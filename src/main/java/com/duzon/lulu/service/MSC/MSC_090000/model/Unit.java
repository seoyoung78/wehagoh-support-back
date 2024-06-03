package com.duzon.lulu.service.MSC.MSC_090000.model;

import com.duzon.lulu.service.MSC.util.HealthSessionContext;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class Unit extends HealthSessionContext {
    private String prsc_cd, strt_date, exmn_rslt_uncd, end_date, unit_rmrk, origin_strt_date, del_yn;
    private Integer unit_sqno;
}
