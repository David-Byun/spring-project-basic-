package login.project.domain;

import lombok.Data;
import org.hibernate.validator.constraints.Range;
import org.springframework.validation.annotation.Validated;

@Data
@Validated
public class Item {


    private Long id;

    private String itemName;

    @Range(min = 100, max = 10000000)
    private Integer price;

    @Range(min = 1, max = 10000)
    private Integer quantity;

    public Item() {

    }

    public Item(String itemName, Integer price, Integer quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }
}
