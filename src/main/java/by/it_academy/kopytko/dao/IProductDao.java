package by.it_academy.kopytko.dao;

import by.it_academy.kopytko.dao.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IProductDao extends JpaRepository<Product, UUID> {

    Page<Product> findAll(Pageable pageable);
}
