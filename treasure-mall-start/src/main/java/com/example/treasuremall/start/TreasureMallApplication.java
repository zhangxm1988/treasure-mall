package com.example.treasuremall.start;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @description: 项目启动类
 * 这里指定scanBasePackages是因为需要跨模块扫描bean，所以一定要指定扫描的包，同时需要在maven中加入想要包的依赖
 * @author 小沙弥
 * @date 2021/7/24 1:22 下午
 */
@SpringBootApplication(scanBasePackages = "com.example.treasuremall.**")
public class TreasureMallApplication {

    public static void main(String[] args) {
        SpringApplication.run(TreasureMallApplication.class, args);
    }

}
