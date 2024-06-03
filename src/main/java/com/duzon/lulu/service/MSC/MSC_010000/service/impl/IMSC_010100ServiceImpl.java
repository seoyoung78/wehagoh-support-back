package com.duzon.lulu.service.MSC.MSC_010000.service.impl;

import com.duzon.lulu.service.MSC.MSC_010000.model.*;
import com.duzon.lulu.service.MSC.MSC_010000.mapper.MSC_010100Mapper;
import com.duzon.lulu.service.MSC.MSC_010000.service.IMSC_010100Service;
import com.duzon.lulu.service.MSC.util.HealthSessionContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class IMSC_010100ServiceImpl implements IMSC_010100Service {

    @Autowired
    private MSC_010100Mapper msc010100Mapper;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private HealthSessionContext healthSessionContext;

    public List<MSC_010100ResModel> selectPatient(MSC_010100ReqParamModel model) {
        Map<String, Object> param = objectMapper.convertValue(model, Map.class);
        healthSessionContext.setContext();
        healthSessionContext.setSessionForHashMap(param);
        param.put("param", model);
        List<MSC_010100ResModel> queryResult = msc010100Mapper.selectPatient(param);
       return   queryResult.stream().filter(list ->
               (!list.getExrm_clsf_cd().equals("E") || !list.getMdtr_site_cd().equals("")))
               .collect(Collectors.toList());
    }
}
