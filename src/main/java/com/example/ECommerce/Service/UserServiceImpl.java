package com.example.ECommerce.Service;

import com.example.ECommerce.Model.User;
import com.example.ECommerce.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Method to authenticate the user
    public User authenticate(String username, String password) {
        // Check if the user exists with the given username and password
        // This is a basic example, you should implement password encryption in a real application
        return userRepository.findByUsernameAndPassword(username, password);
    }

    // Method to check if a username is already taken
    public boolean isUsernameTaken(String username) {
        return userRepository.existsByUsername(username);
    }

    // Method to check if an email is already taken
    public User findUserByUsername(String email) {
        return userRepository.findByUsername(email);
    }

    public boolean isEmailTaken(String email) {
        return userRepository.existsByEmail(email);
    }

    // Method to save a new user
    public void saveUser(User user) {
        user.setRole("ROLE_USER");
        user.setIsEnable(true);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    public User getUserByUsername(String name){
        return userRepository.getUsersByUsername(name);
    }

    @Override
    public void createAdminUser(){
        User adminUser = new User();
        adminUser.setUsername("admin");
        adminUser.setPassword(passwordEncoder.encode("admin"));
        adminUser.setFullName("Admin");
        adminUser.setEmail("admin@example.com");
        adminUser.setPhone("1234567690");
        adminUser.setAddress("Admin Address");
        adminUser.setRole("ROLE_ADMIN");
        adminUser.setIsEnable(true);
        userRepository.save(adminUser);
    }

    @Override
    public List<User> getUsersByRole(String role){
        return userRepository.getUsersByRole(role);
    }

    @Override
    public Boolean updateUserStatus(Long id, boolean status) {
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent()) {
            User u = user.get();
            u.setIsEnable(status);
            userRepository.save(u);
            return true;
        }
        return false;
    }

    @Override
    public void saveAdmin(User user){
        if (user.getId() == null){
            user.setRole("ROLE_ADMIN");
            user.setIsEnable(true);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
        }
    }
}
