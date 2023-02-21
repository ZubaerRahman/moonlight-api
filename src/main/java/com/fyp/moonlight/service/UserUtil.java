//package com.fyp.moonlight.service;
//
//import com.fyp.moonlight.exceptions.EntityNotFoundException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.crossstore.ChangeSetPersister;
//import org.springframework.security.core.Authentication;
//import org.springframework.stereotype.Service;
//
//import java.util.Optional;
//
//@Service
//public class UserUtil {
//
//    @Autowired
//    private UserService userService;
//
//    public boolean doesUserExist(String userId) {
//        return userService.findUserByUserId(userId).isPresent();
//    }
//
//    public Optional<String> getAuthenticatedUser(Authentication authentication) throws EntityNotFoundException {
//        return userService.findUserByEmail(authentication.getName());
//    }
//}
