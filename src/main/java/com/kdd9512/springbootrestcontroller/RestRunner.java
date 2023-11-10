package com.kdd9512.springbootrestcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class RestRunner implements ApplicationRunner {

    @Autowired
    WebClient.Builder builder;

//    @Autowired
//    RestTemplateBuilder builder;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // RestTemplate restTemplate = builder.build();

        WebClient client = builder.build();
        StopWatch stopWatch = new StopWatch();

        // 시간 측정
        stopWatch.start();

        // Non-Blocking I/O 기반 비동기식 API
        Mono<String> helloMono = client.get().uri("http://localhost:8080/hello")
                .retrieve().bodyToMono(String.class);

        helloMono.subscribe(s -> { System.out.println(s);

            if (stopWatch.isRunning()) {
                stopWatch.stop();
            }

            System.out.println(stopWatch.prettyPrint());
            stopWatch.start();

        });

        Mono<String> worldMono = client.get().uri("http://localhost:8080/world")
                .retrieve().bodyToMono(String.class);

        worldMono.subscribe(s -> { System.out.println(s);

            if (stopWatch.isRunning()) {
                stopWatch.stop();
            }

            System.out.println(stopWatch.prettyPrint());
            stopWatch.start();

        });

//         RestTemplateBuilder 활용한 검증. Blocking I/O 기반의 동기식 API
//        // TODO /hello
//        String helloObj = restTemplate.getForObject("http://localhost:8080/hello", String.class);// 주소, 예상 결과타입
//        System.out.println(helloObj);
//
//        // TODO /world
//        String worldObj = restTemplate.getForObject("http://localhost:8080/world", String.class);
//        System.out.println(worldObj);

        stopWatch.stop();
        System.out.println(stopWatch.prettyPrint());

    }
}
