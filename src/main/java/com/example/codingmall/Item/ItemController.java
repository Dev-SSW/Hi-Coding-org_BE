package com.example.codingmall.Item;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("public/items")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    // 카테고리 조회
    @GetMapping("/Categories")
    public ResponseEntity<?> getCategoryList(){
        return ResponseEntity.ok(itemService.getCategoryList());
    }
    // 특정 상품 조회
    @GetMapping("{id}")
    public ResponseEntity<Item>getItemById(@PathVariable Long id){
        Item item = itemService.findItemById(id);
        return ResponseEntity.ok(item);
    }

    //특정 상품 상품명으로 조회
    @GetMapping("/search")
    public ResponseEntity<Item> getItemByProductName(@RequestParam String productName){
        Item item = itemService.findItemByProductName(productName);
        return ResponseEntity.ok(item);
    }

    // 모든 상품 조회
    @GetMapping
    public ResponseEntity<List<Item>> getAllItem(){
        List<Item> items = itemService.findAllItems();
        return  ResponseEntity.ok(items);
    }

    @PostMapping
    // 새로운 상품 등록
    public ResponseEntity<Item> createItem(@RequestBody ItemDto itemDto){
        Item createdItem = itemService.saveItem(itemDto);
        return ResponseEntity.ok(createdItem);
    }

    // 기존 상품 수정.
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateItem(@PathVariable Long id, @RequestBody ItemDto itemDto){
        itemService.updateItem(id,itemDto);
        return ResponseEntity.noContent().build();
    }
    //상품 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long id){
        itemService.deleteItemById(id);
        return ResponseEntity.noContent().build(); // 삭제 후 응답없음.
    }

    // 상품 좋아요 추가
    @PostMapping("/{id}/like")
    public ResponseEntity<String> addLikeToItem(@PathVariable Long id){
        itemService.addLikeToItem(id);
        return ResponseEntity.ok("Like added : " + id);
    }
}
