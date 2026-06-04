package com.example.demo.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.DTO.UserService;

@RestController
public class UserController {
    // private UserService service = new UserService();

    // public void printUser() {
    //     System.out.println(service.getUser());
    // }
    private UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping("/user")
    public String printUser() {
        System.out.println(service.getUser());
        return service.getUser();
    }
}
