package com.example.codingmall.Item;

import com.example.codingmall.Category.Category;
import com.example.codingmall.Category.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemService {
    private final ItemRepository itemRepository;
    private final CategoryRepository categoryRepository;


    public ItemDto findItemById(Long id) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("적절하지 않은 제품 ID 입니다. " + id));
        Hibernate.initialize(item.getCategory());
        return ItemDto.from(item);
    }

    public ItemDto findItemByProductName(String productName) {
        Item item = itemRepository.findByProductName(productName)
                .orElseThrow(() -> new IllegalStateException("그러한 제품 이름이 없습니다. " + productName));
        return ItemDto.from(item);
    }

    public List<ItemDto> findAllItems() {
        List<Item> items = itemRepository.findAll();
        items.forEach(item -> Hibernate.initialize(item.getCategory())); // --> LAZY로 로딩된 필드를 직렬화 중 누락하지 않도록 설정.
        return items.stream()
                .map(ItemDto::from)
                .toList();
    }

    @Transactional
    public void deleteItemById(Long id) { // 삭제
        itemRepository.deleteById(id);
    }

    // 상품수정
    @Transactional
    public void updateItem(ItemDto itemDto) {
        Item item = itemRepository.findById(itemDto.getId())
                .orElseThrow(() -> new IllegalStateException("해당 ID의 제품이 없습니다.: " + itemDto.getId()));

        if (itemDto.getCategory() !=null && itemDto.getCategory().getId() != null){
            Category category = categoryRepository.findById(itemDto.getCategory().getId())
                    .orElseThrow(() -> new IllegalStateException("존재하지 않는 카테고리 입니다. 카테고리 ID : " + itemDto.getCategory().getId()));
            item.setCategory(category);
        }
        item.updateItem(itemDto);
        itemRepository.save(item);
    }

    // 상품 등록
    @Transactional
    public Item saveItem(ItemDto itemDto) {
        Category category = categoryRepository.findCategoryById(itemDto.getCategory().getId())
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 카테고리 입니다." + itemDto.getCategory().getId()));
        Item saveitem = itemDto.toEntity();
        saveitem.setCategory(category);
        return itemRepository.save(saveitem);
    }

    // 좋아요 +1
    @Transactional
    public void addLikeToItem(Long itemId) {
        ItemDto itemDto = findItemById(itemId);
        Item item = itemDto.toEntity();
        item.addLike();
        itemRepository.save(item);
    }
}
