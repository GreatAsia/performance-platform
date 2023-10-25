package com.okay.testcenter.runner;


import com.okay.testcenter.domain.middle.RequestSampler;
import com.okay.testcenter.domain.middle.ResponseSampler;
import com.okay.testcenter.request.RequestFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RunnerFactory {


    private static final Logger logger = LoggerFactory.getLogger(RunnerFactory.class);

    public static ResponseSampler build(RequestSampler requestSampler) {
        ResponseSampler responseSampler;
        int runType = requestSampler.getRunType();
        switch (runType) {
            case 1:
                responseSampler = new WebRunner().runWebCase(requestSampler);
                break;
            case 2:
                responseSampler = new AppRunner().runAppCase(requestSampler);
                break;
            case 3:
                responseSampler = new RunnerBase().runDirectly(requestSampler);
                break;
            default:
                responseSampler = new RunnerBase().runDirectly(requestSampler);
                logger.info("default runner ");
                break;

        }
        return responseSampler;


    }

    public static ResponseSampler prepare(RequestSampler requestSampler) {

        ResponseSampler responseSampler;
        int loginType = requestSampler.getLoginType();
        switch (loginType) {
            case 1:
                responseSampler = new WebRunner().webLogin(requestSampler);
                break;
            case 2:
                responseSampler = new AppRunner().appLogin(requestSampler);
                break;
            case 3:
                responseSampler = new RunnerBase().runSingleLogin(requestSampler);
                break;
            case 4:
                responseSampler = new ResponseSampler();
                responseSampler.setLoginResult(true);
                break;
            default:
                responseSampler = RequestFactory.build(requestSampler);
                logger.info("loginType  default runner ");
                break;

        }
        return responseSampler;


    }


}
