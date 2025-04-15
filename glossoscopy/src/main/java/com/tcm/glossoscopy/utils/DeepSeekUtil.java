package com.tcm.glossoscopy.utils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import javax.servlet.ServletOutputStream;
import java.io.IOException;

@AllArgsConstructor
@Data
public class DeepSeekUtil {
    private String url;
    private String key;

    /**
     * 调用DeepSeek API
     *
     * @param userQuestion 用户问题
     * @return API响应结果
     */
    public JsonObject callDeepSeekApi(String userQuestion) {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            //构造请求体
            JsonObject requestBody = new JsonObject();
            requestBody.addProperty("model", "deepseek-chat"); // 模型名称，根据API文档填写
            //构造messages数组
            JsonArray messages = new JsonArray();
            JsonObject message = new JsonObject();
            message.addProperty("role", "user");
            message.addProperty("content", userQuestion);
            messages.add(message);
            //将messages添加到requestBody
            requestBody.add("messages", messages);
            requestBody.addProperty("max_tokens", 500); // 最大返回token数
            requestBody.addProperty("temperature", 0.7); // 控制生成文本的随机性
            //构造POST请求
            HttpPost httpPost = new HttpPost(url);
            httpPost.setHeader("Content-Type", "application/json");
            httpPost.setHeader("Authorization", "Bearer " + key); // 添加API Key
            Gson gson = new Gson();
            httpPost.setEntity(new StringEntity(gson.toJson(requestBody), ContentType.APPLICATION_JSON));
            //发送请求并获取响应
            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    String responseBody = EntityUtils.toString(entity);
                    return gson.fromJson(responseBody, JsonObject.class);
                } else {
                    throw new IOException("响应体为空");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            JsonObject errorResponse = new JsonObject();
            errorResponse.addProperty("error", e.getMessage());
            return errorResponse;
        }
    }
}
