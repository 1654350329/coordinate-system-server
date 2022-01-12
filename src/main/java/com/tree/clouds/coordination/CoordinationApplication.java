package com.tree.clouds.coordination;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
@MapperScan("com.tree.clouds.coordination.mapper")
public class CoordinationApplication {
    public static void main(String[] args) {
        SpringApplication.run(CoordinationApplication.class, args);
        log.info("劳动鉴定协同系统启动成功");
    }
}
