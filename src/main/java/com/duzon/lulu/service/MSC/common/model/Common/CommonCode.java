package com.duzon.lulu.service.MSC.common.model.Common;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommonCode {
    private String cmcd_clsf_cd; // 공통코드 분류 코드
    private String cmcd_cd; // 공통코드 코드
    private String cmcd_nm; // 공통코드 명
    private String cmcd_expl; // 공통코드 설명
    private Double cmcd_figr_valu1; // 공통코드숫자값1
    private Double cmcd_figr_valu2; // 공통코드숫자값1
    private Double cmcd_figr_valu3; // 공통코드숫자값1
    private String cmcd_char_valu1; // 공통코드문자값1
    private String cmcd_char_valu2; // 공통코드문자값2
    private String cmcd_char_valu3; // 공통코드문자값3
}
