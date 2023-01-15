package by.it_academy.kopytko.service.dto;

import by.it_academy.kopytko.dao.entity.EActivityType;
import by.it_academy.kopytko.dao.entity.EProfileSex;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.Date;

public class ProfileForCU {
    @Min(value = 100, message = "Введено недопустимое значение роста")
    private int height;
    @Min(value = 100, message = "Введено недопустимое значение веса")
    private double weight;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dtBirthday;
    @Min(value = 0, message = "Введено неверное значение веса")
    private double target;
    private EActivityType activityType;

    private EProfileSex sex;

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
}
