package com.example.demo.DTO;

import org.springframework.stereotype.Service;

import com.example.demo.Interface.UserRepository;

import jakarta.websocket.server.ServerEndpoint;

@Service
public class UserService {
    // private UserRepository repository = new MySqlUserRepository();
    // private UserRepository repository = new PostgresUserRepository();

    // public String getUser () {
    //     return repository.getUser();
    // }


    /**
     * In the above commented example if you see we are changing the business logic to change the DB Connection.
     * This is tight coupling. Everytime if new object came we need to change here which is not a good practice.
     */
    private UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }


    public String getUser( ) {
        return repository.getUser();
    }   
}
