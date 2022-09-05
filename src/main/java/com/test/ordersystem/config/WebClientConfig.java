package com.test.ordersystem.config;


import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import javax.print.attribute.standard.Media;
import lombok.Builder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

@Configuration
public class WebClientConfig {

  // TODO: 2022/09/06 데이터 수집 플랫폼 URL이 필요함.
  private final static String URL = "http://localhost:8081";

  @Bean
  public WebClient getWebClient(){
    HttpClient httpClient = HttpClient.create()
        .tcpConfiguration(tcpClient -> tcpClient.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 100000)
            .doOnConnected(conn -> conn.addHandlerLast(new ReadTimeoutHandler(10))
                .addHandlerLast(new WriteTimeoutHandler(10))));

    ClientHttpConnector clientHttpConnector = new ReactorClientHttpConnector(httpClient);

    return WebClient.builder()
        .baseUrl(URL)
        .clientConnector(clientHttpConnector)
        .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        .build();
  }

}
