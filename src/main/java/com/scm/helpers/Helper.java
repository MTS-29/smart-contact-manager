package com.scm.helpers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;


public class Helper {

    public static String getEmailOfLoggedInUser(Authentication authentication){

        Logger logger = LoggerFactory.getLogger(Helper.class);

        // AuthenticationPrincipal principal = (AuthenticationPrincipal)authentication.getPrincipal();
        
        if( authentication instanceof OAuth2AuthenticationToken){
             
            var oAuth2AuthenticationToken = (OAuth2AuthenticationToken)authentication;
            var clientId = oAuth2AuthenticationToken.getAuthorizedClientRegistrationId();
            var oAuth2User = (OAuth2User)authentication.getPrincipal();
            String userName = "";

            if(clientId.equalsIgnoreCase("Google")){
                logger.info("Logged in via Google");
                userName = oAuth2User.getAttribute("email").toString();
            }
            else if(clientId.equalsIgnoreCase("Github")){
                logger.info("Logged in via Github");
                userName = oAuth2User.getAttribute("email") != null ? oAuth2User.getAttribute("email").toString() : oAuth2User.getAttribute("login").toString() + "@gmail.com";
            }
            return userName;
        } 
        else{
            logger.info("Logged in via local storage");
            return authentication.getName();
        }

    }
}
