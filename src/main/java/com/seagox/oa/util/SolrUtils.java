package com.seagox.oa.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.seagox.oa.common.ResultCode;
import com.seagox.oa.common.ResultData;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class SolrUtils {

    public static String getContent(URL url) throws IOException, TikaException {
        Tika tika = new Tika();
        return tika.parseToString(url);
    }

    public static ResultData addOrUpdateSolr(String id, String title, String content) {
        SolrClient solrClient = SpringUtils.getBean(SolrClient.class);
        SolrInputDocument document = new SolrInputDocument();
        document.setField("id", id);
        document.setField("title", title);
        document.setField("content", content);
        try {
            solrClient.add(document);
            solrClient.commit();
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResultData.success(null);
    }

    public static ResultData deleteSolr(String id) {
        SolrClient solrClient = SpringUtils.getBean(SolrClient.class);
        try {
            solrClient.deleteById(id);
            solrClient.commit();
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResultData.success(null);
    }

    public static ResultData deleteSolr(List<String> ids) {
        SolrClient solrClient = SpringUtils.getBean(SolrClient.class);
        try {
            solrClient.deleteById(ids);
            solrClient.commit();
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResultData.success(null);
    }

    public static ResultData seachSolr(String field, String keywords, Integer start, Integer rows) {
        SolrClient solrClient = SpringUtils.getBean(SolrClient.class);
        try {
            Map<String, Object> result = new HashMap<>();
            SolrQuery query = new SolrQuery();
            // 设置查询条件
//            StringBuffer str = new StringBuffer();
//            str.append("title:" + keywords);
//            str.append(" OR content:" + keywords);
//            query.setQuery(str.toString());
            query.setQuery(field + ":" + keywords);

            // 设置分页信息
            query.setStart(start);  //开始
            query.setRows(rows);  //结束

            //desc降序    asc升序
            //query.setSort(sort, SolrQuery.ORDER.desc);
            query.setHighlight(true);// 打开高亮
            query.addHighlightField(field);//设置高亮字段
            query.setHighlightSimplePre("<span style='color:red;'>");// 设置前缀
            query.setHighlightSimplePost("</span>");// 设置后缀

            // 执行查询
            QueryResponse queryResponse = solrClient.query(query);

            // 获取doc文档
            SolrDocumentList documents = queryResponse.getResults();

            // 获取高亮显示信息
            Map<String, Map<String, List<String>>> highlighting = queryResponse.getHighlighting();
            // 6.如果有结果,并且有数据，就装
            JSONArray jsonArray = new JSONArray();
            for (SolrDocument doc : documents) {
                JSONObject jsonObject = new JSONObject();
                // 拿高亮的内容
                List<String> hightDocs = highlighting.get(doc.get("id")).get(field);
                jsonObject.put("title", doc.getFieldValue("title").toString());
                jsonObject.put("allContent", doc.getFieldValue("content").toString());
                jsonObject.put("id", doc.getFieldValue("id").toString());
                if (!CollectionUtils.isEmpty(hightDocs)) {
                    jsonObject.put(field, hightDocs.get(0));
                } else {
                    jsonObject.put(field, doc.get(field).toString());
                }
                jsonArray.add(jsonObject);
            }
            result.put("list", jsonArray);
            result.put("total", documents.getNumFound());

            return ResultData.success(result);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultData.error(ResultCode.INTERNAL_SERVER_ERROR);
        }

    }

}
