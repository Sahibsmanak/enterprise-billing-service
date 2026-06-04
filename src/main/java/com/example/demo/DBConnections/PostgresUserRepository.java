package com.example.demo.DBConnections;

import org.springframework.stereotype.Repository;

import com.example.demo.Interface.UserRepository;

@Repository
public class PostgresUserRepository implements UserRepository {
    @Override
    public String getUser() {
        return "From PostgreSQL";
    }
    
}
