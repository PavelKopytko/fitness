package by.it_academy.kopytko.service.dto;

import javax.validation.constraints.NotEmpty;
import java.util.UUID;

public class BaseProduct {

    @NotEmpty(message = "Не указали uuid продукта")
    UUID uuid;

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }
}
