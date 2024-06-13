package com.example.testDBService;

import com.example.entity.Role;
import com.example.entity.User;
import com.example.repository.RoleRepository;
import com.example.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class TestUsers {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleRepository roleRepository;

    @Test
    public void testAllUsers(){
        Role role = new Role();
        role.setName("temp");
        roleRepository.save(role);
        System.out.println(roleRepository.findAll());

        User  user = new User();
        user.setUserName("ddd");
        user.setPassword("12345");
        user.setEmail("ddd@gmail.com");
        userService.saveUser(new User());
        System.out.println(userService.getAllUsers());
    }

    @Test
    public void testGetUserByEmail(){
        System.out.println(userService.getUserByEmail("dasha@gmail.com"));
    }
}
