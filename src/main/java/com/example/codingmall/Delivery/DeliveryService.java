package com.example.codingmall.Delivery;

import com.example.codingmall.Order.Order;
import com.example.codingmall.Order.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.br.TituloEleitoral;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DeliveryService {
    private final DeliveryRepository deliveryRepository;
    private final OrderRepository orderRepository;

    @Transactional
    public DeliveryResponseDto createDelivery (DeliveryDto deliveryDto){
        Order order = orderRepository.findOrderById(deliveryDto.getOrderId());
        Delivery delivery = Delivery.createDelivery(order, deliveryDto);
        deliveryRepository.save(delivery);

        return new DeliveryResponseDto(delivery);
    }
    @Transactional
    public void startDelivery(Long deliveryId){
        Delivery delivery = deliveryRepository.findByDeliveryId(deliveryId);
        delivery.startDelivery();
    }
    @Transactional
    public void completeDelivery(Long deliveryId){
        Delivery delivery = deliveryRepository.findByDeliveryId(deliveryId);
        delivery.completeDelivery();
    }

    @Transactional
    public void cancelDelivery(Long deliveryId){
        Delivery delivery = deliveryRepository.findByDeliveryId(deliveryId);
        delivery.cancelDelivery();
    }
    public DeliveryResponseDto getDelivery(Long deliveryId){
        Delivery delivery = deliveryRepository.findByDeliveryId(deliveryId);
        return new DeliveryResponseDto(delivery);
    }
    public List<DeliveryResponseDto> getDeliveriesByStatus(DeliveryStatus status){
        return deliveryRepository.findByStatus(status).stream()
                .map(DeliveryResponseDto::new)
                .collect(Collectors.toList());
    }
}
