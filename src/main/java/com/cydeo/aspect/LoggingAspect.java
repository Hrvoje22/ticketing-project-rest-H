package com.cydeo.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.keycloak.adapters.springsecurity.account.SimpleKeycloakAccount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j //this will give us the line 14
public class LoggingAspect {

   // Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    private String getUsername(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        SimpleKeycloakAccount userDetails = (SimpleKeycloakAccount) authentication.getDetails();
        return userDetails.getKeycloakSecurityContext().getToken().getPreferredUsername();

    }

    @Pointcut("execution(* com.cydeo.controller.ProjectController.*(..)) || execution(* com.cydeo.controller.TaskController.*(..))")
    public void anyProjectAndTaskControllerPC(){}

    @Before("anyProjectAndTaskControllerPC()")
    public void beforeAnyProjectAndTaskControllerAdvice(JoinPoint joinPoint){
        log.info("Before -> Method: {}, User: {}"
        , joinPoint.getSignature().toShortString()
        , getUsername());
    }

}
