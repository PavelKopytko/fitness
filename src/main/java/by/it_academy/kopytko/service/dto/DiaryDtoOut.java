package by.it_academy.kopytko.service.dto;

import by.it_academy.kopytko.dao.entity.Dish;
import by.it_academy.kopytko.dao.entity.Product;
import by.it_academy.kopytko.dao.entity.Profile;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;


public class DiaryDtoOut {


    private UUID uuid;

    private LocalDateTime dtCreate;

    private LocalDateTime dtUpdate;

    private DishDtoOut recipe;
    private ProductOutDto product;
    private int weight;
    private LocalDateTime dtSupply;

    public DiaryDtoOut() {
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public LocalDateTime getDtCreate() {
        return dtCreate;
    }

    public void setDtCreate(LocalDateTime dtCreate) {
        this.dtCreate = dtCreate;
    }

    public LocalDateTime getDtUpdate() {
        return dtUpdate;
    }

    public void setDtUpdate(LocalDateTime dtUpdate) {
        this.dtUpdate = dtUpdate;
    }

    public DishDtoOut getRecipe() {
        return recipe;
    }

    public void setRecipe(DishDtoOut recipe) {
        this.recipe = recipe;
    }

    public ProductOutDto getProduct() {
        return product;
    }

    public void setProduct(ProductOutDto product) {
        this.product = product;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public LocalDateTime getDtSupply() {
        return dtSupply;
    }

    public void setDtSupply(LocalDateTime dtSupply) {
        this.dtSupply = dtSupply;
    }
}
