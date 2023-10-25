package com.okay.testcenter.request;

import com.okay.testcenter.domain.middle.RequestSampler;
import com.okay.testcenter.domain.middle.ResponseSampler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestFactory {

    private static final Logger logger = LoggerFactory.getLogger(RequestFactory.class);

    public static ResponseSampler build(RequestSampler requestSampler) {
        ResponseSampler responseSampler = new ResponseSampler();
        switch (requestSampler.getMethod()) {
            case "Post-Form":
                requestSampler.setContentType("application/x-www-form-urlencoded;charset=UTF-8");
                responseSampler = new PostFormRequest().post(requestSampler);
                break;
            case "Post-Json":
                requestSampler.setContentType("application/json");
                responseSampler = new PostJsonRequest().post(requestSampler);
                break;
            case "Post-Text":
                requestSampler.setContentType("text/plain");
                responseSampler = new PostTextRequest().post(requestSampler);
                break;
            case "Post-Html":
                requestSampler.setContentType("text/html");
                responseSampler = new PostHtmlRequest().post(requestSampler);
                break;
            case "Post-Xml":
                requestSampler.setContentType("application/xml");
                responseSampler = new PostXmlRequest().post(requestSampler);
                break;
            case "Post-Binary":
                requestSampler.setContentType("application/octet-stream");
                responseSampler = new PostBinaryRequest().post(requestSampler);
                break;
            case "Get":
                responseSampler = new GetRequest().get(requestSampler);
                break;
            default:
                logger.error("request method error");

        }
        return responseSampler;


    }


}
