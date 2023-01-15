package by.it_academy.kopytko.service;

import by.it_academy.kopytko.dao.IDishDao;
import by.it_academy.kopytko.dao.IIngredientDao;
import by.it_academy.kopytko.dao.entity.Dish;
import by.it_academy.kopytko.dao.entity.Ingredient;
import by.it_academy.kopytko.dao.entity.Product;
import by.it_academy.kopytko.service.api.IDishService;
import by.it_academy.kopytko.service.api.IIngredientService;
import by.it_academy.kopytko.service.api.IProductService;
import by.it_academy.kopytko.service.dto.DishDto;
import by.it_academy.kopytko.service.dto.IngredientDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class IngredientService implements IIngredientService {

    private final IIngredientDao dao;

    public IngredientService(IIngredientDao dao) {
        this.dao = dao;
    }

    @Override
    public Ingredient read(UUID id) {

        Ingredient ingredientOut = this.dao.findById(id).orElseThrow();

        return ingredientOut;
    }

}
