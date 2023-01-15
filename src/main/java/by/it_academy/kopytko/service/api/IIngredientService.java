package by.it_academy.kopytko.service.api;

import by.it_academy.kopytko.dao.entity.Ingredient;

import java.util.UUID;

public interface IIngredientService {
    Ingredient read(UUID id);


}
