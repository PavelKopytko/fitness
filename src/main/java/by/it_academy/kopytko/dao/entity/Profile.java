package by.it_academy.kopytko.dao.entity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
public class Profile {
    @Id
    private UUID uuid;

    @Column(name = "dt_create")
    private LocalDateTime dtCreate;

    @Version
    @Column(name = "dt_update")
    private LocalDateTime dtUpdate;

    private int height;

    private double weight;

    @Column(name = "dt_birthday")
    private Date dtBirthday;

    private double target;

    @Enumerated(value = EnumType.STRING)
    private EActivityType activityType;

    @Enumerated(value = EnumType.STRING)
    private EProfileSex sex;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

//    @OneToMany
//    @JoinColumn(name = "profile_id")
//    List<Diary> diaryList;

    public Profile() {
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

//    public List<Diary> getDiaryList() {
//        return diaryList;
//    }
//
//    public void setDiaryList(List<Diary> diaryList) {
//        this.diaryList = diaryList;
//    }
}
