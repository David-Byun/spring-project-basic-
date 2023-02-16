package login.project.service.item;

import login.project.domain.Item;
import login.project.repository.item.ItemUpdateDto;

import java.util.List;
import java.util.Optional;

public interface ItemService {

    Item save(Item item);

    void update(Long itemId, ItemUpdateDto updateParam);
    Optional<Item> findById(Long id);

    List<Item> findItems();

}
