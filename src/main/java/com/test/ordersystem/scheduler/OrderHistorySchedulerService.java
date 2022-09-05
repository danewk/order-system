package com.test.ordersystem.scheduler;

import com.test.ordersystem.domain.entity.OrderHistoryEntity;
import com.test.ordersystem.domain.entity.PopularMenuEntity;
import com.test.ordersystem.domain.repository.OrderHistoryRepository;
import com.test.ordersystem.service.menu.PopularMenuService;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderHistorySchedulerService {

  private final OrderHistoryRepository orderHistoryRepository;
  private final PopularMenuService popularMenuService;

  @Scheduled(cron = "0 0 0 * * *")
  public void top3MenuSetting(){
    final List<OrderHistoryEntity> orderHistoryEntityList = orderHistoryRepository.findAllWithBaseTime(LocalDateTime.now().minusDays(7));

    // 7일전 데이터부터 현재까지 쌓인 데이터 그룹핑
    final Map<Long, Long> collect = orderHistoryEntityList
        .stream()
        .collect(Collectors.groupingBy(OrderHistoryEntity::getMenuId, Collectors.summingLong(OrderHistoryEntity::getCount)));

    List<Entry<Long, Long>> collect1 = collect.entrySet()
        .stream()
        .sorted(Entry.comparingByValue(Comparator.reverseOrder()))
        .limit(3)
        .toList();

    List<PopularMenuEntity> popularMenuEntities = collect1.stream()
        .map(entry -> convertToEntity(entry.getKey(), entry.getValue()))
        .toList();

    //기존 인기 데이터 삭제 처리
    popularMenuService.deleteExistData();

    //인기 데이터 업데이트 처리
    popularMenuService.create(popularMenuEntities);

  }

  private PopularMenuEntity convertToEntity(Long key, Long value) {
    return PopularMenuEntity.builder()
        .menuId(key)
        .count(value)
        .build();
  }


}