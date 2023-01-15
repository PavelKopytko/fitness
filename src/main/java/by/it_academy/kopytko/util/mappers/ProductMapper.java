package by.it_academy.kopytko.util.mappers;

import by.it_academy.kopytko.dao.entity.Product;
import by.it_academy.kopytko.dao.entity.User;
import by.it_academy.kopytko.service.dto.PageDto;
import by.it_academy.kopytko.service.dto.ProductForCUDto;
import by.it_academy.kopytko.service.dto.ProductOutDto;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class ProductMapper {

    public ProductMapper() {
    }

    public Product dtoToEntityMapper(ProductForCUDto item, User user) {

        LocalDateTime time = LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS);
        UUID id = UUID.randomUUID();

        Product product = new Product();
        product.setUuid(id);
        product.setDtCreate(time);
        product.setDtUpdate(time);
        product.setTitle(item.getTitle());
        product.setCalories(item.getCalories());
        product.setProteins(item.getProteins());
        product.setFats(item.getFats());
        product.setCarbohydrates(item.getCarbohydrates());
        product.setWeight(item.getWeight());
        product.setUser(user);

        return product;
    }

    public Product updateProductMapper(ProductForCUDto item, Product readed) {
        readed.setTitle(item.getTitle());
        readed.setCalories(item.getCalories());
        readed.setProteins(item.getProteins());
        readed.setFats(item.getFats());
        readed.setCarbohydrates(item.getCarbohydrates());
        readed.setWeight(item.getWeight());
        return readed;
    }

    public PageDto<ProductOutDto> mapper(Page<Product> items) {

        PageDto<ProductOutDto> dto = new PageDto<>();
        dto.setNumber(items.getNumber());
        dto.setSize(items.getSize());
        dto.setTotalPages(items.getTotalPages());
        dto.setTotalElements((int) items.getTotalElements());
        dto.setFirst(items.isFirst());
        dto.setNumberOfElements(items.getNumberOfElements());
        dto.setLast(items.isLast());

//        List<ProductOutDto> outs = entityToDtoMapper(items.getContent());

        List<ProductOutDto> outs = new ArrayList<>();
//
        for (Product product : items.getContent()) {
            ProductOutDto out = entityToDtoMapper(product);

            outs.add(out);
        }
        dto.setContent(outs);

        return dto;
    }

    public ProductOutDto entityToDtoMapper(Product product) {

        ProductOutDto out = new ProductOutDto();
        out.setUuid(product.getUuid());
        out.setDtCreate(product.getDtCreate());
        out.setDtUpdate(product.getDtUpdate());
        out.setTitle(product.getTitle());
        out.setCalories(product.getCalories());
        out.setProteins(product.getProteins());
        out.setFats(product.getFats());
        out.setCarbohydrates(product.getCarbohydrates());
        out.setWeight(product.getWeight());

        return out;
    }

}
