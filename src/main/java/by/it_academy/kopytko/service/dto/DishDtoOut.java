package by.it_academy.kopytko.service.dto;

import by.it_academy.kopytko.dao.entity.Ingredient;
import by.it_academy.kopytko.dao.entity.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


public class DishDtoOut {


    private UUID uuid;

    private LocalDateTime dtCreate;

    private LocalDateTime dtUpdate;
    private String title;

    private List<IngredientDtoOut> composition;


    public DishDtoOut() {
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<IngredientDtoOut> getComposition() {
        return composition;
    }

    public void setComposition(List<IngredientDtoOut> composition) {
        this.composition = composition;
    }
}
