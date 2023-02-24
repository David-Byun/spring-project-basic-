package login.project.repository.item;

import lombok.Data;
import org.hibernate.validator.constraints.Range;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Validated
@Data
public class ItemUpdateDto {
    @NotBlank
    @NotNull
    private String itemName;

    @NotNull
    @NotBlank
    @Range(min = 100, max = 100000)
    private Integer price;

    @NotNull
    @NotBlank
    @Range(min = 10, max = 1000)
    private Integer quantity;

    public ItemUpdateDto() {
    }

    public ItemUpdateDto(String itemName, Integer price, Integer quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }
}
