package by.it_academy.kopytko.dao.entity;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
//@Table
public class Diary {

    @Id
    private UUID uuid;
    @Column(name = "dt_create")
    private LocalDateTime dtCreate;
    @Version
    @DateTimeFormat
    @Column(name = "dt_update")
    private LocalDateTime dtUpdate;
    @ManyToOne
    @JoinColumn(name = "dish_id", referencedColumnName = "uuid")
    private Dish dish;
    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "uuid")
    private Product product;
    private int weight;
    @DateTimeFormat
    @Column(name = "dt_supply")
    private LocalDateTime dtSupply;
    @ManyToOne
    @JoinColumn(name = "profile_uuid", referencedColumnName = "uuid")
    private Profile profile;

    public Diary() {
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

    public Dish getDish() {
        return dish;
    }

    public void setDish(Dish dish) {
        this.dish = dish;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public LocalDateTime getDtSupply() {
        return dtSupply;
    }
    public void setDtSupply(LocalDateTime dtSupply) {
        this.dtSupply = dtSupply;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }
}
