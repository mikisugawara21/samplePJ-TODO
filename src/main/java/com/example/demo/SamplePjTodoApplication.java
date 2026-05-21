package com.example.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.example.demo", "sample"})
@MapperScan("sample.common.dao.mapper")
public class SamplePjTodoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SamplePjTodoApplication.class, args);
    }
}