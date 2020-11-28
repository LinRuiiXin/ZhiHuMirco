package com.example.search.service;

import com.example.search.document.Information;
import com.example.search.param.ElasticSearchParam;
import com.example.search.service.interfaces.InformationService;
import com.example.search.service.interfaces.KeywordService;
import com.google.gson.Gson;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * TODO
 *
 * @author LinRuiXin
 * @date 2020/11/28 3:53 下午
 */
@Service
public class InformationServiceImpl implements InformationService {

    private static final String indexInformation = ElasticSearchParam.INDEX_INFORMATION;
    private static final RequestOptions options = ElasticSearchParam.REQUEST_OPTIONS;

    private final RestHighLevelClient client;
    private final Gson gson;
    private final KeywordService keywordService;

    public InformationServiceImpl(RestHighLevelClient client, Gson gson, KeywordService keywordService) {
        this.client = client;
        this.gson = gson;
        this.keywordService = keywordService;
    }

    @Override
    public void insertInformation(Information information) {
        try {
            IndexRequest indexRequest = new IndexRequest(indexInformation);
            indexRequest
                    .timeout("2s")
                    .source(gson.toJson(information), XContentType.JSON);
            IndexResponse index = client.index(indexRequest, options);
            keywordService.addKeyword(information.title());
            System.out.println(index);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void insertInformationBatch(List<Information> information) {
        try{
            if(information.size() > 0){
                BulkRequest bulkRequest = new BulkRequest(indexInformation);
                information.forEach(i -> {
                    bulkRequest.add(new IndexRequest().source(gson.toJson(i),XContentType.JSON));
                });
                BulkResponse bulk = client.bulk(bulkRequest, options);
                System.out.println(bulk);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public List<Information> getInformation(int limit, int size) {
        try {
            SearchRequest searchRequest = new SearchRequest(indexInformation);
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder().query(QueryBuilders.matchAllQuery()).from(limit).size(size);
            searchRequest.source(searchSourceBuilder);
            SearchResponse search = client.search(searchRequest, options);
            return getInformationFromResponse(search);
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    private List<Information> getInformationFromResponse(SearchResponse search) {
        SearchHits hits = search.getHits();
        int size = (int) hits.getTotalHits().value;
        List<Information> res = new ArrayList<>(size);
        hits.forEach(hit -> {
            res.add(gson.fromJson(hit.getSourceAsString(),Information.class));
        });
        return res;
    }
}
