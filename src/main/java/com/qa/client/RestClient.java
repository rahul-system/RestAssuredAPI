package com.qa.client;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RestClient {

    //1. GET Method without Headers
    public CloseableHttpResponse get(String url) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url); // http get request
        CloseableHttpResponse httpResponse = httpClient.execute(httpGet);

        return httpResponse;

    }

    //2. GET Method with Headers
    public CloseableHttpResponse get(String url, HashMap<String,String> headerMap) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url); // http get request

        for(Map.Entry<String,String> entry : headerMap.entrySet()){
            httpGet.addHeader(entry.getKey(), entry.getValue());
        }
        CloseableHttpResponse httpResponse = httpClient.execute(httpGet);

        return httpResponse;

    }
}
