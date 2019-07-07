package com.demo.auth.user.domain.model;

import com.demo.auth.common.domain.model.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.ManyToMany;
import java.util.Collection;

@EqualsAndHashCode(callSuper = true)
@Data
public class Permission extends BaseEntity {

    private String name;

    @ManyToMany(mappedBy = "permissions")
    private Collection<Role> roles;

}
