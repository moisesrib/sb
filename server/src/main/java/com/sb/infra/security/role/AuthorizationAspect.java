package com.sb.infra.security.role;

import com.sb.core.exceptions.AccessDeniedException;
import com.sb.user.enums.RoleEnum;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AuthorizationAspect {

    @Autowired
    private RoleHierarchyService roleHierarchyService;

    @Around("@annotation(authenticationAnnotation)")
    public Object authorize(ProceedingJoinPoint pjp, Authentication authenticationAnnotation) throws Throwable {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        RoleEnum requiredRole = authenticationAnnotation.role();

        if (!roleHierarchyService.hasAccess(auth, requiredRole)) {
            throw new AccessDeniedException("Acesso negado: requer " + requiredRole);
        }

        return pjp.proceed();
    }
}