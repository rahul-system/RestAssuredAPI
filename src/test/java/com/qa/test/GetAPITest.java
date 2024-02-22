package com.qa.test;

import com.qa.base.TestBase;
import com.qa.client.RestClient;
import com.qa.util.TestUtil;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.HashMap;

public class GetAPITest extends TestBase {

    String serviceUrl;
    String apiUrl;
    String url;

    CloseableHttpResponse httpResponse;

    @BeforeMethod
    public void setUp() throws IOException {
        serviceUrl = prop.getProperty("url");
        apiUrl = prop.getProperty("serviceUrl");

        url = serviceUrl + apiUrl;

    }

    @Test
    public void getAPITestWithHeaders() throws IOException {
        RestClient restClient = new RestClient();

        HashMap<String, String> headerMap = new HashMap<String, String>();
        headerMap.put("content-type" ,"application/json");
//        headerMap.put("auth-token","jsjdokowke12");

        httpResponse = restClient.get(url, headerMap);

        //a. Status Code
        int statusCode = httpResponse.getStatusLine().getStatusCode(); //get status code
        System.out.println("Status Code ----> " + statusCode);

        Assert.assertEquals(RESPONSE_STATUS_CODE_200, statusCode, "Status Code is not 200");

        //b. JSON String
        HttpEntity entity = httpResponse.getEntity();
        if (entity == null) {
            System.out.println("Empty or null response from the server.");
            return;
        }

        String responseString = EntityUtils.toString(entity, "UTF-8"); //get response string

        JSONObject responseJson = null;
        try {
            responseJson = new JSONObject(responseString);
        } catch (Exception e) {
            System.err.println("Error parsing JSON: " + e.getMessage());
        }
        System.out.println("Response JSON from API ---> " + responseJson);

        //per_page:
        String perPageValue = TestUtil.getValueByJSONPath(responseJson, "/per_page");
        System.out.println("value of per page is ---> " + perPageValue);

        Assert.assertEquals(Integer.parseInt(perPageValue),6);

        //total:
        String totalValue = TestUtil.getValueByJSONPath(responseJson, "/total");
        System.out.println("value of total is ---> " + totalValue);

        Assert.assertEquals(Integer.parseInt(totalValue), 12);

        //get value from JSON Array
        String lastName = TestUtil.getValueByJSONPath(responseJson, "/data[0]/last_name");
        String id = TestUtil.getValueByJSONPath(responseJson, "/data[0]/id");
        String firstName = TestUtil.getValueByJSONPath(responseJson, "/data[0]/first_name");
        String avatar = TestUtil.getValueByJSONPath(responseJson, "/data[0]/avatar");

        System.out.println(lastName);
        System.out.println(id);
        System.out.println(firstName);

        //c. All Headers
        Header[] headersArray = (Header[]) httpResponse.getAllHeaders(); // get all headers
        HashMap<String, String> allHeaders = new HashMap<>(); // initialize HashMap
        for (Header header : headersArray) {
            allHeaders.put(header.getName(), header.getValue()); // put header values in HashMap
        }
        System.out.println("All Headers from Response ---> " + allHeaders);

    }


}
