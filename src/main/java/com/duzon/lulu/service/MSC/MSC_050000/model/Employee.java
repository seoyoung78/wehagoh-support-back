package com.duzon.lulu.service.MSC.MSC_050000.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Employee {
    private Integer empl_sqno; // 직원일련번호
    private String octy_cd; // 직종구분코드
    private String usid;  // 사용자ID
    private String user_nm; // 이름
    private String sign_lctn; // 서명위치
}
