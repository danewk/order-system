package com.test.ordersystem.config;

import java.util.concurrent.Executor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@EnableAsync
@Configuration
public class AsyncConfig extends AsyncConfigurerSupport {

  /**
   * corePoolSize: 기본적으로 실행을 대기하고 있는 Thread의 갯수
   * MaxPoolSise: 동시 동작하는, 최대 Thread 갯수
   * QueueCapacity : MaxPoolSize를 초과하는 요청이 Thread 생성 요청시 해당 내용을 Queue에 저장하게 되고,
   * 사용할수 있는 Thread 여유 자리가 발생하면 하나씩 꺼내져서 동작하게 된다.
   */
  @Bean(name = "OrderDataSendExecutor")
  public Executor getAsyncExecutor() {
    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
    executor.setCorePoolSize(2);
    executor.setMaxPoolSize(10);
    executor.setQueueCapacity(500);
    executor.setThreadNamePrefix("order-system-async-");
    executor.initialize();
    return executor;
  }

}