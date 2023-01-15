package by.it_academy.kopytko.dao;

import by.it_academy.kopytko.dao.entity.Dish;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IDishDao extends JpaRepository<Dish, UUID> {


}
