package login.project.web;

import login.project.domain.Item;
import login.project.repository.ItemUpdateDto;
import login.project.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/items")
public class ItemController {
    private final ItemService itemService;

    @GetMapping("")
    public ResponseEntity<List<Item>> items() {
        List<Item> items = itemService.findItems();
        if(items == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(items);
    }

    @GetMapping("/{itemId}")
    public ResponseEntity<Item> item(@PathVariable("itemId") Long itemId) {
        Item item = itemService.findById(itemId).get();
        if (item == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(item);
    }

    @PostMapping("/add")
    public ResponseEntity<Item> addItem(Item item) {
        itemService.save(item);
        return ResponseEntity.ok().body(item);
    }

    @PostMapping("/{itemId}/edit")
    public ResponseEntity<ItemUpdateDto> edit(@PathVariable Long itemId, ItemUpdateDto updateParam) {
        itemService.update(itemId, updateParam);
        return ResponseEntity.ok().body(updateParam);
    }
}