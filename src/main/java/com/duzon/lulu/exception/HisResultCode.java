package com.duzon.lulu.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HisResultCode {
    private int resultCode;
    private String resultMsg;

    public HisResultCode(int resultCode, String resultMsg) {
        this.resultCode = resultCode;
        this.resultMsg  = resultMsg;
    }
}
