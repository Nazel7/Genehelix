package com.genehelix;

import com.genehelix.utils.Util;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication(scanBasePackages = "com.genehelix")
public class Genehelix {

    public static void main(String[] args) {
        SpringApplication.run(Genehelix.class, args);
        Util.hashPassword("mikro2090");
    }

    @Bean
    public RestTemplate mRestTemplate(){

        return new RestTemplate();
    }

}
