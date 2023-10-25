package com.okay.testcenter.request;

import com.okay.testcenter.domain.middle.RequestSampler;
import com.okay.testcenter.domain.middle.ResponseSampler;

public interface Request {

    public ResponseSampler post(RequestSampler requestSampler);

    public ResponseSampler get(RequestSampler requestSampler);
}
