package by.it_academy.kopytko.dao;

import by.it_academy.kopytko.dao.entity.Profile;
import by.it_academy.kopytko.dao.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface IProfileDao extends JpaRepository<Profile, UUID> {

    Optional<Profile> findByUuid(UUID uuid);


}
