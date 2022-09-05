package com.test.ordersystem.service.menu;

import com.test.ordersystem.domain.entity.PopularMenuEntity;
import com.test.ordersystem.domain.repository.PopularMenuRepository;
import com.test.ordersystem.dto.menu.PopularMenuResponse;
import java.util.Comparator;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PopularMenuService {

  private final PopularMenuRepository popularMenuRepository;

  @Transactional
  public void deleteExistData() {

    List<PopularMenuEntity> existData = popularMenuRepository.findAllByDeletedAtIsNull();

    for (PopularMenuEntity data : existData) {
      data.delete();
    }
  }

  @Transactional
  public void create(List<PopularMenuEntity> popularMenuEntities) {
    popularMenuRepository.saveAll(popularMenuEntities);
  }

  public List<PopularMenuResponse> top3Menu() {
    List<PopularMenuEntity> existData = popularMenuRepository.findAllByDeletedAtIsNull();

    return existData.stream()
        .sorted(Comparator.comparingLong(PopularMenuEntity::getCount).reversed())
        .map(PopularMenuEntity::from)
        .toList();

  }
}
