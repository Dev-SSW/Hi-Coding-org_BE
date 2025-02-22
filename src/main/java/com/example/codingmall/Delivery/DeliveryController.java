package com.example.codingmall.Delivery;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("deliveries")
@Tag(name = "Delivery" , description = "배송을 관리하는 api")
@RequiredArgsConstructor
public class DeliveryController {
    private final DeliveryService deliveryService;
    @PostMapping
    @Operation(summary = "배송 정보 생성", description = "새로운 배송 정보를 생성합니다.")
    public ResponseEntity<DeliveryResponseDto> createDelivery(@RequestBody DeliveryDto deliveryDto){
        DeliveryResponseDto delivery = deliveryService.createDelivery(deliveryDto);
        return ResponseEntity.ok(delivery);
    }

    @PostMapping("/{deliveryId}/start")
    @Operation(summary = "배송 시작",description = "배송을 시작합니다.(Pending -> On the Way)")
    public ResponseEntity<Void> startDelivery(@PathVariable Long deliveryId){
        deliveryService.startDelivery(deliveryId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{deliveryId}/complete")
    @Operation(summary = "배송 완료",description = "배송을 완료합니다.(On the Way -> Complete)")
    public ResponseEntity<Void> completeDelivery(@PathVariable Long deliveryId){
        deliveryService.completeDelivery(deliveryId);
        return ResponseEntity.ok().build();
    }
    @PostMapping({"/{deliveryId}/cancel"})
    @Operation(summary = "배송 취소" ,description = "배송을 취소합니다.(Complete -> Cancel)")
    public ResponseEntity<Void> cancelDelivery(@PathVariable Long deliveryId){
        deliveryService.cancelDelivery(deliveryId);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/{deliveryId}")
    @Operation(summary = "배송 정보 가져오기",description = "하나의 배송 정보를 가져옵니다.")
    public ResponseEntity<DeliveryResponseDto> getDelivery(@PathVariable Long deliveryId){
        DeliveryResponseDto deliveryResponseDto = deliveryService.getDelivery(deliveryId);
        return ResponseEntity.ok(deliveryResponseDto);
    }
    @GetMapping("/status/{status}")
    @Operation(summary = "배송 상태별 정보 가져오기",description = "배송 상태별 정보를 가져옵니다.")
    public ResponseEntity<List<DeliveryResponseDto>> getDeliveriesByStatus(@PathVariable DeliveryStatus status){
        List<DeliveryResponseDto> deliveries = deliveryService.getDeliveriesByStatus(status);
        return ResponseEntity.ok(deliveries);
    }
}
