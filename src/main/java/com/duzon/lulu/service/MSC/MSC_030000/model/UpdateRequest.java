package com.duzon.lulu.service.MSC.MSC_030000.model;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;

@Getter
@Setter
public class UpdateRequest {
    // 필수값
    private String pid;
    private String prsc_date;
    private String prsc_sqno;
    private String type;

    // 판독소견
    private String iptn_rslt;

    // 파일
    private List<String> deleteList;
    private List<HashMap<String, Object>> uploadList;
    private String file_path_id;

    // 이력 코드
    private String hstr_stat_cd;

    // 전자 서명
    private String dgsg_no;

    public HashMap<String, Object> convertKeysToMap() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("pid", this.getPid());
        map.put("prsc_date", this.getPrsc_date());
        map.put("prsc_sqno", this.getPrsc_sqno());
        map.put("type", this.getType());
        map.put("iptn_rslt", this.getIptn_rslt());
        map.put("dgsg_no", this.getDgsg_no());
        return map;
    }
}
