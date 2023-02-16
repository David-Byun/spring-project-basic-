package login.project.repository.item;

import lombok.Data;

@Data
public class ItemSearchDto {
    private String itemName;
    private Integer maxPrice;

    public ItemSearchDto() {
    }

    public ItemSearchDto(String itemName, Integer maxPrice) {
        this.itemName = itemName;
        this.maxPrice = maxPrice;
    }
}
