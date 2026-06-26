package com.example.cab_app_backend.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")  // Defines the MongoDB collection
@Data
public class User {
    @Id
    private String id;
    private String username;
    private String email;
    private String phonenumber;
    private String password;
    private int userrole;
    private int  Status = 0;
}

