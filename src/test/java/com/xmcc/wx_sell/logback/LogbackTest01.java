package com.xmcc.wx_sell.logback;





import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest//启动springboot测试 会自动去寻找一application结尾的引导类,运行main方法
@RunWith(SpringRunner.class)
public class LogbackTest01 {
    private final Logger logger = LoggerFactory.getLogger(LogbackTest01.class);
    @Test
    public void test(){
        logger.debug("logger日志，debug");
        logger.info("logger日志，info级别");
        logger.error("logger日志，error级别");
    }
}
