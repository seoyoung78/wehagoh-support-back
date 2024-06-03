package com.duzon.lulu.service.MSC.MSC_080000.service.impl;

import com.duzon.lulu.service.MSC.MSC_080000.mapper.MSC_080100Mapper;
import com.duzon.lulu.service.MSC.MSC_080000.model.MSC_080100ReqParamModel;
import com.duzon.lulu.service.MSC.MSC_080000.model.MSC_080100ResModel;
import com.duzon.lulu.service.MSC.MSC_080000.service.IMSC_080100Service;
import com.duzon.lulu.service.MSC.util.HealthSessionContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class IMSC_080100ServiceImpl implements IMSC_080100Service {

    @Autowired
    private MSC_080100Mapper mapper;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private HealthSessionContext healthSessionContext;

    @Override
    public List<MSC_080100ResModel> getExmPatient(MSC_080100ReqParamModel model) {
        if (model.getHope_exrm_cd() == null || model.getHope_exrm_cd().size() == 0) {
            return new ArrayList<>();
        }
        Map<String, Object> param = objectMapper.convertValue(model, HashMap.class);
        healthSessionContext.setContext();
        healthSessionContext.setSessionForHashMap(param);
        List<MSC_080100ResModel> queryResult = mapper.getExmPatient(param);
        return queryResult.stream().filter(list ->
                        (!list.getExrm_clsf_cd().equals("E") || !list.getMdtr_site_cd().equals("")))
                .collect(Collectors.toList());
    }
}
