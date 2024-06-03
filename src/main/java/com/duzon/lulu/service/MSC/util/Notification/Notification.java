package com.duzon.lulu.service.MSC.util.Notification;

import com.duzon.lulu.service.MSC.util.HealthSessionContext;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class Notification extends HealthSessionContext {
    // 메신저 알림
    private String authorization;
    private String transactionId;
    private String content_detail_type;
    private String req_title;
    private String req_msg;
    private String user_name;
    private String company_name;
    private String req_user_name;
    private String req_code;
    private String req_code_name;
    private String req_memo;
    private String req_url;
    private String req_result;
    private String req_result_date;
    private String req_normal_range;
    private Integer exrm_dept_sqno;

    // 웹 알림
    private String msgCd;
    private String exmnDvsn;
    private String emrFormNm;
}
