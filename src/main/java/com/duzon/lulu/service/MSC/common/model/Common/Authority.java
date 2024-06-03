package com.duzon.lulu.service.MSC.common.model.Common;

import lombok.Data;

@Data
public class Authority {
    private Integer mdfr_clsf_sqno;         // 서식 분류 일련번호
    private String read;            // 조회 권한 여부
    private String update;       // 등록 수정 권한 여부
    private String print;            // 출력 관한 여부
    private String issue;            // 발급 권한 여부
}
