package com.genehelix;

import com.genehelix.utils.Util;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.genehelix")
public class Genehelix {

    public static void main(String[] args) {
        SpringApplication.run(Genehelix.class, args);
        Util.hashPassword("Hikmah");
    }

}
