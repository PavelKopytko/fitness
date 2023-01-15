package by.it_academy.kopytko.service.dto;

import by.it_academy.kopytko.dao.entity.Product;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.UUID;


public class IngredientDtoOut {

    private ProductOutDto product;
    private int weight;

    public IngredientDtoOut() {
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
}
