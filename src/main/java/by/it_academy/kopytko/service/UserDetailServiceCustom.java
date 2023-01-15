package by.it_academy.kopytko.service;

import by.it_academy.kopytko.dao.IUserDao;
import by.it_academy.kopytko.dao.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailServiceCustom implements UserDetailsService {

    private final IUserDao userDao;

    public UserDetailServiceCustom(IUserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException {
        User user = this.userDao.findUserByMail(mail);
        List<GrantedAuthority> authorityList = new ArrayList<>();
        authorityList.add(new SimpleGrantedAuthority(user.getRole().name()));
        org.springframework.security.core.userdetails.User user2 =
                new org.springframework.security.core.userdetails.User(user.getMail(), user.getPassword(), authorityList);
        return user2;
    }
}
