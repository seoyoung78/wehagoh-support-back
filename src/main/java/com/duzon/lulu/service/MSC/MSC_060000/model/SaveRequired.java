package com.duzon.lulu.service.MSC.MSC_060000.model;

import com.duzon.lulu.service.MSC.MSC_050000.model.ConvertibleToMap;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.HashMap;

@Getter
@Setter
public class SaveRequired implements ConvertibleToMap {
    @NotNull
    private String pid;
    @NotNull
    private String prsc_date;
    @NotNull
    private String prsc_sqno;
    private String portal_id;
    private String mdtr_opnn;
    private String mdtr_memo;
    private String dgsg_no;
    private String hstr_stat_cd;

    @Override
    public HashMap<String, Object> convertKeysToMap() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("pid", this.getPid());
        map.put("prsc_date", this.getPrsc_date());
        map.put("prsc_sqno", this.getPrsc_sqno());
        map.put("portal_id", this.getPortal_id());
        map.put("hstr_stat_cd", this.getHstr_stat_cd());
        return map;
    }
}
