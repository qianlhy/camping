package com.camping.life;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.camping.life.**.mapper")
@SpringBootApplication
public class CampingLifeAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(CampingLifeAdminApplication.class, args);
    }

}
