package com.xmcc.wx_sell.logback;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class LogBackTest02 {
    @Test
    public void test(){
        log.debug("logger日志，debug");
        log.info("logger日志，info级别");
        log.error("logger日志，error级别");
    }

}
