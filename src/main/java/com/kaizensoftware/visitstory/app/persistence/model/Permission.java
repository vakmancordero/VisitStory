package com.kaizensoftware.visitstory.app.persistence.model;

import com.kaizensoftware.visitstory.common.persistence.model.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity(name = "Permission")
@Table(name = "Permission", schema = "visitStory")
public class Permission extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "permission_type_id")
    private PermissionType permission;

    @OneToMany(mappedBy = "permission", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserPermission> userPermissions;

}
