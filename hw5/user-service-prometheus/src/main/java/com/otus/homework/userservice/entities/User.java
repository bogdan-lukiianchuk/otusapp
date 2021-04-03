package com.otus.homework.userservice.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "app_user")
public class User {
    @Id
    private Long id;
    private String login;
    private String email;
    private String phoneNumber;
}
