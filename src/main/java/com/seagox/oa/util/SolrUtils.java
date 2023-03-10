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
            // ??????????????????
//            StringBuffer str = new StringBuffer();
//            str.append("title:" + keywords);
//            str.append(" OR content:" + keywords);
//            query.setQuery(str.toString());
            query.setQuery(field + ":" + keywords);

            // ??????????????????
            query.setStart(start);  //??????
            query.setRows(rows);  //??????

            //desc??????    asc??????
            //query.setSort(sort, SolrQuery.ORDER.desc);
            query.setHighlight(true);// ????????????
            query.addHighlightField(field);//??????????????????
            query.setHighlightSimplePre("<span style='color:red;'>");// ????????????
            query.setHighlightSimplePost("</span>");// ????????????

            // ????????????
            QueryResponse queryResponse = solrClient.query(query);

            // ??????doc??????
            SolrDocumentList documents = queryResponse.getResults();

            // ????????????????????????
            Map<String, Map<String, List<String>>> highlighting = queryResponse.getHighlighting();
            // 6.???????????????,????????????????????????
            JSONArray jsonArray = new JSONArray();
            for (SolrDocument doc : documents) {
                JSONObject jsonObject = new JSONObject();
                // ??????????????????
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
