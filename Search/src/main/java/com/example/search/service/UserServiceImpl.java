package com.example.search.service;

import com.example.search.document.UserDoc;
import com.example.search.param.ElasticSearchParam;
import com.example.search.service.interfaces.UserService;
import com.google.gson.Gson;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * TODO
 *
 * @author LinRuiXin
 * @date 2020/11/30 7:51 下午
 */
@Service
public class UserServiceImpl implements UserService {

    private static final String indexUser = ElasticSearchParam.INDEX_USER;
    private static final RequestOptions options = ElasticSearchParam.REQUEST_OPTIONS;
    private final RestHighLevelClient client;
    private final Gson gson;

    @Autowired
    public UserServiceImpl(RestHighLevelClient client, Gson gson) {
        this.client = client;
        this.gson = gson;
    }

    @Override
    public void addUser(UserDoc userDoc) {
        try {
            IndexRequest indexRequest = new IndexRequest(indexUser);
            indexRequest.source(gson.toJson(userDoc), XContentType.JSON);
            IndexResponse response = client.index(indexRequest, options);
            System.out.println(response);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void addUserBatch(List<UserDoc> userDocs) {
        try {
            BulkRequest bulkRequest = new BulkRequest(indexUser);
            userDocs.forEach(userDoc -> {
                bulkRequest.add(new IndexRequest().source(gson.toJson(userDoc),XContentType.JSON));
            });
            BulkResponse bulk = client.bulk(bulkRequest, options);
            System.out.println(bulk);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public List<UserDoc> getUser(int from, int size) {
        try {
            SearchRequest searchRequest = new SearchRequest(indexUser);
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder().query(QueryBuilders.matchAllQuery()).from(from).size(size);
            searchRequest.source(searchSourceBuilder);
            SearchResponse search = client.search(searchRequest, options);
            return convertResponseToUserCollection(search);
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    /*
    * 模糊查询用户
    * @keyword 关键词
    * @from 分页-起
    * @size 分页-长度
    * */
    @Override
    public List<UserDoc> user(String keyword, int from, int size) {
        try {
            SearchRequest searchRequest = new SearchRequest(indexUser);
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder().query(QueryBuilders.matchQuery("userName", keyword)).from(from).size(size);
            searchRequest.source(searchSourceBuilder);
            SearchResponse search = client.search(searchRequest, options);
            return convertResponseToUserCollection(search);
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    private List<UserDoc> convertResponseToUserCollection(SearchResponse search) {
        SearchHits hits = search.getHits();
        int size = (int) hits.getTotalHits().value;
        List<UserDoc> res = new ArrayList<>(size);
        hits.forEach(hit -> {
            res.add(gson.fromJson(hit.getSourceAsString(),UserDoc.class));
        });
        return res;
    }
}
