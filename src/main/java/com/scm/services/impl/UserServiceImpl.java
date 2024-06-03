package com.scm.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scm.entites.User;
import com.scm.helpers.ResourceNotFoundException;
import com.scm.repositries.UserRepo;
import com.scm.services.UserService;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepo userRepo;

    private Logger logger = LoggerFactory.getLogger(this.getClass()); 

    @Override
    public User saveUser(User user) {
        String userId = UUID.randomUUID().toString();
        user.setUserID(userId);
        return userRepo.save(user);
    }

    @Override
    public Optional<User> getUserById(String id) {
        return userRepo.findById(id);    
    }

    @Override
    public Optional<User> updateUser(User user) {
        User updateUser = userRepo.findById(user.getUserID())
        .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        updateUser.setName(user.getName());
        updateUser.setEmail(user.getEmail());
        updateUser.setPhoneNumber(user.getPhoneNumber());
        updateUser.setPassword(user.getPassword());
        updateUser.setAbout(user.getAbout());
        updateUser.setProfilePic(user.getProfilePic());
        updateUser.setEnabled(user.isEnabled());
        updateUser.setEmailVerfied(user.isEmailVerfied());
        updateUser.setPhoneVerified(user.isPhoneVerified());
        updateUser.setProvider(user.getProvider());
        updateUser.setProviderUserId(user.getProviderUserId());

        return Optional.ofNullable(userRepo.save(updateUser));
    }

    @Override
    public void deleteUser(String id) {
        User updateUser = userRepo.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        userRepo.delete(updateUser);
    }

    @Override
    public boolean isUserExist(String userId) {
        User updateUser = userRepo.findById(userId)
        .orElse(null);
        return updateUser != null ? true :false;
         
    }

    @Override
    public boolean isUserExistByEmail(String email) {
        User updateUser = userRepo.findByEmail(email)
        .orElse(null);
        return updateUser != null ? true :false;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }
}
