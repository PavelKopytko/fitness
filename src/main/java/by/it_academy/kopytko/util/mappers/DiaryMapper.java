package by.it_academy.kopytko.util.mappers;

import by.it_academy.kopytko.dao.entity.*;
import by.it_academy.kopytko.service.api.IDishService;
import by.it_academy.kopytko.service.api.IProductService;
import by.it_academy.kopytko.service.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class DiaryMapper {

    private final DishMapper dishMapper;
    private final ProductMapper productMapper;

    public DiaryMapper(DishMapper dishMapper, ProductMapper productMapper) {
        this.dishMapper = dishMapper;
        this.productMapper = productMapper;
    }

    public Diary dtoToEntityMapper(DiaryDto item, Profile profile, IProductService productService, IDishService dishService) {

        LocalDateTime time = LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS);
        UUID id = UUID.randomUUID();

        Diary diary = new Diary();
        diary.setUuid(id);
        diary.setDtCreate(time);
        diary.setDtUpdate(time);
        diary.setWeight(item.getWeight());
        LocalDateTime dtSupply = LocalDateTime.ofInstant(
                Instant.ofEpochMilli(item.getDtSupply()),
                ZoneId.of("UTC"));
        diary.setDtSupply(dtSupply);

        if (item.getProduct() != null || item.getRecipe() == null) {
            Product product = productService.read(item.getProduct());
            diary.setProduct(product);
        } else {
            Dish dish = dishService.read(item.getRecipe());
            diary.setDish(dish);
        }
        diary.setProfile(profile);

        return diary;
    }


    public PageDto<DiaryDtoOut> mapper(Page<Diary> items) {

        PageDto<DiaryDtoOut> dto = new PageDto<>();
        dto.setNumber(items.getNumber());
        dto.setSize(items.getSize());
        dto.setTotalPages(items.getTotalPages());
        dto.setTotalElements((int) items.getTotalElements());
        dto.setFirst(items.isFirst());
        dto.setNumberOfElements(items.getNumberOfElements());
        dto.setLast(items.isLast());

        List<DiaryDtoOut> list = new ArrayList<>();
        for (Diary diary : items.getContent()) {
            DiaryDtoOut out = new DiaryDtoOut();
            out.setUuid(diary.getUuid());
            out.setDtCreate(diary.getDtCreate());
            out.setDtUpdate(diary.getDtUpdate());
            if (diary.getDish() != null) {
                out.setRecipe(this.dishMapper.entityToDtoMapper(diary.getDish()));
            }
            if (diary.getProduct() != null) {
                out.setProduct(this.productMapper.entityToDtoMapper(diary.getProduct()));
            }
            out.setWeight(diary.getWeight());
            out.setDtSupply(diary.getDtSupply());
            list.add(out);
        }

        dto.setContent(list);
        return dto;
    }

}
