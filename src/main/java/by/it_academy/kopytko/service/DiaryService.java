package by.it_academy.kopytko.service;

import by.it_academy.kopytko.controller.exceptions.LockException;
import by.it_academy.kopytko.dao.IDiaryDao;
import by.it_academy.kopytko.dao.entity.*;
import by.it_academy.kopytko.service.api.*;
import by.it_academy.kopytko.service.dto.DiaryDto;
import by.it_academy.kopytko.service.dto.PageDto;
import by.it_academy.kopytko.util.mappers.DiaryMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class DiaryService implements IDiaryService {

    private final static String CREATED = "Создана запись дневника";
    private final static String LOCK = "Запрещено корректировать";

    private final IDiaryDao dao;
    private final IProductService productService;
    private final IDishService dishService;
    private final IProfileService profileService;
    private final IUserService userService;
    private final IAuditService auditService;
    private final DiaryMapper diaryMapper;

    public DiaryService(IDiaryDao dao, IProductService productService, IDishService dishService, IProfileService profileService, IUserService userService, IAuditService auditService, DiaryMapper diaryMapper) {
        this.dao = dao;
        this.productService = productService;
        this.dishService = dishService;
        this.profileService = profileService;
        this.userService = userService;
        this.auditService = auditService;
        this.diaryMapper = diaryMapper;
    }

    @Override
    @Transactional
    public Diary create(DiaryDto item, UUID profileUuid) throws LockException {

        validate(item);

        String mail = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = this.userService.getCurrentUser(mail);

        Profile currentProfile = this.profileService.getByUuid(profileUuid);

        if(user.getUuid() != currentProfile.getUser().getUuid()){
            throw new LockException(LOCK);
        }

        Diary diary = this.diaryMapper.dtoToEntityMapper(item, currentProfile, productService, dishService);

        Diary diaryOut = this.dao.save(diary);

        this.auditService.create(
                new Audit(CREATED, EEssenceType.JOURNAL_FOOD, diaryOut.getUuid().toString()),
                user);

        return diaryOut;
    }

    @Override
    public Diary read(UUID id) {

        Diary diaryOut = this.dao.findById(id).orElseThrow();
        return diaryOut;
    }

    @Override
    public PageDto get(Pageable pageable, UUID uuid) {
        Page<Diary> items = this.dao.findAllByProfileUuid(pageable, uuid);
        return this.diaryMapper.mapper(items);
    }


    private void validate(DiaryDto item) {
        if (item == null) {
            throw new IllegalStateException("Не передано значение DiaryDto");
        }
        if (item.getRecipe() == null && item.getProduct() == null) {
            throw new IllegalArgumentException("Введите dish или product");
        }
        if (item.getRecipe() != null && item.getProduct() != null) {
            throw new IllegalArgumentException("Введите dish или product");
        }
    }

}
