package ru.kata.spring.boot_security.demo.dao;


import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.User;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {
    @PersistenceContext
    private EntityManager em;

    @Override
    public void addUser(User user) {
        em.persist(user);
    }

    @Override
    public void deleteUser(Long id) {
        em.remove(em.find(User.class, id));
    }

    @Override
    public void changeUser(Long id, User newUser) {
        User user = em.find(User.class, id);
        user.setEmail(newUser.getEmail());
        user.setFirstName(newUser.getFirstName());
        user.setLastName(newUser.getLastName());
        user.setEncodedPass(newUser.getPass());
        user.setRoleList(newUser.getRoleList());
    }

    @Override
    public User findById(Long id) {
        return em.find(User.class, id);
    }

    @Override
    public User findByEmail(String email) {
        return em.createQuery("select users FROM User users where users.email = :email", User.class)
                .setParameter("email", email)
                .getSingleResult();
    }

    @Override
    public List<User> getAllUsers() {
        return em.createQuery("select users from User users", User.class).getResultList();
    }
}
