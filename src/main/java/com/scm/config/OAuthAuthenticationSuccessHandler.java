package com.scm.config;

import java.io.IOException;
import java.util.UUID;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
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

                DefaultOAuth2User user = (DefaultOAuth2User) authentication.getPrincipal();

                String email = user.getAttribute("email").toString();
                String name = user.getAttribute("name").toString();
                String picture = user.getAttribute("picture").toString();

                User userSave = new User();
                userSave.setName(name);
                userSave.setEmail(email);
                userSave.setProfilePic(picture);
                userSave.setPassword("password");
                userSave.setUserId(UUID.randomUUID().toString());
                userSave.setProvider(Providers.GOOGLE);
                userSave.setEnabled(true);
                userSave.setEmailVerified(true);
                userSave.setProviderUserId(user.getName());
                userSave.setRoleList(List.of(AppConstants.ROLE_USER));
                userSave.setAbout("This account is created using google");

                User checkUser = userRepo.findByEmail(email).orElse(null);

                if(checkUser == null){
                    userRepo.save(userSave);
                    logger.info("User saved : "+ email);
                }

                new DefaultRedirectStrategy().sendRedirect(request, response, "/user/profile ");
            }

}
