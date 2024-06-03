package com.duzon.lulu.service.MSC.MSC_050000.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.HashMap;

@Getter
@Setter
public class UpdateRequest implements ConvertibleToMap {
    // 필수값
    @NotNull
    private String pid;
    @NotNull
    private String prsc_date;
    @NotNull
    private String prsc_sqno;
    @NotNull
    private String type;
    @NotNull
    private long basic_ends_rcrd_sqno;
    @NotNull
    private long record_ends_rcrd_sqno;
    private String basic_frst_rgst_dt;
    private String record_frst_rgst_dt;
    private String basic_frst_rgst_usid;
    private String record_frst_rgst_usid;
    private String dgsg_no;
    private String portal_id;

    @Override
    public HashMap<String, Object> convertKeysToMap() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("pid", this.getPid());
        map.put("prsc_date", this.getPrsc_date());
        map.put("prsc_sqno", this.getPrsc_sqno());
        map.put("type", this.getType());
        return map;
    }
}
