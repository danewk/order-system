package com.test.ordersystem.service.order;

import com.test.ordersystem.domain.OrderStatus;
import com.test.ordersystem.domain.entity.MenuEntity;
import com.test.ordersystem.domain.entity.OrderEntity;
import com.test.ordersystem.domain.repository.MenuRepository;
import com.test.ordersystem.domain.repository.OrderRepository;
import com.test.ordersystem.dto.order.OrderCreateRequest;
import com.test.ordersystem.dto.order.OrderResponse;
import com.test.ordersystem.dto.order.OrderSendDataRequest;
import com.test.ordersystem.dto.point.PointPayResponse;
import com.test.ordersystem.exception.ErrorMessage;
import com.test.ordersystem.service.data.DataSendService;
import com.test.ordersystem.service.pay.PayService;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderService {

  private final OrderRepository orderRepository;
  private final MenuRepository menuRepository;

  private final OrderHistoryService menuCountService;
  private final OrderItemService orderItemService;
  private final PayService payService;
  private final DataSendService dataSendService;


  @Transactional
  public OrderResponse create(OrderCreateRequest request) {

    //주문한 메뉴가 전체메뉴에 있는지 검증
    MenuEntity menuEntity = validationOrderMenus(request.getMenuId());

    //주문 엔티티 생성
    OrderEntity savedOrder = createOrders(request);

    //주문 아이템 생성
    orderItemService.create(menuEntity, savedOrder);

    //결제
    final PointPayResponse payResponse = payService.pay(savedOrder.getId(), request.getUserId());

    //주문상태 변경
    savedOrder.setStatus(OrderStatus.ORDER);

    //주문 조회수 카운트
    menuCountService.addOrderHistory(request.getMenuId(),request.getUserId());

    OrderSendDataRequest dataRequest = OrderSendDataRequest.builder()
        .orderedPrice(payResponse.getOrderedPrice())
        .menuId(request.getMenuId())
        .userId(request.getUserId())
        .build();

    //주문내역 데이터 수집 플랫폼으로 전송 비동기 호출
    dataSendService.sendData(dataRequest);

    return OrderResponse.builder()
        .orderedPrice(payResponse.getOrderedPrice())
        .orderStatus(savedOrder.getStatus())
        .remainingPoints(payResponse.getPointAmount())
        .orderId(savedOrder.getId())
        .build();
  }


   public OrderEntity createOrders(OrderCreateRequest request) {
    OrderEntity newOrder = OrderEntity.builder()
        .orderDate(LocalDateTime.now())
        .status(OrderStatus.READY)
        .userId(request.getUserId())
        .build();

    return orderRepository.save(newOrder);
  }

  private MenuEntity validationOrderMenus(Long menuId) {

    return menuRepository.findAllWithMenuId(menuId)
        .orElseThrow(() -> new IllegalStateException(ErrorMessage.NOT_EXIST_MENU.getMessage()));

  }

}
