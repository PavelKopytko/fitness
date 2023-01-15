package by.it_academy.kopytko.service.dto;

import by.it_academy.kopytko.dao.entity.Diary;
import by.it_academy.kopytko.dao.entity.EActivityType;
import by.it_academy.kopytko.dao.entity.EProfileSex;
import by.it_academy.kopytko.dao.entity.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;


public class ProfileForOut {

    private UUID uuid;
    private LocalDateTime dtCreate;
    private LocalDateTime dtUpdate;
    private int height;
    private double weight;
    private Date dtBirthday;
    private double target;
    private EActivityType activityType;
    private EProfileSex sex;
    private UserDtoForProfile user;

    public ProfileForOut() {
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public LocalDateTime getDtCreate() {
        return dtCreate;
    }

    public void setDtCreate(LocalDateTime dtCreate) {
        this.dtCreate = dtCreate;
    }

    public LocalDateTime getDtUpdate() {
        return dtUpdate;
    }

    public void setDtUpdate(LocalDateTime dtUpdate) {
        this.dtUpdate = dtUpdate;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public Date getDtBirthday() {
        return dtBirthday;
    }

    public void setDtBirthday(Date dtBirthday) {
        this.dtBirthday = dtBirthday;
    }

    public double getTarget() {
        return target;
    }

    public void setTarget(double target) {
        this.target = target;
    }

    public EActivityType getActivityType() {
        return activityType;
    }

    public void setActivityType(EActivityType activityType) {
        this.activityType = activityType;
    }

    public EProfileSex getSex() {
        return sex;
    }

    public void setSex(EProfileSex sex) {
        this.sex = sex;
    }

    public UserDtoForProfile getUser() {
        return user;
    }

    public void setUser(UserDtoForProfile user) {
        this.user = user;
    }
}
