package by.it_academy.kopytko.util.mappers;

import by.it_academy.kopytko.dao.entity.Dish;
import by.it_academy.kopytko.dao.entity.Ingredient;
import by.it_academy.kopytko.dao.entity.Product;
import by.it_academy.kopytko.dao.entity.User;
import by.it_academy.kopytko.service.api.IProductService;
import by.it_academy.kopytko.service.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class DishMapper {

    private final ProductMapper productMapper;

    public DishMapper(ProductMapper productMapper) {
        this.productMapper = productMapper;
    }

    public Dish dtoToEntityMapper(DishDto item, IProductService productService, User user) {

        LocalDateTime time = LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS);
        UUID id = UUID.randomUUID();

        List<Ingredient> ingredients = new ArrayList<>();
        for (IngredientDto dto : item.getComposition()) {
            Ingredient ingredient = new Ingredient();
            ingredient.setUuid(UUID.randomUUID());
            ingredient.setProduct(productService.read(dto.getProduct().getUuid()));
            ingredients.add(ingredient);
            ingredient.setWeight(dto.getWeight());
        }
        Dish dish = new Dish();
        dish.setUuid(id);
        dish.setDtCreate(time);
        dish.setDtUpdate(time);
        dish.setTitle(item.getTitle());
        dish.setComposition(ingredients);
        dish.setUser(user);

        return dish;
    }

    public Dish updateDishtMapper(DishDto item, Dish readed, IProductService productService) {
        List<Ingredient> ingredients = new ArrayList<>();

        for (IngredientDto dto : item.getComposition()) {
            UUID uuid = UUID.randomUUID();

            Product product = productService.read(dto.getProduct().getUuid());
            Ingredient ingredient = new Ingredient();
            ingredient.setUuid(uuid);
            ingredient.setProduct(product);
            ingredient.setWeight(dto.getWeight());
            ingredients.add(ingredient);
        }
        readed.setTitle(item.getTitle());
        readed.setComposition(ingredients);

        return readed;
    }

    public PageDto<DishDtoOut> mapper(Page<Dish> items) {

        PageDto<DishDtoOut> dto = new PageDto<>();
        dto.setNumber(items.getNumber());
        dto.setSize(items.getSize());
        dto.setTotalPages(items.getTotalPages());
        dto.setTotalElements((int) items.getTotalElements());
        dto.setFirst(items.isFirst());
        dto.setNumberOfElements(items.getNumberOfElements());
        dto.setLast(items.isLast());

        List<DishDtoOut> dishes = new ArrayList<>();
        for (Dish dish : items.getContent()) {
            DishDtoOut dishDto = entityToDtoMapper(dish);
            dishes.add(dishDto);
        }

        dto.setContent(dishes);
        return dto;
    }
    public DishDtoOut entityToDtoMapper(Dish dish){
        DishDtoOut dishDto = new DishDtoOut();
        dishDto.setUuid(dish.getUuid());
        dishDto.setDtCreate(dish.getDtCreate());
        dishDto.setDtUpdate(dish.getDtUpdate());
        dishDto.setTitle(dish.getTitle());
        List<IngredientDtoOut> list = new ArrayList<>();
        for (Ingredient ing : dish.getComposition()) {
            IngredientDtoOut ingredientDtoOut = new IngredientDtoOut();
            ingredientDtoOut.setProduct(this.productMapper.entityToDtoMapper(ing.getProduct()));
            ingredientDtoOut.setWeight(ing.getWeight());
            list.add(ingredientDtoOut);
        }
        dishDto.setComposition(list);
        return dishDto;
    }

}
