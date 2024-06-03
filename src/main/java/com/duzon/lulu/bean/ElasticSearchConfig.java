package com.duzon.lulu.bean;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.beans.factory.annotation.Value;

@Configuration
@EnableElasticsearchRepositories(basePackages = {"com.duzon.lulu.service.MSC.Elasticsearch.service"})
public class ElasticSearchConfig extends AbstractElasticsearchConfiguration {
    private final String hostAndPort;

    public ElasticSearchConfig(@Value("${spring.elasticsearch.rest.uris}") String uris) {
        this.hostAndPort = uris.replace("http://", "");
    }

    @Override
    public RestHighLevelClient elasticsearchClient() {
        ClientConfiguration clientConfiguration = ClientConfiguration.builder()
                .connectedTo(hostAndPort)
                .build();

        return RestClients.create(clientConfiguration).rest();
    }
}
