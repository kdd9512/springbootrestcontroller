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

    @Autowired
    RestTemplateBuilder restBuilder;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // RestTemplate restTemplate = restBuilder.build();


        WebClient client = builder.build();
        StopWatch stopWatch = new StopWatch();

        // 시간 측정
        stopWatch.start();

        // Non-Blocking I/O 기반 비동기식 API. 이대로는 동작하지 않는다.
        Mono<String> helloMono = client.get().uri("/hello")
                .retrieve().bodyToMono(String.class);

        // 이를 streaming API 라고 하며, 반드시 이하처럼 subscribe 를 이용하여 동작시켜야 한다.
        helloMono.subscribe(s -> { System.out.println(s);

            if (stopWatch.isRunning()) {
                stopWatch.stop();
            }

            System.out.println(stopWatch.prettyPrint());
            stopWatch.start();

        });

        // controller 단에서 해당 페이지 호출을 지연하도록 하는 코드를 넣었기 때문에
        // app 구동 시, 지연시간이 짧은 world 부터 먼저 출력된다.
        Mono<String> worldMono = client.get().uri("/world")
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
