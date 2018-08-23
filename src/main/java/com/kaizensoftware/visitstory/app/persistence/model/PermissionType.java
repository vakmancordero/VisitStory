package com.kaizensoftware.visitstory.app.persistence.model;

import com.kaizensoftware.visitstory.common.persistence.model.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity(name = "PermissionType")
@Table(name = "PermissionType", schema = "visitStory")
public class PermissionType extends BaseEntity {

    private String name;
    private String description;

    @OneToMany(mappedBy = "permission", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Permission> permissions;

}
