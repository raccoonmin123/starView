// src/main/java/com/example/demo/StarViewApplication.java
package com.example.demo;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.demo.mapper")
public class StarViewApplication {

    public static void main(String[] args) {
        SpringApplication.run(StarViewApplication.class, args);
    }
}