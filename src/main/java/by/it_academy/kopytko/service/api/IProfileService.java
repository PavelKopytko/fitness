package by.it_academy.kopytko.service.api;

import by.it_academy.kopytko.dao.entity.Profile;
import by.it_academy.kopytko.service.dto.ProfileForCU;
import by.it_academy.kopytko.service.dto.ProfileForOut;

import java.util.UUID;

public interface IProfileService {

    Profile create(ProfileForCU item);

    ProfileForOut read(UUID uuid);

    Profile getByUuid(UUID uuid);

}
