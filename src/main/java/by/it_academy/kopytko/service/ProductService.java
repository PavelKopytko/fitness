package by.it_academy.kopytko.service;

import by.it_academy.kopytko.controller.exceptions.LockException;
import by.it_academy.kopytko.dao.IProductDao;
import by.it_academy.kopytko.dao.entity.*;
import by.it_academy.kopytko.service.api.IAuditService;
import by.it_academy.kopytko.service.api.IProductService;
import by.it_academy.kopytko.service.api.IUserService;
import by.it_academy.kopytko.service.dto.PageDto;
import by.it_academy.kopytko.service.dto.ProductForCUDto;
import by.it_academy.kopytko.service.dto.ProductOutDto;
import by.it_academy.kopytko.util.mappers.ProductMapper;
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
public class ProductService implements IProductService {

    private final static String EDITED = "Продукт уже отредактирован кем-то другим";
    private final static String CREATED = "Создан продукт";
    private final static String UPDATED = "Обновлен продукт";
    private final static String LOCK = "Запрещено редактировать";


    private final IProductDao productDao;
    private final IAuditService auditService;
    private final IUserService userService;
    private final ProductMapper productMapper;

    public ProductService(IProductDao productDao, IAuditService auditService, IUserService userService, ProductMapper productMapper) {
        this.productDao = productDao;
        this.auditService = auditService;
        this.userService = userService;
        this.productMapper = productMapper;
    }

    @Transactional
    public Product create(ProductForCUDto item) {

        validate(item);

        String mail = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = this.userService.getCurrentUser(mail);

        Product product = this.productMapper.dtoToEntityMapper(item, user);

        Product productOut = this.productDao.save(product);

        this.auditService.create(
                new Audit(CREATED, EEssenceType.PRODUCT, productOut.getUuid().toString()),
                user);

        return productOut;
    }

    @Override
    public Product read(UUID id) {
        Product productOut = this.productDao.findById(id).orElseThrow();
        return productOut;
    }

    @Override
    public PageDto<ProductOutDto> get(Pageable pageable) {
        Page<Product> items = this.productDao.findAll(pageable);
        return this.productMapper.mapper(items);
    }

    @Override
    @Transactional
    public Product update(UUID id, LocalDateTime dtUpdate, ProductForCUDto item) throws LockException {

        validate(item);

        Product readed = this.productDao.findById(id).orElseThrow();

        if (!readed.getDtUpdate().isEqual(dtUpdate)) {
            throw new OptimisticLockException(EDITED);
        }
        readed = this.productMapper.updateProductMapper(item, readed);

        String mail = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = this.userService.getCurrentUser(mail);

        if (readed.getUser().getUuid() != user.getUuid()) {
            if (user.getRole() != ERole.ROLE_ADMIN) {
                throw new LockException(LOCK);
            }
        }

        this.auditService.create(new Audit(UPDATED, EEssenceType.PRODUCT, readed.getUuid().toString()),
                user);

        return this.productDao.save(readed);
    }

    private void validate(ProductForCUDto item) {
        if (item == null) {
            throw new IllegalStateException("Вы не передали продукт");
        }
    }


}
