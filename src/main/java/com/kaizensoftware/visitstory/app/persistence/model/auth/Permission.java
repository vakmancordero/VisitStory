package com.kaizensoftware.visitstory.app.persistence.model.auth;

import com.kaizensoftware.visitstory.common.persistence.model.BaseEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity(name = "Permission")
@Table(name = "Permission", schema = "visitStory")
public class Permission extends BaseEntity {

    @Column(nullable = false)
    private String name;
    
    @ManyToMany(mappedBy = "permissions")
    private List<Role> roles = new ArrayList<>();

    public Permission(String name) { this.name = name; }

}
