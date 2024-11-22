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
        Item item = itemRepository.findItemById(id);
        Hibernate.initialize(item.getCategory());
        return ItemDto.from(item);
    }

    public ItemDto findItemByProductName(String productName) {
        Item item = itemRepository.findItemByProductName(productName);
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
        Item item = itemRepository.findItemById(itemDto.getId());
        if (itemDto.getCategory() !=null && itemDto.getCategory().getId() != null){
            Category category = categoryRepository.findCategoryById(itemDto.getCategory().getId());
            item.setCategory(category);
        }
        item.updateItem(itemDto);
        itemRepository.save(item);
    }

    // 상품 등록
    @Transactional
    public Item saveItem(ItemDto itemDto) {
        Category category = categoryRepository.findCategoryById(itemDto.getCategory().getId());
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
