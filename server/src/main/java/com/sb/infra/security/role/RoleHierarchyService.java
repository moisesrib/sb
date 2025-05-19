package com.sb.infra.security.role;

import com.sb.user.enums.RoleEnum;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class RoleHierarchyService {

    private static final Map<RoleEnum, List<RoleEnum>> hierarchy = new EnumMap<>(RoleEnum.class);

    static {
        hierarchy.put(RoleEnum.USER, List.of(RoleEnum.USER));
        hierarchy.put(RoleEnum.SELLER, List.of(RoleEnum.SELLER, RoleEnum.USER));
        hierarchy.put(RoleEnum.MANAGER, List.of(RoleEnum.MANAGER, RoleEnum.SELLER, RoleEnum.USER));
        hierarchy.put(RoleEnum.OWNER, List.of(RoleEnum.OWNER, RoleEnum.MANAGER, RoleEnum.SELLER, RoleEnum.USER));
        hierarchy.put(RoleEnum.ADMIN, List.of(RoleEnum.ADMIN, RoleEnum.OWNER, RoleEnum.MANAGER, RoleEnum.SELLER, RoleEnum.USER));
    }

    public boolean hasAccess(Authentication authentication, RoleEnum requiredRole) {
        if (authentication == null || !authentication.isAuthenticated()) return false;
    
        var userRoles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
    
        return userRoles.stream().anyMatch(userRole -> {
            RoleEnum userRoleEnum = RoleEnum.valueOf(userRole.replace("ROLE_", ""));
            List<RoleEnum> rolesPermitidos = hierarchy.get(userRoleEnum);
            return rolesPermitidos != null && rolesPermitidos.contains(requiredRole);
        });
    }
}
