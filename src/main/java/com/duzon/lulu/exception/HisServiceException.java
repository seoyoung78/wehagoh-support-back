package com.duzon.lulu.exception;

import lombok.Getter;

/**
 * 개발자 : 이성민
 * BusanServiceException : HelthCare exception Form
 */
@Getter
public class HisServiceException extends RuntimeException {
    public static HisResultCode Fail = new HisResultCode(-99, "FAIL");
    private final int resultCode;
    private final Exception exception = null;

    public HisServiceException() {
        super(Fail.getResultMsg());
        this.resultCode = Fail.getResultCode();
    }
    public HisServiceException(String s, int resultCode, Exception e) {
        super(s);
        this.resultCode = resultCode;
    }
    public HisServiceException(String s, int resultCode) {
        super(s);
        this.resultCode = resultCode;
    }

    public HisServiceException(String s) {
        super(s);
        this.resultCode = Fail.getResultCode();
    }

//    public static BusanResultCode Success = new BusanResultCode(0, "SUCCESS");
//    public static BusanResultCode Fail = new BusanResultCode(-1, "FAIL");
//
//    private static final long serialVersionUID = -6532085207127826390L;
//    private int resultCode = Fail.getResultCode();
//    private String resultMsg = null;//Fail.getResultMsg();
//    private Exception exception = null;
//
//    public BusanServiceException() {
//        super();
//        this.setResultMsg(Fail.getResultMsg());
//    }
//
//    public BusanServiceException(String resultMsg) {
//        super();
//        this.setResultMsg(resultMsg);
//    }
//
//    public BusanServiceException(int resultCode, String resultMsg, Exception e) {
//        super();
//        this.setResultCode(resultCode);
//        this.setResultMsg(resultMsg);
//        this.setException(e);
//    }
//
//    public int getResultCode() {
//        return resultCode;
//    }
//
//    public void setResultCode(int resultCode) {
//        this.resultCode = resultCode;
//    }
//
//    public String getResultMsg() {
//        return resultMsg;
//    }
//
//    public void setResultMsg(String resultMsg) {
//        this.resultMsg = resultMsg;
//    }
//
//    public Exception getException() {
//        return exception;
//    }
//
//    public void setException(Exception exception) {
//        this.exception = exception;
//    }
//
//    @Override
//    public String toString() {
//        return String.format("[%d] %s, exception : %s", this.getResultCode(), this.getResultMsg(),
//                this.getException() != null ? this.getException().toString() : "null");
//    }
}

