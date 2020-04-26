package com.ego.item;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.ego.item.mapper")
public class EgoItemService {
    public static void main(String[] args){
        SpringApplication.run(EgoItemService.class,args);
    }
}
