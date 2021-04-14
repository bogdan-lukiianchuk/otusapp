package com.otus.homework.userserviceauth.entities;

import com.otus.homework.userserviceauth.enums.UserRole;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "app_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String login;
    @Column
    private String password;
    @Enumerated(EnumType.STRING)
    private UserRole role;
}
