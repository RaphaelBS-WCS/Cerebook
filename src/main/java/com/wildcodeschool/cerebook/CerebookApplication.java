package com.wildcodeschool.cerebook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
public class CerebookApplication {

    public static void main(String[] args) {
        SpringApplication.run(CerebookApplication.class, args);
    }

}
