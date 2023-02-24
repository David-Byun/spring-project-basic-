package login.project.controller;

import login.project.domain.Item;
import login.project.repository.item.ItemUpdateDto;
import login.project.service.item.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
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
    public ResponseEntity<Item> addItem(@Validated Item item, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.info("검증 오류 발생 errors={}", bindingResult);
            return (ResponseEntity<Item>) bindingResult.getAllErrors();
        }
        itemService.save(item);
        log.info("성공 로직 실행");
        return ResponseEntity.ok().body(item);
    }

    //return bindingResult.getAllErrors()는 ObjectError와 FieldError를 반환한다. 스프링은 이 객체를 JSON으로 변환하여 클라이언트에 전달. 실제 개발 할때는 이 객체를 그대로 사용하지 말고, 필요한 데이터만 뽑아서 별도의 API 스펙을 정의하고 그에 맞는 객체를 만들어서 반환한다.
    @PostMapping("/{itemId}/edit")
    public ResponseEntity<ItemUpdateDto> edit(@PathVariable Long itemId,  @Validated ItemUpdateDto updateParam, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.info("검증 오류 발생 errors = {}", bindingResult);
            return (ResponseEntity<ItemUpdateDto>) bindingResult.getAllErrors();
        }
        log.info("성공 로직 실행");
        itemService.update(itemId, updateParam);
        return ResponseEntity.ok().body(updateParam);
    }
}