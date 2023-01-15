package by.it_academy.kopytko.dao;

import by.it_academy.kopytko.dao.entity.Ingredient;
import by.it_academy.kopytko.dao.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IIngredientDao extends JpaRepository<Ingredient, UUID> {
}
