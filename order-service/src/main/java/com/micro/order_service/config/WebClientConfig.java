package com.micro.order_service.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    //danh cho cac service 1 phien ban
//    @Bean
//    public WebClient webClient(){
//        return WebClient.builder().build();
//    }


    //1 service cos nhieu phien ban tro len thi cach giao tiep phai khac
    //suwr dungj may chu eureka va may khach client
    @Bean
    @LoadBalanced  //them kha nang can bang tai
    public WebClient.Builder webClientBuilder(){
        return WebClient.builder();
    }
}
