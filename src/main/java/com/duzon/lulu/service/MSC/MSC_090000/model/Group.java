package com.duzon.lulu.service.MSC.MSC_090000.model;

import com.duzon.lulu.service.MSC.util.HealthSessionContext;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.*;

@Data
@EqualsAndHashCode(callSuper=false)
public class Group extends HealthSessionContext {
    private String prsc_cd;                     // 처방코드
    private Integer exmn_set_sqno;              // 일련번호
    private String exmn_cd;                     // 검사코드
    private String exmn_nm;                     // 검사명
    private Integer sort_seq;                   // 정렬(순번)
    private String use_yn;                      // 사용여부
}
