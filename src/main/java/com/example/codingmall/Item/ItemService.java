package com.example.codingmall.Item;

import com.example.codingmall.Category.Category;
import com.example.codingmall.Category.CategoryDto;
import com.example.codingmall.Category.CategoryRepository;
import lombok.RequiredArgsConstructor;
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


    @Transactional(readOnly = true)
    public Item findItemById(Long id) {
        return itemRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Invalid Item ID"));
    }

    @Transactional(readOnly = true)
    public Item findItemByProductName(String productName) {
        return itemRepository.findByProductName(productName)
                .orElseThrow(() -> new IllegalStateException("There is no such productName"));
    }

    @Transactional(readOnly = true)
    public List<Item> findAllItems() {
        return itemRepository.findAll();
    }

    @Transactional
    public void deleteItemById(Long id) { // 삭제
        Item item = findItemById(id);
        itemRepository.delete(item);
    }

    // 상품수정
    @Transactional
    public void updateItem(Long id, ItemDto itemDto) {
        Item item = findItemById(id);
        item.updateItem(itemDto);
    }

    // 상품 등록
    @Transactional
    public Item saveItem(ItemDto itemDto) {
        categoryRepository.findCategoryById(itemDto.getCategory().getId());
        Item saveitem = itemDto.toEntity();
        return itemRepository.save(saveitem);
    }

    // 좋아요 기능
    @Transactional
    public void addLikeToItem(Long itemId) {
        Item item = findItemById(itemId);
        item.addLike();
        itemRepository.save(item);
    }
}
