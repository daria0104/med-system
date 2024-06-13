package com.example.service;

import com.example.entity.Role;
import com.example.entity.User;
import com.example.repository.RoleRepository;
import com.example.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements IUserService
{
    private final UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public boolean existsByUsernameOrEmail(String username, String email) {
        System.out.println("existsByUsernameOrEmail");
        return userRepository.existsByUserNameOrEmail(username, email);
    }

    @Override
    public User saveUser(User toSave)
    {
        System.out.println("saved new user");

        User user = new User();
        user.setUserName(toSave.getUserName());
        user.setEmail(toSave.getEmail());
        user.setPassword(toSave.getPassword());//passwordEncoder.encode(toSave.getPassword()));

//        Role role = roleRepository.findByName("USER");
//        if(user.getUserName().contains("admin"))
//            role = roleRepository.findByName("ADMIN");
//        if(role == null){
//            role = checkRoleExist("USER");
//        }
//        user.setRoles(List.of(role));
        return userRepository.save(user);
    }

    private Role checkRoleExist(String roleName){
        Role role = new Role();
        role.setName(roleName);
        return roleRepository.save(role);
    }

    @Override
    public User getUserByEmail(String email) {
        System.out.println("getUserByEmail");

        return userRepository.findByEmail(email);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}