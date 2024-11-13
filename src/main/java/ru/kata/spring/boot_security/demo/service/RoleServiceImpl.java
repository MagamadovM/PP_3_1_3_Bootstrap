package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.RoleDao;
import ru.kata.spring.boot_security.demo.models.Role;
import java.util.Set;

@Service
public class RoleServiceImpl implements RoleService{

    private final RoleDao roleDao;

    public RoleServiceImpl(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Override
    @Transactional(readOnly = true)
    public Role findRole(Long id) {
        return roleDao.findRole(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Set<Role> rolesSet() {
        return roleDao.rolesSet();
    }

    @Override
    @Transactional(readOnly = true)
    public Role findRoleByName(String name) {
        return roleDao.findByName(name).orElseThrow();
    }

    @Transactional
    @Override
    public void add(Role role) {
        roleDao.add(role);
    }

}
