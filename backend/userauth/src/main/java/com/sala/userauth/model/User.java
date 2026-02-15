package com.sala.userauth.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "user")
public class User {

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
@Column(name = "userid") 
private Long userID;

    @Column(name = "username", length = 50)
    private String username;

    @Column(name = "email", length = 100)
    private String email;

    @Column(name = "password", length = 255)
    private String password;

    @Column(name = "dateCreated", updatable = false, insertable = false)
private LocalDateTime dateCreated;

    public User() {}

    public Long getUserID() { 
        return userID; 

    }
    
    public void setUserID(Long userID) { 
        this.userID = userID; 

    }

    public String getUsername() { 
        return username; 

    }

    public void setUsername(String username) { 
        this.username = username; 

    }

    public String getEmail() { 
        return email; 
    
    }

    public void setEmail(String email) { 
        this.email = email; 
    }

    public String getPassword() { 
        return password; 
    
    }

    public void setPassword(String password) { 
        this.password = password; 
    
    }

    public LocalDateTime getDateCreated() { 
        return dateCreated; 
    
    }

    public void setDateCreated(LocalDateTime dateCreated) { 
        this.dateCreated = dateCreated; 
    
    }
}