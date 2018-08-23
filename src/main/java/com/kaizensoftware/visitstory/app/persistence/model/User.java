package com.kaizensoftware.visitstory.app.persistence.model;

import com.kaizensoftware.visitstory.common.persistence.model.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity(name = "User")
@Table(name = "User", schema = "visitStory")
public class User extends BaseEntity {

    private String email;
    private String password;

    private String name;
    private String lastName;

    private String phone;

    @Column(columnDefinition = "TEXT")
    private String about;

}
