package by.it_academy.kopytko.service;

import by.it_academy.kopytko.controller.exceptions.LockException;
import by.it_academy.kopytko.dao.IDishDao;
import by.it_academy.kopytko.dao.entity.*;
import by.it_academy.kopytko.service.api.*;
import by.it_academy.kopytko.service.dto.DishDto;
import by.it_academy.kopytko.service.dto.DishDtoOut;
import by.it_academy.kopytko.service.dto.PageDto;
import by.it_academy.kopytko.util.mappers.DishMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.OptimisticLockException;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class DishService implements IDishService {
    private final static String EDITED = "Блюдо уже отредактирован кем-то другим";
    private final static String CREATED = "Создано блюдо";
    private final static String UPDATED = "Обновлено блюдо";
    private final static String LOCK = "Запрещено редактировать";

    private final IDishDao dao;
    private final IProductService productService;
    private final IUserService userService;
    private final IAuditService auditService;
    private final DishMapper dishMapper;
    private final IIngredientService iIngredientService;

    public DishService(IDishDao dao, IProductService productService, IUserService userService, IAuditService auditService, DishMapper dishMapper, IIngredientService iIngredientService) {
        this.dao = dao;
        this.productService = productService;
        this.userService = userService;
        this.auditService = auditService;
        this.dishMapper = dishMapper;
        this.iIngredientService = iIngredientService;
    }

    @Override
    @Transactional
    public Dish create(DishDto item) {

        validate(item);

        String mail = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = this.userService.getCurrentUser(mail);

        Dish dish = this.dishMapper.dtoToEntityMapper(item, productService, user);

        Dish dishOut = this.dao.save(dish);

        this.auditService.create(
                new Audit(CREATED, EEssenceType.RECIPE, dishOut.getUuid().toString()),
                user);

        return dishOut;
    }

    @Override
    public Dish read(UUID id) {

        Dish dish = this.dao.findById(id).orElseThrow();
        return dish;
    }

    @Override
    public PageDto<DishDtoOut> get(Pageable pageable) {

        Page<Dish> items = this.dao.findAll(pageable);

        return this.dishMapper.mapper(items);
    }

    @Override
    @Transactional
    public Dish update(UUID id, LocalDateTime dtUpdate, DishDto item) throws LockException {

        validate(item);

        Dish readed = this.dao.findById(id).orElseThrow();

        if (!readed.getDtUpdate().isEqual(dtUpdate)) {
            throw new OptimisticLockException(EDITED);
        }
        readed = this.dishMapper.updateDishtMapper(item, readed, productService);

        String mail = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = this.userService.getCurrentUser(mail);

        if (readed.getUser().getUuid() != user.getUuid()) {
            throw new LockException(LOCK);
        }

        this.auditService.create(
                new Audit(UPDATED, EEssenceType.RECIPE, readed.getUuid().toString()),
                user);

        return this.dao.save(readed);
    }

    private void validate(DishDto item) {
        if (item == null) {
            throw new IllegalStateException("Вы не передали Блюдо");
        }
        if (item.getComposition() == null) {
            throw new IllegalStateException("Вы не передали список ингредиентов");
        }
    }
}
