package com.example.codingmall.Delivery;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Delivery", description = "배송 관리 Api")
@RestController
@RequestMapping("public/delivery")
@RequiredArgsConstructor
public class DeliveryController {
    private final DeliveryService deliveryService;

    @Operation(summary = "배송 정보 생성", description = "새로운 배송 정보를 생성합니다.")
    @PostMapping
    public ResponseEntity<Long>createDelivery(
            @Parameter(description = "배송 정보",required = true)
            @RequestBody @Valid DeliveryDto request){
        Long deliveryId = deliveryService.saveDelivery(request);
        return ResponseEntity.ok(deliveryId);
    }
    @Operation(summary = "배송 정보 조회",description = "배송 정보를 조회합니다")
    @GetMapping("{id}")
    public ResponseEntity <Delivery> getDelivery(@PathVariable Long id){
        Delivery delivery = deliveryService.findDelivery(id);
        return ResponseEntity.ok(delivery);
    }
    @Operation(summary = "주문별 배송 정보 조회",description = "주문 id로 배송 정보 조회")
    @GetMapping("/order/{orderId}")
    public ResponseEntity<Delivery> getDeliveryByOrder(@PathVariable Long orderId){
        Delivery delivery = deliveryService.findDeliveryByOrder(orderId);
        return ResponseEntity.ok(delivery);
    }
    // 배송 상태 변경
    @Operation(summary = "배송 상태 변경",description = "배송 상태를 변경합니다.")
    @PutMapping("{id}/status")
    public ResponseEntity<Void> updateStatus(@PathVariable Long id, @RequestParam boolean status){
        deliveryService.updateStatus(id,status);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "전체 배송 목록 조회",description = "모든 배송 정보를 조회합니다.")
    @GetMapping
    public ResponseEntity<List<Delivery>> getAllDeliveries(){
        List<Delivery> deliveries =
                deliveryService.findAllDeliveries();
        return ResponseEntity.ok(deliveries);
    }

    @Operation(summary = "카드사별 배송 정보 조회",description = "카드사별 배송 정보 목록을 조회합니다.")
    @GetMapping("/card-company/{cardCompany}")
    public ResponseEntity <List<Delivery>> getAllDeliveriesByCardCompany(
            @PathVariable String cardCompany){
        List<Delivery> deliveries = deliveryService.findDeliveriesByCardCompany(cardCompany);
        return ResponseEntity.ok(deliveries);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<String> handleIlleageArgument(IllegalStateException e){
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
