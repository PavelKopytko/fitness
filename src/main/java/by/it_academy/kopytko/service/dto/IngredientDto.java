package by.it_academy.kopytko.service.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.util.UUID;

public class IngredientDto {
    @NotEmpty(message = "Не указали продукт")
    private BaseProduct product;
    @Min(value = 0, message = "Введено неверное значение массы")
    private int weight;

    public IngredientDto() {
    }

    public BaseProduct getProduct() {
        return product;
    }

    public void setProduct(BaseProduct product) {
        this.product = product;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}
