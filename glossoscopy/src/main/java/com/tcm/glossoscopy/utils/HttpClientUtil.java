package com.tcm.glossoscopy.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.util.StringUtil;
import com.tcm.glossoscopy.constant.Constant;
import com.tcm.glossoscopy.entity.po.Record;
import com.tcm.glossoscopy.enums.*;
import com.tcm.glossoscopy.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Http工具类
 */
@Component
public class HttpClientUtil {

    static final int TIMEOUT = 20 * 1000;

    @Resource
    private AliOssUtil aliOssUtil;
    @Value(value = "${glossoscopy.python.url}")
    private String pythonUrl;

    /**
     * 发送GET方式请求
     * @param url
     * @param paramMap
     * @return
     */
    public String doGet(String url,Map<String,String> paramMap){
        // 创建Httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();

        CloseableHttpResponse response = null;

        String result=null;

        try{
            URIBuilder builder = new URIBuilder(url);
            if(paramMap != null){
                for (String key : paramMap.keySet()) {
                    builder.addParameter(key,paramMap.get(key));
                }
            }
            URI uri = builder.build();

            //创建GET请求
            HttpGet httpGet = new HttpGet(uri);

            //发送请求
            response = httpClient.execute(httpGet);

            if(response.getStatusLine().getStatusCode()==200){
                result = EntityUtils.toString(response.getEntity());
            }

        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }finally {
            try {
                response.close();
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
        return result;
    }

    /**
     * 发送POST方式请求
     * @param url
     * @param paramMap
     * @return
     * @throws IOException
     */
    public String doPost(String url, Map<String, String> paramMap) throws IOException {
        // 创建Httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String result=null;
        try {
            // 创建Http Post请求
            HttpPost httpPost = new HttpPost(url);
            // 创建参数列表
            if (paramMap != null) {
                List<NameValuePair> paramList = new ArrayList();
                for (Map.Entry<String, String> param : paramMap.entrySet()) {
                    paramList.add(new BasicNameValuePair(param.getKey(), param.getValue()));
                }
                // 模拟表单
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList);
                httpPost.setEntity(entity);
            }

            httpPost.setConfig(builderRequestConfig());

            // 执行http请求
            response = httpClient.execute(httpPost);
            if(response.getStatusLine().getStatusCode()==200){
                result = EntityUtils.toString(response.getEntity());
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            try {
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
        return result;
    }

    /**
     * 发送POST方式请求
     * @param url
     * @param paramMap
     * @return
     * @throws IOException
     */
    public String doPost4Json(String url, Map<String, String> paramMap) throws IOException {
        // 创建Httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String result=null;
        try {
            // 创建Http Post请求
            HttpPost httpPost = new HttpPost(url);

            if (paramMap != null) {
                //构造json格式数据
                JSONObject jsonObject = new JSONObject();
                for (Map.Entry<String, String> param : paramMap.entrySet()) {
                    jsonObject.put(param.getKey(),param.getValue());
                }
                StringEntity entity = new StringEntity(jsonObject.toString(),"utf-8");
                //设置请求编码
                entity.setContentEncoding("utf-8");
                //设置数据类型
                entity.setContentType("application/json");
                httpPost.setEntity(entity);
            }

            httpPost.setConfig(builderRequestConfig());

            // 执行http请求
            response = httpClient.execute(httpPost);
            if(response.getStatusLine().getStatusCode()==200){
                result = EntityUtils.toString(response.getEntity());
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            try {
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
        return result;
    }

    private static RequestConfig builderRequestConfig() {
        return RequestConfig.custom()
                .setConnectTimeout(TIMEOUT)
                .setConnectionRequestTimeout(TIMEOUT)
                .setSocketTimeout(TIMEOUT).build();
    }

    public Record getRecordBySendHttpRequest(String url) {
        //获取图片名称
        String objectName = url.substring(url.lastIndexOf('/')+1);
        //将图片进行base64格式转码
        String base64Image = aliOssUtil.getImageAsBase64(objectName);
        //构建请求数据集
        Map<String,String> map = new HashMap<>();
        map.put(Constant.BASE64_IMAGE, base64Image);
        String result;
        try {
            //发送http请求至python端并接收响应结果
            result= doPost4Json(pythonUrl,map);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        if(StringUtil.isEmpty(result)){
            throw new BusinessException(ExceptionEnum.GET_DIAGNOSIS_DATA_FAILED);
        }
        return parseResultToRecord(result,url);
    }

    private Record parseResultToRecord(String result,String url) {
        //获取目标结果数据集
        JSONObject jsonObject = JSON.parseObject(result);
        Record record = Record.builder()
                .image(url)
                .tongueSize(EnumUtil.fromValue(TongueSizeEnum.class,jsonObject.getInteger("tongueSize")))
                .tongueThickness(EnumUtil.fromValue(TongueThicknessEnum.class,jsonObject.getInteger("tongueThickness")))
                .toothMark(EnumUtil.fromValue(ToothMarkEnum.class,jsonObject.getInteger("toothMark")))
                .coatingColor(EnumUtil.fromValue(CoatingColorEnum.class,jsonObject.getInteger("coatingColor")))
                .healthStatus(EnumUtil.fromValue(HealthStatusEnum.class,jsonObject.getInteger("healthStatus")))
                .createTime(LocalDateTime.now())
                .build();
        return record;
    }
}
