package by.it_academy.kopytko.service.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

public class DishDto {
    @NotEmpty(message = "Не введено имя")
    private String title;
    @NotNull(message = " Не заполнен список продуктов")
    private List<IngredientDto> composition;

    public DishDto() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<IngredientDto> getComposition() {
        return composition;
    }

    public void setComposition(List<IngredientDto> composition) {
        this.composition = composition;
    }
}
