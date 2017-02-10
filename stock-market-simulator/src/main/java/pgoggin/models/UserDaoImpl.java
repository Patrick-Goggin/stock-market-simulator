package pgoggin.models;

import org.hibernate.Session;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.persistence.PersistenceContext;

import javax.transaction.Transactional;
import javax.persistence.EntityManager;
import java.util.*;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

/**
 * Created by patrickgoggin on 2/4/17.
 */
@Repository
@Transactional
@Component
public class UserDaoImpl implements UserDao{

    @PersistenceContext
    private EntityManager entityManager;

    private User currentUser;

    private Session session;

    @Override
    public User getCurrentUser(){
        return currentUser;
    }

    @Override
    public User createUser(User user){
        entityManager.persist(user);
        List<User> list = entityManager.createQuery("from User").setMaxResults(1).getResultList();
        user = list.get(0);
        return user;
    }

    @Override
    public void deleteUser(User user){
        if (entityManager.contains(user))
            entityManager.remove(user);
        else
            entityManager.remove(entityManager.merge(user));
        return;
    }

    @Override
    public List<User> getAll(){
        return entityManager.createQuery("from User").getResultList();
    }

    @Override
    public User getByEmail(String email){
        List<User> list = entityManager.createQuery("from Quote where email = :email").setParameter("email", email).getResultList();
        User user = list.get(0);
        return user;
    }

    @Override
    public User getById(long id){
        return entityManager.find(User.class, id);
    }

    @Override
    public void updateUser(User user){
        entityManager.merge(user);
    }

    @Override
    public User login(String email, String password){
        User user = new User();
        List<User>list = entityManager.createQuery("from User where email = :email").setParameter("email", email).getResultList();
        if(list.get(0)!= null){
            if(list.get(0).getPassword().equals(password)){
                currentUser = list.get(0);
                user = new User(currentUser);
                user.setPassword("");
            }
        }
        else{user.setPassword("failed");}
        return user;
    }

    public User logout(){
        User user = new User();
        currentUser = user;
        return user;
    }
}
