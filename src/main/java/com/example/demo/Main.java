package com.example.demo;

import com.example.demo.Controller.UserController;
import com.example.demo.DBConnections.PostgresUserRepository;
import com.example.demo.DTO.UserService;
import com.example.demo.Interface.UserRepository;

public class Main {
    public static void main(String[] args) {
        // UserController controller = new UserController();
        // controller.printUser();

        /**
         * Now in the below example Dependency Injection is happening manually. Now we aren't changing business logic like UserService or controller. 
         * We are simply changing the implementation through Main class only. Nothing changed in the services.
         */
        UserRepository repository = new PostgresUserRepository();
        UserService service = new UserService(repository);
        UserController controller = new UserController(service);
        controller.printUser();
    }
}
