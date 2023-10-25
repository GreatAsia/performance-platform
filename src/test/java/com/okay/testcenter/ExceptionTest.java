package com.okay.testcenter;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ExceptionTest {


    @Value("${jmeter.path}")
    private String path;


    private static final Logger logger = LoggerFactory.getLogger(ExceptionTest.class);

    @Test
    public void testException() {

        int s = 1 / 0;


    }

    @Test
    public void testPath(){

        logger.info("path:" + path);
    }


}
