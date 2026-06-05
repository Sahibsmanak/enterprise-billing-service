package com.example.demo.Interface;

import org.springframework.stereotype.Repository;

// public class UserRepository {
    
//     public String getUser () {
//         return "Sahib";
//     }
// }

@Component
public interface UserRepository {
    String getUser();
}