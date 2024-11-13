package ru.kata.spring.boot_security.demo.dao;

import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.models.Role;

import ru.kata.spring.boot_security.demo.service.UserService;
import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Repository
public class RoleDaoImpl implements RoleDao {

    private final UserService userService;

    @PersistenceContext
    private EntityManager entityManager;

    public RoleDaoImpl(UserService userService) {
        this.userService = userService;
    }

    @PostConstruct
    public void init(){
        userService.addFirstAdmin();
    }


    @Override
    public Role findRole(Long id) {
        System.out.println(id);
        return entityManager.find(Role.class, id);
    }

    @Override
    public Set<Role> rolesSet() {
        return new HashSet<>(entityManager.createQuery( "SELECT r FROM Role r" ).getResultList());
    }

    @Override
    public void add(Role role) {
        entityManager.merge(role);
    }

    @Override
    public Role findByName(String name) {
        return entityManager.createQuery("SELECT r FROM Role r WHERE r.role = :name", Role.class)
                .setParameter("name", name)
                .getSingleResult();
    }

    public Role save(Role roleAdmin) {
        return entityManager.merge(roleAdmin);
    }
}
