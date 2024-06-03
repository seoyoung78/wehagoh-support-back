package com.duzon.lulu.service.MSC.common.service.impl;

import com.duzon.common.model.LuluResult;
import com.duzon.common.util.StringUtil;
import com.duzon.lulu.service.MSC.MSC_030000.model.ObjectStorageResult;
import com.duzon.lulu.service.MSC.common.model.FileInfo;
import com.duzon.lulu.service.MSC.common.service.IFileService;
import com.duzon.lulu.service.MSC.util.HealthSessionContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Properties;
import java.util.UUID;

@Service
public class FileServiceImpl implements IFileService {

    @Autowired
    private Properties luluProperties;

    @Autowired
    private HealthSessionContext healthSessionContext;

    @Transactional
    @Override
    public LuluResult fileUploadService(FileInfo fileInfo) {
        LuluResult result = new LuluResult();

        healthSessionContext.setContext();
        healthSessionContext.setSessionForModel(fileInfo);

        String url = luluProperties.getProperty("globals.INNER_GATEWAY_URL")+"wehagoh/cliniccommon/storage/upload?cno="+fileInfo.getCompany_no();
        String serviceCode = luluProperties.getProperty("globals.SERVICE_CODE");
        String bucketType = "COMPANY";
        String authorization = fileInfo.getAuthorization();
        String transactionId = fileInfo.getTransactionId();
        String contentType = fileInfo.getFile().getContentType();

        // Header 세팅
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", StringUtil.fixNull(authorization));
        headers.set("transaction-id", StringUtil.fixNull(transactionId, UUID.randomUUID().toString().replace("-", "")));

        // Params 세팅
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", fileInfo.getFile().getResource());
        body.add("serviceCode", serviceCode);
        body.add("contentType", contentType);
        body.add("bucketType", bucketType);

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            ObjectMapper objectMapper = new ObjectMapper();

            try {
                // JSON 문자열을 자바 객체로 변환
                ObjectStorageResult uploadResult = objectMapper.readValue(response.getBody(), ObjectStorageResult.class);
                String uuid = uploadResult.getResultData();

                result.setResultCode(200);
                result.setResultData(uuid);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            result.setResultCode(400);
            result.setResultData(response.getBody());
        }

        return result;
    }

}
