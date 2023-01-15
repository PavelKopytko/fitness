package by.it_academy.kopytko.dao;

import by.it_academy.kopytko.dao.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IUserDao extends JpaRepository<User, UUID> {

    User findUserByMail(String mail);

    User findUserByUuid(String id);

}
