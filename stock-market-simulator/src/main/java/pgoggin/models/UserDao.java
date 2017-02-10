package pgoggin.models;

import java.util.List;

/**
 * Created by patrickgoggin on 2/4/17.
 */
public interface UserDao {
    public User createUser(User user);
    public void deleteUser(User user);
    public List<User> getAll();
    public User getByEmail(String email);
    public User getCurrentUser();
    public User getById(long id);
    public void updateUser(User user);
    public User login(String email, String password);
    public User logout();
}
