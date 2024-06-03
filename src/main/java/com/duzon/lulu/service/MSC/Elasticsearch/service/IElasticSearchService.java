package com.duzon.lulu.service.MSC.Elasticsearch.service;

import com.duzon.common.service.LuluService;

import java.util.HashMap;

public interface IElasticSearchService extends LuluService {
    Object searchPrsc(HashMap<String, Object> param) throws Exception;
}
