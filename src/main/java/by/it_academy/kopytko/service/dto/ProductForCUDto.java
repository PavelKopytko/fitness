package by.it_academy.kopytko.service.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

public class ProductForCUDto {

    @NotEmpty(message = "Не введено имя")
    private String title;
    @Min(value = 0, message = "Введено неверное значение калорий")
    private int calories;
    @Min(value = 0, message = "Введено неверное значение белков")
    private double proteins;
    @Min(value = 0, message = "Введено неверное значение жиров")
    private double fats;
    @Min(value = 0, message = "Введено неверное значение углеводов")
    private double carbohydrates;
    @Min(value = 0, message = "Введено неверное значение массы")
    private int weight;

    public ProductForCUDto() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public double getProteins() {
        return proteins;
    }

    public void setProteins(double proteins) {
        this.proteins = proteins;
    }

    public double getFats() {
        return fats;
    }

    public void setFats(double fats) {
        this.fats = fats;
    }

    public double getCarbohydrates() {
        return carbohydrates;
    }

    public void setCarbohydrates(double carbohydrates) {
        this.carbohydrates = carbohydrates;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}
