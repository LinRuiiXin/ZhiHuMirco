package com.example.search.param;

import org.elasticsearch.client.RequestOptions;

/**
 * TODO
 * 存放 ElasticSearch 的一些参数，如索引名
 * @author LinRuiXin
 * @date 2020/11/26 7:28 下午
 */
public class ElasticSearchParam {
    public static final String INDEX_INFORMATION = "zhi_hu_information";
    public static final String INDEX_USER = "zhi_hu_user";
    public static final String INDEX_CLASSIFY = "zhi_hu_classify";
    public static final String INDEX_KEYWORD = "zhi_hu_keyword";

    public static final RequestOptions REQUEST_OPTIONS = RequestOptions.DEFAULT;
}
