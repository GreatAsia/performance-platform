package com.okay.testcenter.tools.singlelogin;

import org.apache.http.client.CookieStore;

import java.util.HashMap;
import java.util.Map;

public class CookieResponse {

    private Map<String, String> cookies = new HashMap<>();

    private CookieStore cookieStore;
    private String result;
    private String requestId;


    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }






    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }


    public Map<String, String> getCookies() {
        return cookies;
    }

    public void setCookies(Map<String, String> cookies) {
        this.cookies = cookies;
    }

    public CookieStore getCookieStore() {
        return cookieStore;
    }

    public void setCookieStore(CookieStore cookieStore) {
        this.cookieStore = cookieStore;
    }
}
