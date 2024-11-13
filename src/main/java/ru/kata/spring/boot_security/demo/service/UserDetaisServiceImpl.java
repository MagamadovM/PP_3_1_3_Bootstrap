package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.dao.UserDao;
import ru.kata.spring.boot_security.demo.models.User;

@Service
public class UserDetaisServiceImpl implements UserDetailsService {
    private final UserDao userDao;

    @Autowired
    public UserDetaisServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }
    @Override
    public User loadUserByUsername(String login) {
        return userDao.findUserByLogin(login);
    }
}
