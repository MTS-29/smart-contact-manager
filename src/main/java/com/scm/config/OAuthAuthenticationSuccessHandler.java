package com.scm.config;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.scm.entites.Providers;
import com.scm.entites.User;
import com.scm.helpers.AppConstants;
import com.scm.repositries.UserRepo;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class OAuthAuthenticationSuccessHandler implements AuthenticationSuccessHandler{

    Logger logger =  LoggerFactory.getLogger(OAuthAuthenticationSuccessHandler.class);

    @Autowired
    private UserRepo userRepo;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
                logger.info("OAuthAuthenticationSuccessHandler");
                
                // Identify the provider
                var OAuthAuthenticationToken = (OAuth2AuthenticationToken) authentication;              
                String authorizedClientRegistrationId =  OAuthAuthenticationToken.getAuthorizedClientRegistrationId();

                logger.info("Client: "+authorizedClientRegistrationId);


                var oathUser = (DefaultOAuth2User)authentication.getPrincipal();

                oathUser.getAttributes().forEach((key, value) ->{
                    logger.info(key + " : " + value);
                });

                User userSave = new User();
                userSave.setUserId(UUID.randomUUID().toString());
                userSave.setEmailVerified(true);
                userSave.setEnabled(true);
                userSave.setRoleList(List.of(AppConstants.ROLE_USER));
                userSave.setPassword("dummy ");

                if(authorizedClientRegistrationId.equalsIgnoreCase("google")){
                    
                    userSave.setName(oathUser.getAttribute("name").toString());
                    userSave.setProfilePic(oathUser.getAttribute("picture").toString());
                    userSave.setEmail(oathUser.getAttribute("email").toString());
                    userSave.setProviderUserId(oathUser.getName());
                    userSave.setProvider(Providers.GOOGLE);
                    userSave.setAbout("Created by Google login");
                }

                else if(authorizedClientRegistrationId.equalsIgnoreCase("github")){
                    String email = oathUser.getAttribute("email") != null ? oathUser.getAttribute("email").toString() : oathUser.getAttribute("login").toString() + "@gmail.com";
                    String picture = oathUser.getAttribute("avatar_url").toString();
                    String name = oathUser.getAttribute("login").toString();
                    String providerUserId = oathUser.getName();

                    userSave.setEmail(email);
                    userSave.setProfilePic(picture);
                    userSave.setName(name);
                    userSave.setProvider(Providers.GITHUB);
                    userSave.setProviderUserId(providerUserId);
                    userSave.setAbout("Created by Github login");
                }

                // else if(authorizedClientRegistrationId.equalsIgnoreCase("linkedin")){
                
                // }

                // else if(authorizedClientRegistrationId.equalsIgnoreCase("facebook")){

                // }

                else{
                    logger.info("Unknown Client ");
                }

                 
                User checkUser = userRepo.findByEmail(userSave.getEmail()).orElse(null);

                if(checkUser == null){
                    userRepo.save(userSave);
                    logger.info("User saved : "+ userSave.getEmail());
                }
                new DefaultRedirectStrategy().sendRedirect(request, response, "/user/profile ");
            }

}
