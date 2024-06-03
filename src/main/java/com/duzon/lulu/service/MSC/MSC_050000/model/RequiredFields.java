package com.duzon.lulu.service.MSC.MSC_050000.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.HashMap;

@Getter
@Setter
public class RequiredFields implements ConvertibleToMap {
    @NotNull
    @Size(max = 20)
    private String pid;
    @NotNull
    private String prsc_date;
    @NotNull
    private String prsc_sqno;
    @NotNull
    private String type;
    private Long ends_rcrd_sqno;
    @Size(max = 200)
    private String exmn_nm;
    @Pattern(regexp = "^$|^\\d{4}-\\d{2}-\\d{2}$")
    private String exmn_date;

    @Override
    public HashMap<String, Object> convertKeysToMap() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("pid", this.getPid());
        map.put("prsc_date", this.getPrsc_date());
        map.put("prsc_sqno", this.getPrsc_sqno());
        map.put("type", this.getType());
        map.put("ends_rcrd_sqno", this.getEnds_rcrd_sqno());
        return map;
    }

    @Override
    public String toString() {
        return "RequiredFields{" +
                "pid='" + pid + '\'' +
                ", prsc_date='" + prsc_date + '\'' +
                ", prsc_sqno='" + prsc_sqno + '\'' +
                ", type='" + type + '\'' +
                ", ends_rcrd_sqno=" + ends_rcrd_sqno +
                '}';
    }
}
