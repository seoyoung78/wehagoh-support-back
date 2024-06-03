package com.duzon.lulu.service.MSC.Elasticsearch.controller;

import com.duzon.lulu.annotation.HealthRestController;
import com.duzon.lulu.service.MSC.Elasticsearch.service.IElasticSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;

@HealthRestController("/elastic")
public class ElasticSearchController {
    @Autowired
    private IElasticSearchService iElasticSearchService;

    @PostMapping("/searchPrsc")
    public  Object searchPrsc(@RequestBody HashMap<String, Object> param) throws Exception { return this.iElasticSearchService.searchPrsc(param); }
}
