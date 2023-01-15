package by.it_academy.kopytko.service.dto;

import javax.validation.constraints.Min;
import java.util.UUID;

public class DiaryDto {
    @Min(value = 0, message = "Введено неверное значение времени")
    private long dtSupply;
    private UUID recipe;
    private UUID product;
    @Min(value = 0, message = "Введено неверное значение массы")
    private int weight;

    public DiaryDto() {
    }

    public long getDtSupply() {
        return dtSupply;
    }

    public void setDtSupply(long dtSupply) {
        this.dtSupply = dtSupply;
    }

    public UUID getRecipe() {
        return recipe;
    }

    public void setRecipe(UUID recipe) {
        this.recipe = recipe;
    }

    public UUID getProduct() {
        return product;
    }

    public void setProduct(UUID product) {
        this.product = product;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}
