package com.example.ECommerce.Service;

import com.example.ECommerce.Model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    User authenticate(String username, String password);

    boolean isUsernameTaken(String username);

    User findUserByUsername(String username);

    boolean isEmailTaken(String email);
    void saveUser(User user);
    User getUserByUsername(String name);
    void createAdminUser();
    List<User> getUsersByRole(String role);

    Boolean updateUserStatus(Long id, boolean status);
    void saveAdmin(User user);
}
