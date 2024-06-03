package com.duzon.lulu.service.MSC.ExternalInterface.Saerom.model;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ExResultUpdateData {
    private String exmn_rslt_valu;
    private String spcm_no;
    private int rslt_sqno;
    private String DB_CLINIC;

}
