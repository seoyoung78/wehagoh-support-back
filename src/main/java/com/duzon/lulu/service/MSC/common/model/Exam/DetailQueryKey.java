package com.duzon.lulu.service.MSC.common.model.Exam;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class DetailQueryKey {
    @NotNull
    private String pid;
    @NotNull
    private String prsc_date;
    @NotNull
    private String prsc_sqno;

    // 기본 생성자
    public DetailQueryKey() {
    }

    // String, String, String 파라미터를 받는 생성자 추가
    public DetailQueryKey(String pid, String prsc_date, String prsc_sqno) {
        this.pid = pid;
        this.prsc_date = prsc_date;
        this.prsc_sqno = prsc_sqno;
    }
}
