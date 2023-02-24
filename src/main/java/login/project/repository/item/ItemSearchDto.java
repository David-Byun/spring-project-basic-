package login.project.repository.item;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class ItemSearchDto {
    @NotBlank
    @NotNull
    private String itemName;

    @NotNull
    @NotBlank
    @Range(min=100, max=1000000)
    private Integer maxPrice;

    public ItemSearchDto() {
    }

    public ItemSearchDto(String itemName, Integer maxPrice) {
        this.itemName = itemName;
        this.maxPrice = maxPrice;
    }
}
