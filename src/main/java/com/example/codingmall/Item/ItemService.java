package com.example.codingmall.Item;

import com.example.codingmall.Category.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemService {
    private final ItemRepository itemRepository;
    private final CategoryRepository categoryRepository;

    @Transactional
    public Item findItemById(Long id){
        return itemRepository.findById(id)
                .orElseThrow(() ->new IllegalStateException("Invalid Item ID"));
    }
    @Transactional
    public Item findItemByProductName(String productName){
        return itemRepository.findByProductName(productName)
                .orElseThrow(() -> new IllegalStateException("There is no such productName"));
    }
    @Transactional
    public List<Item> findAllItems(){
        return itemRepository.findAll();
    }

    @Transactional
    public Item saveItem(Item item){ // 저장
        return itemRepository.save(item);
    }

    @Transactional
    public void deleteItemById(Long id){ // 삭제
        Item item = findItemById(id);
        itemRepository.delete(item);
    }
    // 상품수정
    @Transactional
    public void updateItem(Long id, ItemDto itemDto){
        Item item = findItemById(id);
        item.updateItem(itemDto);
    }

    // 상품 등록
    @Transactional
    public Item saveItem(ItemDto itemDto){
        categoryRepository.findById(itemDto.getCategory().getId())
                .orElseThrow(() -> new IllegalStateException("Invalid category ID")); // category개발 후 findCategoryById 로 바꾸기

        Item item = findItemById(itemDto.getId());
        Item saveitem = itemDto.toEntity();
        return itemRepository.save(saveitem);
    }
}
