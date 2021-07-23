package com.java.zemo;

import com.java.zemo.Exception.MyExceptionResolver;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ZemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZemoApplication.class, args);
    }

    // 注册统一异常处理bean
    @Bean
    public MyExceptionResolver globalExceptionResolver() {
        return new MyExceptionResolver();
    }

}
