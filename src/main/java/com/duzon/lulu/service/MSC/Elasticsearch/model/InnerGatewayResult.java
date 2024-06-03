package com.duzon.lulu.service.MSC.Elasticsearch.model;

import lombok.Data;

import java.util.HashMap;
import java.util.List;

@Data
public class InnerGatewayResult {
    private String resultCode;
    private List<HashMap<String, Object>> resultData;
    private String resultMsg;

}
