package com.demo.auth.common.infrastructure.security;

import com.demo.auth.user.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class CustomPermissionEvaluator implements PermissionEvaluator {

    @Autowired
    UserRepository userRepository;

    @Override
    public boolean hasPermission(Authentication authentication, Object accessType, Object permission) {
        if (authentication != null && accessType instanceof String) {
            UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
            if ("hasAccess".equalsIgnoreCase(String.valueOf(accessType))) {
                boolean hasAccess = validateAccess(String.valueOf(permission), userPrincipal);
                return hasAccess;
            }
            return false;
        }
        return false;
    }

    private boolean validateAccess(String permission, UserPrincipal userPrincipal) {
        return userPrincipal.getAuthorities().contains(new SimpleGrantedAuthority(permission));
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable serializable, String targetType,
                                 Object permission) {
        return false;
    }

}
