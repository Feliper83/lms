package org.cb.minilms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching

public class MinilmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(MinilmsApplication.class, args);
    }

}
