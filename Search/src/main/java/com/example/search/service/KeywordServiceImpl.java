package com.example.search.service;

import com.example.search.document.Keyword;
import com.example.search.param.ElasticSearchParam;
import com.example.search.service.interfaces.KeywordService;
import com.google.gson.Gson;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * TODO
 *
 * @author LinRuiXin
 * @date 2020/11/27 7:57 下午
 */
@Service
public class KeywordServiceImpl implements KeywordService {

    private static final String indexKeyword = ElasticSearchParam.INDEX_KEYWORD;
    private static final RequestOptions options = ElasticSearchParam.REQUEST_OPTIONS;

    private final RestHighLevelClient client;
    private final Gson gson;

    @Autowired
    public KeywordServiceImpl(RestHighLevelClient client, Gson gson) {
        this.client = client;
        this.gson = gson;
    }

    @Override
    public void addKeyword(String keyword) {
        try {
            Keyword source = new Keyword(keyword);
            IndexRequest indexRequest = new IndexRequest(indexKeyword);
            indexRequest.timeout("1s").source(gson.toJson(source), XContentType.JSON);
            IndexResponse index = client.index(indexRequest, options);
            System.out.println(index);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addKeywordBatch(List<String> keywords) {
        try {
            if (keywords.size() > 0) {
                Keyword source = new Keyword();
                BulkRequest bulkRequest = new BulkRequest(indexKeyword);
                keywords.forEach(keywordsItem -> {
                    source.setTitle(keywordsItem);
                    bulkRequest.add(new IndexRequest().timeout("1s").source(gson.toJson(source), XContentType.JSON));
                });
                BulkResponse bulk = client.bulk(bulkRequest, options);
                System.out.println(bulk);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Keyword> getAllKeyword(int size) {
        try {
            SearchRequest searchRequest = new SearchRequest(indexKeyword);
            searchRequest.source(new SearchSourceBuilder().query(QueryBuilders.matchAllQuery()).from(0).size(size));
            SearchResponse search = client.search(searchRequest, options);
            return convertRespToStringCollection(search);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Keyword> matchKeyword(String keyword, int size) {
        try {
            SearchRequest searchRequest = new SearchRequest(indexKeyword);
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder().query(QueryBuilders.matchQuery("title", keyword)).from(0).size(size);
            searchRequest.source(searchSourceBuilder);
            SearchResponse search = client.search(searchRequest, options);
            return convertRespToStringCollection(search);
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    private List<Keyword> convertRespToStringCollection(SearchResponse search) {
        SearchHits hits = search.getHits();
        int size = (int) hits.getTotalHits().value;
        List<Keyword> res = new ArrayList<>(size);
        hits.forEach(hit -> {
            res.add(gson.fromJson(hit.getSourceAsString(),Keyword.class));
        });
        return res;
    }

}
