package com.duzon.lulu.service.MSC.Elasticsearch.service.impl;

import com.duzon.clinichelper.infrastructure.datasource.model.DatabaseManager;
import com.duzon.clinichelper.redis.application.service.RedisService;
import com.duzon.common.model.LuluResult;
import com.duzon.lulu.common.service.InnerGatewayService;
import com.duzon.lulu.service.MSC.Elasticsearch.model.InnerGatewayResult;
import com.duzon.lulu.service.MSC.Elasticsearch.service.IElasticSearchService;
import com.duzon.lulu.service.MSC.util.HealthSessionContext;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("ElasticSearchService")
public class ElasticSearchServiceImpl implements IElasticSearchService {
    @Autowired
    private HealthSessionContext healthSessionContext;

    @Autowired
    private RedisService redisService;

    @Autowired
    private InnerGatewayService innerGatewayService;

    @Override
    public Object searchPrsc(HashMap<String, Object> param) throws Exception {
        Map<String, Object> sessionData = new HashMap<>();
        healthSessionContext.setContext();
        healthSessionContext.setSessionForHashMap(sessionData);

        // 회사 Database 접근정보 조회
        DatabaseManager databaseManager = redisService.readDatabaseManager((Long) sessionData.get("company_no"));

        String apiUrl = "wehagoh/cliniccommon/elasticsearch/medprscdv/list";
        String[] prscClsfCd = { "C1", "C2", "C3", "F1" };
        param.put("sugaApdy", param.get("date"));
        param.put("sugaEndy", param.get("date"));
        param.put("prscClsfCd", prscClsfCd);
        param.put("size", 50);
        param.put("schemaName", databaseManager.getDatabaseConnector().getSchema()); //companyCode
        param.put("companyCode", databaseManager.getDatabaseConnector().getSchema());

        HashMap<String, Object> header = new HashMap<>();
        header.put("content-type", "application/json"); // 컨텐츠 타입 대소문자 상관없음

        LuluResult luluResult = innerGatewayService.sendPost(apiUrl, param, header);
        // Gson 객체 생성
        Gson gson = new Gson();

        InnerGatewayResult resultObject = gson.fromJson((String) luluResult.getResultData(), InnerGatewayResult.class);
        if (resultObject != null && resultObject.getResultCode() != null && resultObject.getResultCode().equals("OK")) {
            // camel To snake array
            ArrayList<HashMap<String, Object>> resultCamelToSnake = new ArrayList<>();

            for(HashMap<String, Object> objCamelCase : resultObject.getResultData()){
                // camel to snake map
                HashMap<String, Object> objSnackCase = new HashMap<>();
                for (Map.Entry<String, Object> entry : objCamelCase.entrySet()) {
                    String camelKey = camelToSnake(entry.getKey());
                    objSnackCase.put(camelKey, entry.getValue());
                }
                resultCamelToSnake.add(objSnackCase);
            }

            return resultCamelToSnake;
        } else {
            return new Object[0];
        }
    }

    private String snakeToCamel(String snakeCase) {
        StringBuilder camelCase = new StringBuilder();
        boolean nextUpperCase = false;

        for (char c : snakeCase.toCharArray()) {
            if (c == '_') {
                nextUpperCase = true;
            } else {
                if (nextUpperCase) {
                    camelCase.append(Character.toUpperCase(c));
                    nextUpperCase = false;
                } else {
                    camelCase.append(Character.toLowerCase(c));
                }
            }
        }
        return camelCase.toString();
    }

    private String camelToSnake(String camelCase) {
        StringBuilder snakeCase = new StringBuilder();
        snakeCase.append(Character.toLowerCase(camelCase.charAt(0)));

        for (int i = 1; i < camelCase.length(); i++) {
            char c = camelCase.charAt(i);
            if (Character.isUpperCase(c)) {
                snakeCase.append('_');
                snakeCase.append(Character.toLowerCase(c));
            } else {
                snakeCase.append(c);
            }
        }
        return snakeCase.toString();
    }
}
