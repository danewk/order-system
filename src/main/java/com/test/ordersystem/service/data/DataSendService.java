package com.test.ordersystem.service.data;

import com.test.ordersystem.config.WebClientConfig;
import com.test.ordersystem.dto.order.OrderSendDataRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class DataSendService {

  private final WebClientConfig webClientConfig;
  @Async("OrderDataSendExecutor")
  public void sendData(OrderSendDataRequest dataRequest){

    //데이터 수집 플랫폼으로 Post 전송
    WebClient webClient = webClientConfig.getWebClient();

    webClient.post()
        .uri("/order-data")
        .body(Mono.just(dataRequest), OrderSendDataRequest.class)
        .retrieve()
        .bodyToMono(Void.class)
        .subscribe();

  }

}
