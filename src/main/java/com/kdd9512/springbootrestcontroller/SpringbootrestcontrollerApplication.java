package com.kdd9512.springbootrestcontroller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateCustomizer;
import org.springframework.boot.web.reactive.function.client.WebClientCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class SpringbootrestcontrollerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootrestcontrollerApplication.class, args);
    }

    // builder 커스터마이징.
    // 예제에서는 기본 url 을 설정 하였으므로, WebClientCustomizer 을 사용하는 메서드 등지에서 코드를 간략화할 수 있다.
    // 람다식으로 간략화.
    @Bean
    public WebClientCustomizer webClientCustomizer() {
        return webClientBuilder -> webClientBuilder.baseUrl("http://localhost:8080");
    }

    @Bean
    public RestTemplateCustomizer restTemplateCustomizer() {
        return restTemplate -> restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
    }



}
