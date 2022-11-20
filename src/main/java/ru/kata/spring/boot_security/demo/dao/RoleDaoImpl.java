package ru.kata.spring.boot_security.demo.dao;

import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.Role;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class RoleDaoImpl implements RoleDao {
    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Role> getAllRoles() {
        return em.createQuery("Select role from Role role", Role.class).getResultList();
    }

    @Override
    public Role getByRole(String role) {
        return em.createQuery("select role from Role role where role.roleName = :roleName", Role.class)
                .setParameter("roleName", role)
                .getSingleResult();
    }

    @Override
    public Role getById(Long id) {
        return em.find(Role.class, id);
    }
}
